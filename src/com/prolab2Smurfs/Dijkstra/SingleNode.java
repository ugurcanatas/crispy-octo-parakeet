package com.prolab2Smurfs.Dijkstra;

public class SingleNode {
    private int NODE_TYPE,
    lastX,
    lastY,
    x,y,dist;

    public SingleNode(int type, int x, int y) {
        NODE_TYPE = type;
        this.x = x;
        this.y = y;
        dist = 0;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getLastX() {return lastX;}
    public int getLastY() {return lastY;}
    public int getType() {return NODE_TYPE;}
    public int getDist() {return dist;}

    public void setType(int type) {NODE_TYPE = type;}
    public void setLastNode(int x, int y) {lastX = x; lastY = y;}
    public void setDist(int dist){ this.dist = dist;}
}
