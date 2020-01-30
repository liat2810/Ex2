package dataStructure;

import org.junit.jupiter.api.Test;
import utils.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class DGraphTest {


    private static final int MAX_NODES = 20;
    private static boolean isGraphInit = false;
    private static DGraph _graph;

    private static void initGraph(){

        if(isGraphInit) return;

        _graph = new DGraph();

        for (int i = 0; i < MAX_NODES; i++) {

            Point3D p = new Point3D(i, i+1,i+2);
            Node n = new Node(i, p, 0);
            _graph.addNode(n);
        }

        int random;

        for (int i = 0; i < MAX_NODES; i++) {
            random = (int)(Math.random()*5);

            for (int j = 0; j < random;) {
                random = (int)(Math.random()*MAX_NODES);
                if (random != i){
                    _graph.connect(i, random, 1);
                    j++;
                }
            }
        }
    }

    @Test
    void getNode() {
        
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
    }

    @Test
    void connect() {
    }

    @Test
    void getV() {
    }

    @Test
    void getE() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void getAllEdges() {
    }
}