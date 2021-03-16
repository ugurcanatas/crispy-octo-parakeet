package com.prolab2Smurfs.PlayerClasses;

public class Karakter {
    private String ID, Ad, Tur;
    private int coords_x, coords_y;

    public Karakter() {
    }

    public Karakter(String ID, String ad, String tur) {
        this.ID = ID;
        Ad = ad;
        Tur = tur;
    }

    public Karakter(String ID, String ad, String tur, int coords_x, int coords_y) {
        this.ID = ID;
        Ad = ad;
        Tur = tur;
        this.coords_x = coords_x;
        this.coords_y = coords_y;
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
