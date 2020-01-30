package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.*;
import utils.Point3D;

public class GraphGUI implements graph_algorithms {

    private static final double SCALE = 0.1;

    private Graph_Algo graphAlgo_;

    public GraphGUI(){
        this.graphAlgo_ = null;
    }

    public GraphGUI(graph g){
        this.graphAlgo_ = new Graph_Algo(g);
        this.graphAlgo_.setGraphGUI(this);
    }

    public GraphGUI(String file){
        this.graphAlgo_ = new Graph_Algo();
        this.graphAlgo_.init(file);
        this.graphAlgo_.setGraphGUI(this);
    }

    @Override
    public void init(graph g){
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.init(g);
    }

    @Override
    public graph copy() {
        return this.graphAlgo_.copy();
    }

    @Override
    public boolean isConnected() {
        return this.graphAlgo_.isConnected();
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return this.graphAlgo_.shortestPathDist(src, dest);
    }


    /*
    * Returns the shortest path list of Nodes and marks the starting point and the edges
    * on the graphic interface in green
    */
    @Override
    public List<node_data> shortestPath(int src, int dest) {

        List<node_data> pathList =  this.graphAlgo_.shortestPath(src, dest);
        if(pathList == null){
            return null;
        }

        StdDraw.setPenColor(Color.GREEN);

        Node source;
        Node destination;

        source = (Node) pathList.get(0);
        Point3D p = source.getLocation();
        StdDraw.setPenRadius(0.03);
        StdDraw.point(p.x()*SCALE, p.y()*SCALE);

        for(int i = 0; i < pathList.size()-1; i++){

            source = (Node) pathList.get(i);
            destination = (Node) pathList.get(i+1);

            Point3D srcLocation = source.getLocation();
            Point3D destLocation = destination.getLocation();
            StdDraw.setPenRadius(0.007);
            StdDraw.line(srcLocation.x() * SCALE,srcLocation.y() * SCALE, destLocation.x() * SCALE, destLocation.y() * SCALE);

        }
        return pathList;
    }

    @Override
    public List<node_data> TSP(List<Integer> targets) {


        List<node_data> pathList =  this.graphAlgo_.TSP(targets);
        if(pathList == null){
            return null;
        }

        StdDraw.setPenColor(Color.GREEN);

        Node source;
        Node destination;

        source = (Node) pathList.get(0);
        Point3D p = source.getLocation();
        StdDraw.setPenRadius(0.03);
        StdDraw.point(p.x()*SCALE, p.y()*SCALE);

        for(int i = 0; i < pathList.size()-1; i++){

            source = (Node) pathList.get(i);
            destination = (Node) pathList.get(i+1);

            Point3D srcLocation = source.getLocation();
            Point3D destLocation = destination.getLocation();
            StdDraw.setPenRadius(0.007);
            StdDraw.line(srcLocation.x() * SCALE,srcLocation.y() * SCALE, destLocation.x() * SCALE, destLocation.y() * SCALE);

        }

        return pathList;
    }

    @Override
    public void init(String file) {
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.init(file);
    }

    @Override
    public void save(String file){
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.save(file);
    }

    public Graph_Algo getGraphAlgo() {
        return graphAlgo_;
    }

    public void drawDGraph() {

        int width = 1024, height = 1024;

        StdDraw.setCanvasSize(width, height);

        for(node_data n : this.graphAlgo_.getGraphNodes()){

            Node node = (Node) n;

            Point3D p = n.getLocation();

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(Color.BLUE);

            StdDraw.text(p.x()*SCALE, p.y()*SCALE + 0.02, n.getKey()+"");
            StdDraw.point(p.x()*SCALE, p.y()*SCALE);

            // draw edges

            for(node_data neighbor : node.getNeighbors()){

                StdDraw.setPenRadius(0.007);
                StdDraw.setPenColor(Color.RED);

                Point3D neighborLocation = neighbor.getLocation();
                edge_data edge = ((Node) n).getEdgeByNeighborKey(neighbor.getKey());
                double m = (p.y() - neighborLocation.y())/(p.x() - neighborLocation.x());
                double dirPointX = neighborLocation.x() + (neighborLocation.x() > p.x() ? -Math.sqrt((0.1)/(1+m*m)) : Math.sqrt((0.1)/(1+m*m)));
                double dirPointY = m*(dirPointX - neighborLocation.x()) + neighborLocation.y();

                double weightPointX = neighborLocation.x() + (neighborLocation.x() > p.x() ? -Math.sqrt(0.2/(1+m*m)) : Math.sqrt(0.5/(1+m*m)));
                double weightPointY = m*(weightPointX - neighborLocation.x()) + neighborLocation.y();

                StdDraw.line(p.x() * SCALE,p.y() * SCALE, neighborLocation.x() * SCALE, neighborLocation.y() * SCALE);

                StdDraw.setPenRadius(0.025);
                StdDraw.setPenColor(Color.YELLOW);

                StdDraw.point(dirPointX*SCALE, dirPointY*SCALE);

                StdDraw.setPenColor(Color.BLACK);

                StdDraw.text(weightPointX*SCALE, weightPointY*SCALE, "" + edge.getWeight());
            }
        }
    }


    public static void main(String[] args){

        GraphGUI graphGui = new GraphGUI();
        graphGui.init("small_graph.json");

        graphGui.drawDGraph();

        ArrayList targets = new ArrayList<Integer>();

        graphGui.shortestPath(4,6);

//        targets.add(1);
//        targets.add(2);
//        targets.add(3);
//        targets.add(6);
//        targets.add(5);
//        targets.add(4);
//        targets.add(3);
//        targets.add(2);
//
//        graphGui.TSP(targets);
    }
}
