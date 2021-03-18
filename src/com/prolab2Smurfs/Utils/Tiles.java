package com.prolab2Smurfs.Utils;

public class Tiles {
    private int x;
    private int y;
    private int windowPositionX, windowPositionY;
    private int TILE_TYPE;

    public Tiles (int x, int y, int TILE_TYPE, int windowPositionX, int windowPositionY) {
        this.x = x;
        this.y = y;
        this.TILE_TYPE = TILE_TYPE;
        this.windowPositionX = windowPositionX;
        this.windowPositionY = windowPositionY;
    }

    public int getWindowPositionX() {
        return windowPositionX;
    }

    public void setWindowPositionX(int windowPositionX) {
        this.windowPositionX = windowPositionX;
    }

    public int getWindowPositionY() {
        return windowPositionY;
    }

    public void setWindowPositionY(int windowPositionY) {
        this.windowPositionY = windowPositionY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTILE_TYPE(int TILE_TYPE) {
        this.TILE_TYPE = TILE_TYPE;
    }

    public int getTILE_TYPE() {
        return TILE_TYPE;
    }
}
