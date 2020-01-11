package code.dataStructure;

import code.utils.Point3D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node implements node_data {

    private int key;
    private ArrayList<node_data> neighbors;
    private HashMap<Integer, Integer> neighborsKeyToIndex;
    private HashMap<node_data, edge_data> neighborsToEdges;
    private int neighborsCount;
    private double weight;
    private Point3D location;
    private int tag;
    private String info;

    public Node(int key, Point3D location, double weight){
        this.key = key;
        this.location = location;
        this.weight = weight;
        this.neighbors = new ArrayList<node_data>();
        this.neighborsCount = 0;
        this.neighborsKeyToIndex = new HashMap<Integer, Integer>();
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public Point3D getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Point3D p) {
        this.location = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public ArrayList<node_data> getNeighbors(){
        return this.neighbors;
    }

    public Map<node_data, edge_data> getNeighborsToEdges(){
        return this.neighborsToEdges;
    }

    public edge_data getEdgeByNeighborKey(int key){
        return this.neighborsToEdges.get(key);
    }

    public void connect(node_data dest, edge_data edge){
        this.neighbors.add(dest);
        this.neighborsToEdges.put(dest, edge);
        this.neighborsCount++;
    }

    public void removeNeighbor(node_data n){
        this.neighbors.remove(n);
        this.neighborsToEdges.remove(n);
        this.neighborsCount--;
    }

    // returns the list of Nodes in the path iff there is a valid path
    // between this node and the other node provided by its key
    public List<node_data> isValidPath(int key){

        edge_data edge = this.getEdgeByNeighborKey(key);
        List<node_data> list;
        Node neighbor;

        // if the edge is not null then the other node is a neighbor of this node
        if(edge != null) {
            neighbor = (Node) this.neighbors.get(key);
            list = new ArrayList<>();
            list.add(neighbor);
            return list;
        }

        //else, we should ask our neighbors if there is a valid path between them and the other node
        for(int i = 0; i < this.neighbors.size(); i++){
            neighbor = (Node) this.neighbors.get(i);
            // if there is a valid part between one of the neighbors and the other node then there is
            // a valid part between this node and the other part
            list = neighbor.isValidPath(key);
            if(list.size() >= 1){
                list.add(0 , neighbor);
                return list;
            }
        }

        // there is no valid path
        return new ArrayList<>();
    }





}
