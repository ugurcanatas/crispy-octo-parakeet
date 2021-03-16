package com.prolab2Smurfs.Utils;

public class Tiles {
    private int x;
    private int y;
    private int coord_x, coord_y;
    private String TILE_TYPE;

    public Tiles (int x, int y, String TILE_TYPE, int coord_x, int coord_y) {
        this.x = x;
        this.y = y;
        this.TILE_TYPE = TILE_TYPE;
        this.coord_x = coord_x;
        this.coord_y = coord_y;
    }

    public int[] getXY () {
        return new int[]{x,y};
    }

    public String getTILE_TYPE() {
        return switch (TILE_TYPE) {
            case "0" -> "WALL";
            case "1" -> "PATH";
            default -> "";
        };
    }
}
