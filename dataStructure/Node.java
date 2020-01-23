package dataStructure;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.Point3D;

import java.util.*;

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
        this.neighbors = new ArrayList<>();
        this.neighborsCount = 0;
        this.neighborsKeyToIndex = new HashMap<>();
        this.neighborsToEdges = new HashMap<>();
    }

    public Node(Node other){
        this(other.key, new Point3D(other.location), other.weight);
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

    public Collection<edge_data> getAllEdges(){
        return this.neighborsToEdges.values();
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


    public List<node_data> getShortestPathList(int key){

        List<node_data> minList = new ArrayList<>();

        if(key == this.key){
            minList.add(this);
            return minList;
        }

        List<node_data> tempList;

        double minWeight = Double.MAX_VALUE;
        double tempWeight;

        Node neighbor;

        //else, we should ask our neighbors if there is a valid path between them and the other node
        for(int i = 0; i < this.neighbors.size(); i++){
            neighbor = (Node) this.neighbors.get(i);
            tempList = neighbor.getShortestPathList(key); // C B
            double edgeWeight = this.getEdgeByNeighborKey(neighbor.key).getWeight(); // 2
            tempWeight = calculatePathWeight(tempList) + edgeWeight; // 4
            if(tempWeight < minWeight){
                minList = tempList;
                minWeight = tempWeight;
            }
        }

        if(minList.size() > 0){
            minList.add(0, this);
        }

        // there is no valid path
        return minList;
    }


    public double shortestPathDist(int key) {
        List<node_data> list = this.getShortestPathList(key);

        if(list.size() == 0) {
            return Double.MAX_VALUE;
        }

        return calculatePathWeight(list);
    }




        public JSONObject toJsonObject(){

        JSONObject obj = new JSONObject();

        obj.put("key", this.key);
        obj.put("weight", this.weight);

        JSONObject p = new JSONObject();

        p.put("x", this.location.x());
        p.put("y", this.location.y());
        p.put("z", this.location.z());

        obj.put("Point3D", p);

        return obj;

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

}
