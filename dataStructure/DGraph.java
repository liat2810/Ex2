package dataStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class DGraph implements graph{

	private ArrayList<node_data> V;
	private ArrayList<edge_data> E;
	private HashMap<Integer, Integer> VKeyToIndex;
	private int size;

	public DGraph(){
		this.V = new ArrayList<node_data>();
		this.E = new ArrayList<edge_data>();
		this.VKeyToIndex = new HashMap<>();
		this.size = 0;
	}


	@Override
	public node_data getNode(int key) {
		Integer nodeIndex = this.getNodeIndexByKey(key);
		if(nodeIndex > -1 && (nodeIndex >= 0 && nodeIndex <this.size)){
			return this.V.get(nodeIndex);
		}
		return null;
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		Node source = (Node) this.getNode(src);
		Edge edge = (Edge) source.getEdgeByNeighborKey(dest);
		return edge;
	}

	@Override
	public void addNode(node_data n) {
		this.V.add(n);
		this.VKeyToIndex.put(n.getKey(), this.size);
		this.size++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		Node source = (Node) this.getNode(src);
		Node destination = (Node) getNode(dest);
		Edge edge = new Edge(source, destination, w);
		source.connect(destination, edge);
		this.E.add(edge);
	}

	@Override
	public Collection<node_data> getV() {
		return this.V;
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		Node src = (Node) this.getNode(node_id);
		return src.getAllEdges();
	}


	@Override
	public node_data removeNode(int key) {
		Node node = (Node) this.getNode(key);
		for(edge_data e: this.E){
			if(e.getSrc() == key || e.getDest() == key ){
				if(e.getSrc() == key){
					((Node) this.getNode(e.getSrc())).removeNeighbor(node);
				}
			}
			this.E.remove(e);
		}
		return node;
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		Node source = (Node) this.getNode(src);
		Node destination = (Node) this.getNode(dest);
		Edge edge = (Edge) source.getEdgeByNeighborKey(dest);
		source.removeNeighbor(destination);
		this.E.remove(edge);
		return edge;
	}

	@Override
	public int nodeSize() {
		return this.V.size();
	}

	@Override
	public int edgeSize() {
		return this.E.size();
	}

	@Override
	public int getMC() {
		return 0;
	}


	public Collection<edge_data> getAllEdges(){
		return this.E;
	}


	private int getNodeIndexByKey(int key){
		Integer nodeIndex = this.VKeyToIndex.get(key);

		if(nodeIndex != null && (nodeIndex >= 0 && nodeIndex < this.size)){
			return nodeIndex;
		}

		return -1;
	}

	public static void main(String[] args){

	}

}
