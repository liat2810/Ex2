package gui;

import java.io.*;

import java.awt.Color;
import algorithms.Graph_Algo;
import dataStructure.*;
import utils.Point3D;

public class GraphGUI {

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

    public void init(graph g){
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.init(g);
    }

    public void initFromFile(String file) throws IOException {
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.init(file);
    }

    public void saveToFile(String file) throws IOException {
        if(this.graphAlgo_ == null){
            this.graphAlgo_ = new Graph_Algo();
            this.graphAlgo_.setGraphGUI(this);
        }
        this.graphAlgo_.save(file);
    }


    public void drawDGraph() {

        int width = 1024, height = 1024;

        StdDraw.setCanvasSize(width, height);

        for(node_data n : this.graphAlgo_.getGraphNodes()){

            Node node = (Node) n;

            Point3D p = n.getLocation();

            double scale = 0.1;

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(Color.BLUE);

            StdDraw.text(p.x()*scale, p.y()*scale + 0.02, n.getKey()+"");
            StdDraw.point(p.x()*scale, p.y()*scale);

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

                StdDraw.line(p.x() * scale,p.y() * scale, neighborLocation.x() * scale, neighborLocation.y() * scale);

                StdDraw.setPenRadius(0.025);
                StdDraw.setPenColor(Color.YELLOW);

                StdDraw.point(dirPointX*scale, dirPointY*scale);

                StdDraw.setPenColor(Color.BLACK);

                StdDraw.text(weightPointX*scale, weightPointY*scale, "" + edge.getWeight());
            }
        }
    }


    public static void main(String[] args){


        GraphGUI graphGui = new GraphGUI();
        try{
            graphGui.initFromFile("small_graph.json");
        }catch(IOException e){
            System.err.println(e.getStackTrace());
        }

        graphGui.drawDGraph();

    }
}
