package com.prolab2Smurfs.Dijkstra;

import java.util.Comparator;

public class Dugum{
    private int[] neighbourList;
    private int nodeIndex;
    boolean isVisited;

    public Dugum(int[] neighbourList, int nodeIndex, boolean isVisited) {
        this.neighbourList = neighbourList;
        this.nodeIndex = nodeIndex;
        this.isVisited = isVisited;
    }

    public int[] getNeighbourList() {
        return neighbourList;
    }

    public void setNeighbourList(int[] neighbourList) {
        this.neighbourList = neighbourList;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
