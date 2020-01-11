package algorithms;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataStructure.*;
import dataStructure.graph;


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
			Long weight = (Long) jsonEdge.get("weight");
			graph.connect(srcKey.intValue(), destKey.intValue(), weight.doubleValue());
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
					if(n1 != n2 && ((Node) n1).isValidPath((n2).getKey()).size() == 0) return false;
				}
			}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		return this.shortestPathList(src, dest).size();
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		return this.shortestPathList(src, dest);
	}

	private List<node_data> shortestPathList(int src, int dest){

		Node source = (Node) this._graph.getNode(src);
		List<node_data> shortestList = null;
		List<node_data> list;
		int minDist = Integer.MAX_VALUE;
		int dist = 0;

		for(node_data n : source.getNeighbors()){

			list = ((Node) n).isValidPath(dest);
			dist = list.size();

			if(dist > 0){
				if(dist < minDist){
					minDist = dist;
					shortestList = list;
				}
			}
		}

		if(shortestList == null){
			return new ArrayList<>();
		}

		return shortestList;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {

		Integer[] keysList = (Integer[]) targets.toArray();

		List<node_data> shortest = new ArrayList<>();
		List<node_data> temp;

		for(int i = 0; i < keysList.length-1; i++){
			int src = keysList[i];
			int dest = keysList[i+1];
			temp = this.shortestPath(src, dest);

			if(temp.size() == 0){
				return new ArrayList<>();
			}
			shortest.addAll(temp);
		}

		return shortest;
	}

	@Override
	public graph copy() {
		// TODO Auto-generated method stub
		return null;
	}



	public static void main(String[] args){


		Graph_Algo ga = new Graph_Algo();

		ga.init("graph.json");
		ga.save("graphAfterSave.json");



	}
}
