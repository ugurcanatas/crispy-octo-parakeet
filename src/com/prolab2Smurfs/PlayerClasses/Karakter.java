package com.prolab2Smurfs.PlayerClasses;
import com.prolab2Smurfs.Dijkstra.SingleNode;

import java.util.ArrayList;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Karakter {
    DijkstraResultInterface dijkstraResultInterface;

    boolean isDijkstraStarted = false;
    private String ID, Ad, Tur;
    private int coords_x, coords_y;
    SingleNode[][] NODE_MATRIX;
    public Karakter() {
        super();
    }

    public Karakter(String ID, String ad, String tur) {
        this.ID = ID;
        this.Ad = ad;
        this.Tur = tur;
    }

    public Karakter(String ID, String ad, String tur, int coords_x, int coords_y) {
        this.ID = ID;
        Ad = ad;
        Tur = tur;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
    }

    public Karakter(String ID, String ad, String tur, int coords_x, int coords_y,SingleNode[][] NODE_MATRIX, int[] gate) {
        this.ID = ID;
        this.Ad = ad;
        this.Tur = tur;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
        this.NODE_MATRIX = NODE_MATRIX;
    }

    public void setNODE_MATRIX(SingleNode[][] NODE_MATRIX) {
        this.NODE_MATRIX = NODE_MATRIX;
    }

    public SingleNode[][] getNODE_MATRIX() {
        return NODE_MATRIX;
    }

    public void setNodes (int x, int y, int TYPE) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE,x,y);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getTur() {
        return Tur;
    }

    public void setTur(String tur) {
        Tur = tur;
    }

    public int getCoords_x() {
        return coords_x;
    }

    public void setCoords_x(int coords_x) {
        this.coords_x = coords_x;
    }

    public int getCoords_y() {
        return coords_y;
    }

    public void setCoords_y(int coords_y) {
        this.coords_y = coords_y;
    }

    public String getImg (){
        return "";
    }

    public int getMovement () {
        return 0;
    }

    public void enKisaYoluBul () {
        ArrayList<SingleNode> visitedList = new ArrayList<>();
        System.out.println("START X" + getCoords_x());
        SingleNode startPoint = new SingleNode(TYPE_START,getCoords_x(),getCoords_y());
        this.isDijkstraStarted = true;
        visitedList.add(startPoint);

        while (isDijkstraStarted) {
            System.out.println("STARTED ????");
            if (visitedList.size() <= 0) {
                this.isDijkstraStarted = false;
                break;
            }
            int newDistance = visitedList.get(0).getDist() + 1;
            ArrayList<SingleNode> exploringList = exploreNODEMATRIX(visitedList.get(0),newDistance);
            if (exploringList.size() > 0) {
                visitedList.remove(0);
                visitedList.addAll(exploringList);
            }else {
                visitedList.remove(0);
            }
        }
    }

    private ArrayList<SingleNode> exploreNODEMATRIX(SingleNode currentNode, int distance){
        ArrayList<SingleNode> explored = new ArrayList<>();
        if (currentNode.getX() - 1 > -1) {
            SingleNode neighbor = getNODE_MATRIX()[currentNode.getX() - 1][currentNode.getY()];
            if(neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, currentNode.getX(), currentNode.getY(), distance);
                explored.add(neighbor);
            }
        }
        if(currentNode.getX() + 1 < getNODE_MATRIX().length) {
            SingleNode neighbor = getNODE_MATRIX()[currentNode.getX() + 1][currentNode.getY()];
            if (neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, currentNode.getX(), currentNode.getY(), distance);
                explored.add(neighbor);
            }
        }
        if(currentNode.getY() - 1 > -1) {
            SingleNode neighbor = getNODE_MATRIX()[currentNode.getX()][currentNode.getY() - 1];
            if(neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, currentNode.getX(), currentNode.getY(), distance);
                explored.add(neighbor);
            }
        }
        if(currentNode.getY() + 1 < getNODE_MATRIX()[0].length) {
            SingleNode neighbor = getNODE_MATRIX()[currentNode.getX()][currentNode.getY() + 1];
            if (neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, currentNode.getX(), currentNode.getY(), distance);
                explored.add(neighbor);
            }
        }
        return explored;
    }

    public void explore(SingleNode current, int lastx, int lasty, int dist){
        if(current.getType() != TYPE_DESTINATION){
            current.setType(TYPE_CHECKED);
            current.setLastNode(lastx,lasty);
            current.setDist(dist);

        }

        if (current.getType() == TYPE_DESTINATION) {
            current.setLastNode(lastx,lasty);
            current.setDist(dist);
            finalPath(current);
        }
    }

    public void finalPath(SingleNode current){
        ArrayList<SingleNode> finalNodes = new ArrayList<>();
        int length = current.getDist();
        System.out.println(current.getDist());
        int lx = current.getLastX();
        int ly = current.getLastY();
        while(length > 1) {
            current = getNODE_MATRIX()[lx][ly];
            current.setType(TYPE_FINAL);
            lx = current.getLastX();
            ly = current.getLastY();
            length--;
            finalNodes.add(current);
        }
        this.isDijkstraStarted = false;
        dijkstraResultInterface.onDijkstraResult(finalNodes,getID());
    }

    public void setDijkstraResultInterface(DijkstraResultInterface dijkstraResultInterface) {
        this.dijkstraResultInterface = dijkstraResultInterface;
    }

    public interface DijkstraResultInterface {
        void onDijkstraResult(ArrayList<SingleNode> result, String ID);
    }
}
