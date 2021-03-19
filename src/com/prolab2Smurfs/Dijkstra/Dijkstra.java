package com.prolab2Smurfs.Dijkstra;

import java.util.ArrayList;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Dijkstra {

   ArrayList<SingleNode> progress = new ArrayList<>();
    SingleNode startNode;
    SingleNode[][] NODE_MATRIX;
    boolean solving = true;
    OnResult onResult;
    String FROM_ID;

    public Dijkstra() {
    }

    public Dijkstra(OnResult onResult) {
        this.onResult = onResult;
    }

    public Dijkstra(SingleNode startNode, SingleNode[][] NODE_MATRIX, OnResult onResult, String FROM_ID) {
        this.startNode = startNode;
        this.NODE_MATRIX = NODE_MATRIX;
        this.onResult = onResult;
        this.FROM_ID = FROM_ID;
    }

    public void reset () {
        progress = new ArrayList<>();
        this.solving = true;
    }

    public void setNODE_MATRIX(SingleNode[][] NODE_MATRIX) {
        this.NODE_MATRIX = NODE_MATRIX;
    }

    public SingleNode[][] getNODE_MATRIX() {
        return NODE_MATRIX;
    }

    public void setStartPoint(int x, int y) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE_START,x,y);
    }

    public void setDestinationPoint (int x, int y) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE_DESTINATION,x,y);
    }

    public void setNodes (int x, int y, int TYPE) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE,x,y);
    }

    public void start() {
        progress.add(startNode);
        while (this.solving) {
            if (progress.size() <= 0) {
                this.solving = false;
                break;
            }
            System.out.println("SOLVIBNG");

            int newDistance = progress.get(0).getDist() + 1;
            ArrayList<SingleNode> explored = exploreAdjMatrix(progress.get(0), newDistance);
            if (explored.size() > 0) {
                progress.remove(0);
                progress.addAll(explored);
            }else {
                progress.remove(0);
            }
        }
    }

    public ArrayList<SingleNode> exploreAdjMatrix (SingleNode curr, int dist) {
        ArrayList<SingleNode> explored = new ArrayList<>();
        if (curr.getX() - 1 > -1) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX() - 1][curr.getY()];
            if(neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getX() + 1 < 13) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX() + 1][curr.getY()];
            if (neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getY() - 1 > -1) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX()][curr.getY() - 1];
            if(neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getY() + 1 < 11) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX()][curr.getY() + 1];
            if (neighbor.getType() != TYPE_WALL && neighbor.getType() != TYPE_START && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
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
        while(length > 1) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
            current = this.NODE_MATRIX[lx][ly];
            current.setType(TYPE_FINAL);
            System.out.println("PATH => " + current.getType());
            System.out.println("PATH => " + lx + " - " + ly);
            System.out.println("-------------------");
            lx = current.getLastX();
            ly = current.getLastY();
            length--;
            finalNodes.add(current);
        }
        solving = false;
        onResult.OnDijkstraResult(finalNodes, this.FROM_ID);
    }

    public interface OnResult{
        void OnDijkstraResult(ArrayList<SingleNode> nodes, String FROM_ID);
    }
}
