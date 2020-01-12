package dataStructure;

import org.junit.jupiter.api.Test;
import utils.Point3D;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    Point3D p1 = new Point3D(1,1,1);
    Node n1 = new Node(1, new Point3D(1,1,1), 1);
    Node n2 = new Node(2, new Point3D(2,2,2), 2);
    Node n3 = new Node(3, new Point3D(3,3,3), 3);
    Node n4 = new Node(4, new Point3D(4,4,4), 4);


    @Test
    void getKey() {
        int key1 = n1.getKey();
        int key2 = n2.getKey();
        int key3 = n3.getKey();
        int key4 = n4.getKey();

        assert(key1 == 1 && key2 == 2 && key3 == 3 && key4 == 4);
    }

    @Test
    void getLocation() {
        Point3D p1 = n1.getLocation();
        Point3D p2 = n2.getLocation();
        Point3D p3 = n3.getLocation();
        Point3D p4 = n4.getLocation();

        Point3D expected1 = new Point3D(1,1,1);
        Point3D expected2 = new Point3D(2,2,2);
        Point3D expected3 = new Point3D(3,3,3);
        Point3D expected4 = new Point3D(4,4,4);


        assert(p1.equals(expected1) && p2.equals(expected2) && p3.equals(expected3) && p4.equals(expected4));
    }

    @Test
    void setLocation() {
        Point3D p1 = new Point3D(4,4,4);
        Point3D p2 = new Point3D(3,3,3);
        Point3D p3 = new Point3D(2,2,2);
        Point3D p4 = new Point3D(1,1,1);

        n1.setLocation(p1);
        n2.setLocation(p2);
        n3.setLocation(p3);
        n4.setLocation(p4);

        assert(n1.getLocation().equals(p1) && n2.getLocation().equals(p2) &&
                n3.getLocation().equals(p3) && n4.getLocation().equals(p4));
    }

    @Test
    void getWeight() {
        double weight1 = n1.getWeight();
        double weight2 = n2.getWeight();
        double weight3 = n3.getWeight();
        double weight4 = n4.getWeight();

        assert(weight1 == 1 && weight2 == 2 && weight3 == 3 && weight4 == 4);
    }

    @Test
    void setWeight() {
        n1.setWeight(4);
        n2.setWeight(3);
        n3.setWeight(2);
        n4.setWeight(1);

        assert(n1.getWeight() == 4 && n2.getWeight() == 3.0 && n3.getWeight() == 2 && n4.getWeight() == 1);
    }

    @Test
    void getNeighbors() {

    }

    @Test
    void getAllEdges() {
    }

    @Test
    void getNeighborsToEdges() {
    }

    @Test
    void getEdgeByNeighborKey() {
    }

    @Test
    void connect() {

    }

    @Test
    void removeNeighbor() {
    }

    @Test
    void isValidPath() {
    }

    @Test
    void getShortestPathList() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void toJsonObject() {
    }
}