package com.prolab2Smurfs.Dijkstra;

public class SingleNode {
    //WALL PATH etc...
    private int nodeType;
    //coordinates (x,y).
    private int x, y;
    //prev node coordinates
    private int lastX,lastY;
    //distance
    private int distance;

    //Destination x,y
    private int destX, destY;

    public SingleNode(int type, int x, int y) {
        nodeType = type;
        this.x = x;
        this.y = y;
        distance = 0;
    }

    //Getters default properties
    public int getNodeType() {
        return nodeType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getDist() {
        return distance;
    }

    //Setters
    public void setNodeType(int type) {
        nodeType = type;
    }

    public void setLastNode (int x, int y) {
        lastX = x;
        lastY = y;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
