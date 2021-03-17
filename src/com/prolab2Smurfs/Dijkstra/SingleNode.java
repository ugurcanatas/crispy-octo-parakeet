package com.prolab2Smurfs.Dijkstra;

public class SingleNode {
    private int nType;
    private int lastX;
    private int lastY;
    private int x;
    private int y;
    private int dist;


    public SingleNode(int type, int x, int y) {
        nType = type;
        this.x = x;
        this.y = y;
        dist = 0;
    }

    public int getX() {return x;}		//GET METHODS
    public int getY() {return y;}
    public int getLastX() {return lastX;}
    public int getLastY() {return lastY;}
    public int getType() {return nType;}
    public int getDist() {return dist;}

    public void setType(int type) {nType = type;}		//SET METHODS
    public void setLastNode(int x, int y) {lastX = x; lastY = y;}
    public void setDist(int dist){ this.dist = dist;}
}
