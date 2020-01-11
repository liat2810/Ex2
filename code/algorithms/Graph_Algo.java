package code.algorithms;

import java.util.ArrayList;
import java.util.List;

import code.dataStructure.*;
import code.dataStructure.graph;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{

	private graph _graph;

	public void init(graph g){
		this._graph = g;
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(String file_name) {
		// TODO Auto-generated method stub
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

}
