package algorithms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dataStructure.*;
import dataStructure.graph;


import gui.GraphGUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.Point3D;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */

public class Graph_Algo implements graph_algorithms{

	private graph _graph;
	private GraphGUI _graphGUI;

	public Graph_Algo(){
		this._graph = new DGraph();
	}

	public Graph_Algo(graph g){
		this._graph = g;
	}

	public void init(graph g){
		this._graph = g;
	}

	@Override
	public void init(String file_name) {

		JSONParser jsonParser = new JSONParser();
		JSONObject config = null;

		try
		{
			//Read JSON file
			FileReader reader = new FileReader(file_name);
			config = (JSONObject) jsonParser.parse(reader);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		DGraph graph = new DGraph();

		JSONArray nodes = (JSONArray) config.get("Nodes");
		JSONArray edges = (JSONArray) config.get("Edges");


		// Parse Nodes
		for(int i = 0; i < nodes.size(); i++){

			JSONObject jsonNode = (JSONObject) nodes.get(i);

			Long key = (Long) jsonNode.get("key");
			Long weight = (Long) jsonNode.get("weight");

			JSONObject jsonPoint = (JSONObject) jsonNode.get("Point3D");

			Long x = (Long) jsonPoint.get("x");
			Long y = (Long) jsonPoint.get("y");
			Long z = (Long) jsonPoint.get("z");

			Point3D p3d = new Point3D(x,y,z);

			Node n = new Node(key.intValue(), p3d, weight.doubleValue());

			graph.addNode(n);

		}

		//Parse Edges
		for(int i = 0; i < edges.size(); i++){
			JSONObject jsonEdge = (JSONObject) edges.get(i);
			Long srcKey = (Long) jsonEdge.get("src");
			Long destKey = (Long) jsonEdge.get("dest");
			double weight = ((Number)jsonEdge.get("weight")).doubleValue();
			graph.connect(srcKey.intValue(), destKey.intValue(), weight);
		}

		this._graph = graph;

	}

	@Override
	public void save(String file_name) {

		JSONObject obj = new JSONObject();
		JSONArray nodes = new JSONArray();
		JSONArray edges = new JSONArray();

		for(node_data n : this._graph.getV()){
			Node node = (Node) n;
			nodes.add(node.toJsonObject());
		}

		obj.put("Nodes", nodes);

		for(edge_data e : ((DGraph) this._graph).getAllEdges()){
			Edge edge = (Edge) e;
			edges.add(edge.toJsonObject());
		}

		obj.put("Edges", edges);

		try (FileWriter file = new FileWriter(file_name)) {
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {
			for(node_data n1 : this._graph.getV()){
				for(node_data n2 : this._graph.getV()){
					if(n1 != n2 && ! ((Node) n1).isValidPath(n2.getKey())) return false;
				}
			}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		Node source = (Node) this._graph.getNode(src);
		return source.shortestPathDist(dest);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		Node source = (Node) this._graph.getNode(src);
		return source.getShortestPathList(dest);
	}


	@Override
	public List<node_data> TSP(List<Integer> targets) {

//		Integer[] keysList = (Integer[]) targets.toArray();

		List<node_data> shortest = new ArrayList<>();
		List<node_data> temp;

		for(int i = 0; i < targets.size()-1; i++){
			int src = targets.get(i);
			int dest = targets.get(i+1);
			temp = this.shortestPath(src, dest);
			if(temp.size() == 0){
				return null;
			}else {
				// add all items from temp to shortest list
				for(int j = (i == 0 ? 0 : 1); j < temp.size(); j++){
					shortest.add(temp.get(j));
				}
			}
		}
		return shortest;
	}

	@Override
	public graph copy() {
		return ((DGraph) this._graph).copy();
	}


	private static double calculatePathWeight(List<node_data> list){

		if(list.size() == 0) return Double.MAX_VALUE;
		if(list.size() == 1) return 0;

		double totalWeight = 0;
		node_data src;
		node_data dest;

		for(int i = 0; i < list.size() - 1; i++){
			src = list.get(i);
			dest = list.get(i+1);
			edge_data edge = ((Node)src).getEdgeByNeighborKey(dest.getKey());
			totalWeight += edge.getWeight();
		}

		return totalWeight;
	}

	public Collection<node_data> getGraphNodes(){
		return this._graph.getV();
	}


	public graph getGraph(){
		return this._graph;
	}

	public void setGraphGUI(GraphGUI gui){
		this._graphGUI = gui;
	}



	public void updateGUI(){
		if(this._graphGUI != null){
			this._graphGUI.drawDGraph();
		}
	}


	public static void main(String[] args){

		Graph_Algo ga = new Graph_Algo();

		ga.init("small_graph.json");

		ArrayList targets = new ArrayList<Integer>();

		targets.add(1);
		targets.add(2);
		targets.add(3);
		targets.add(6);
		targets.add(5);
		targets.add(4);
		targets.add(3);
		targets.add(2);


		System.out.println("is ga connected?");
		System.out.println(ga.isConnected());

		List l = ga.TSP(targets);

		if(l != null){
			for(int i = 0; i < l.size(); i++){

				System.out.println(((Node)l.get(i)).getKey());
			}
			System.out.println(calculatePathWeight(l));
		}
	}
}
