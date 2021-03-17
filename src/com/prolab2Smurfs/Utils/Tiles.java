package com.prolab2Smurfs.Utils;

public class Tiles {
    private int x;
    private int y;
    private int coord_x, coord_y;
    private int TILE_TYPE;
    private boolean isVisible;

    public Tiles (int x, int y, int TILE_TYPE, int coord_x, int coord_y, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.TILE_TYPE = TILE_TYPE;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    public int getCoord_x() {
        return coord_x;
    }

    public void setCoord_x(int coord_x) {
        this.coord_x = coord_x;
    }

    public int getCoord_y() {
        return coord_y;
    }

    public void setCoord_y(int coord_y) {
        this.coord_y = coord_y;
    }

    public void setTILE_TYPE(int TILE_TYPE) {
        this.TILE_TYPE = TILE_TYPE;
    }

    public int getTILE_TYPE() {
        return TILE_TYPE;
        /*return switch (TILE_TYPE) {
            case 2 -> "WALL";
            case 3 -> "PATH";
            case 5 -> "ROUTE";
            default -> "";
        };*/
    }
}
