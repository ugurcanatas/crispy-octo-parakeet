package com.prolab2Smurfs.PlayerClasses;

import com.prolab2Smurfs.Dijkstra.Dijkstra;
import com.prolab2Smurfs.Dijkstra.SingleNode;

public class Karakter {
    private String ID, Ad, Tur;
    private int coords_x, coords_y;
    SingleNode[][] NODE_MATRIX;
    public Karakter() {
        super();
    }

    public Karakter(String ID, String ad, String tur) {
        this.ID = ID;
        this.Ad = ad;
        this.Tur = tur;
    }

    public Karakter(String ID, String ad, String tur, int coords_x, int coords_y) {
        this.ID = ID;
        Ad = ad;
        Tur = tur;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
    }

    public Karakter(String ID, String ad, String tur, int coords_x, int coords_y, SingleNode[][] NODE_MATRIX) {
        this.ID = ID;
        this.Ad = ad;
        this.Tur = tur;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
        this.NODE_MATRIX = NODE_MATRIX;
    }

    public void setNODE_MATRIX(SingleNode[][] NODE_MATRIX) {
        this.NODE_MATRIX = NODE_MATRIX;
    }

    public SingleNode[][] getNODE_MATRIX() {
        return NODE_MATRIX;
    }

    public void setNodes (int x, int y, int TYPE) {
        this.NODE_MATRIX[x][y] = new SingleNode(TYPE,x,y);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getTur() {
        return Tur;
    }

    public void setTur(String tur) {
        Tur = tur;
    }

    public int getCoords_x() {
        return coords_x;
    }

    public void setCoords_x(int coords_x) {
        this.coords_x = coords_x;
    }

    public int getCoords_y() {
        return coords_y;
    }

    public void setCoords_y(int coords_y) {
        this.coords_y = coords_y;
    }

    public String getImg (){
        return "";
    }
}
