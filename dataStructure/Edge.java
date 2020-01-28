package dataStructure;

import org.json.simple.JSONObject;

public class Edge implements edge_data {

    private node_data src;
    private node_data dest;
    private double weight;
    private int tag;
    private String info;
    private boolean visited;

    public Edge(node_data src, node_data dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.visited = false;
    }

    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
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

    public void setVisited(boolean flag){
        this.visited = flag;
    }

    public boolean isVisited(){
        return this.visited;
    }

    public JSONObject toJsonObject(){

        JSONObject obj = new JSONObject();

        obj.put("src", this.src.getKey());
        obj.put("dest", this.dest.getKey());
        obj.put("weight", this.weight);

        return obj;
    }

}
