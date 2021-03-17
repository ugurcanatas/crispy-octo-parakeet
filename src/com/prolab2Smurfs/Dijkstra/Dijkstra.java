package com.prolab2Smurfs.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;

public class Dijkstra {

   /* ArrayList<SingleNode> progress = new ArrayList<>();
    SingleNode startNode;
    SingleNode[][] NODE_MATRIX;
    boolean solving = true;

    public Dijkstra(SingleNode startNode, SingleNode[][] NODE_MATRIX) {
        this.startNode = startNode;
        this.NODE_MATRIX = NODE_MATRIX;
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
                //update
                //delay
            }else {
                progress.remove(0);
            }
        }
    }

    public ArrayList<SingleNode> exploreAdjMatrix (SingleNode curr, int dist) {
        ArrayList<SingleNode> explored = new ArrayList<>();
        if (curr.getX() - 1 > -1) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX() - 1][curr.getY()];
            if(neighbor.getNodeType() != 0 && neighbor.getNodeType() != 2 && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getX() + 1 < 12) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX() + 1][curr.getY()];
            if (neighbor.getNodeType() != 0 && neighbor.getNodeType() != 2 && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getY() - 1 > -1) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX()][curr.getY() - 1];
            if(neighbor.getNodeType() != 0 && neighbor.getNodeType() != 2 && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        if(curr.getY() + 1 < 12) {
            SingleNode neighbor = this.NODE_MATRIX[curr.getX()][curr.getY() + 1];
            if (neighbor.getNodeType() != 0 && neighbor.getNodeType() != 2 && neighbor.getDist() == 0) {
                explore(neighbor, curr.getX(), curr.getY(), dist);
                explored.add(neighbor);
            }
        }
        return explored;
    }

    public void explore(SingleNode current, int lastx, int lasty, int dist){
        if(current.getNodeType() != 1){
            current.setNodeType(4);
            current.setLastNode(lastx,lasty);
            current.setDistance(dist);

        }

        if (current.getNodeType() == 1) {
            current.setLastNode(lastx,lasty);
            current.setDistance(dist);
            finalPath(current);
        }
    }

    public void finalPath(SingleNode current){
        int length = current.getDist();
        System.out.println(current.getDist());
        int lx = current.getLastX();
        int ly = current.getLastY();
        while(length > 1) {	//BACKTRACK FROM THE END OF THE PATH TO THE START
            current = this.NODE_MATRIX[lx][ly];
            current.setNodeType(5);
            System.out.println("PATH => " + current.getNodeType());
            System.out.println("PATH => " + lx + " - " + ly);
            System.out.println("-------------------");
            lx = current.getLastX();
            ly = current.getLastY();
            length--;
        }
        solving = false;
    }

    private void showResult () {
        System.out.println("SHOWING RESULT");
        for (int i = 0; i < this.NODE_MATRIX.length; i++) {
            for (int j = 0; j < this.NODE_MATRIX[0].length; j++) {
                System.out.print(this.NODE_MATRIX[i][j].getNodeType() + "\t");
            }
            System.out.println("");
        }
    }*/
}
