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

    public Dijkstra(SingleNode startNode, SingleNode[][] NODE_MATRIX, OnResult onResult, String FROM_ID) {
        this.startNode = startNode;
        this.NODE_MATRIX = NODE_MATRIX;
        this.onResult = onResult;
        this.FROM_ID = FROM_ID;
    }

    /**
     * <h1>reset() method</h1>
     * <h3>Resets the certain properties to beggining values in order to
     * start Dijkstra search again</h3>
     * */
    public void reset () {
        progress = new ArrayList<>();
        this.solving = true;
    }

    /**
     * <h1>setNODE_MATRIX() method</h1>
     * <h3>Sets the 2D array to the CLONED 2D array.</h3>
     * */
    public void setNODE_MATRIX(SingleNode[][] CLONED) {
        for (int i = 0; i < CLONED.length; i++) {
            for (int j = 0; j < CLONED[0].length; j++) {
                setNodes(i,j,CLONED[i][j].getType());
            }
        }
    }

    public SingleNode[][] getNODE_MATRIX() {
        return NODE_MATRIX;
    }

    /**
     * <h1>setStartPoint() method</h1>
     * <h3>Sets the start point.</h3>
     * <p>Start point contains enemy x and y coordinates.</p>
     * */
    public void setStartPoint(int x, int y) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE_START,x,y);
    }

    /**
     * <h1>setStartPoint() method</h1>
     * <h3>Sets the destination point.</h3>
     * <p>Destination point contains player x and y coordinates.</p>
     * */
    public void setDestinationPoint (int x, int y) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE_DESTINATION,x,y);
    }

    /**
     * <h1>setNodes() method</h1>
     * <h3>Sets the desired node with dynamic type.</h3>
     * @param TYPE <h3>Node Type.</h3>
     *              @see com.prolab2Smurfs.Utils.Constants
     * @param x <h3>X coordinate</h3>
     * @param y <h3>Y coordinate</h3>
     * */
    public void setNodes (int x, int y, int TYPE) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE,x,y);
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }

    public void start() {
        progress.add(startNode);
        while (this.solving) {
            if (progress.size() <= 0) {
                this.solving = false;
                break;
            }
            System.out.println("SOLVING");

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
