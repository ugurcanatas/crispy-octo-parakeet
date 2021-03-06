package com.prolab2Smurfs.PlayerClasses;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;
import com.prolab2Smurfs.Utils.Tiles;

import static com.prolab2Smurfs.Utils.Constants.TYPE_WALL;

public class Dusman extends Karakter {
    private String dusmanID, dusmanAdi, dusmanTur;
    private DusmanLokasyon dusmanLokasyon;
    private int[] gate;

    public Dusman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon) {
        super(ID, ad, tur);
        this.dusmanID = ID;
        this.dusmanAdi = ad;
        this.dusmanTur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
    }

    public Dusman(String ID, String ad, String tur, int coords_x, int coords_y, DusmanLokasyon dusmanLokasyon,
                  SingleNode[][] NODE_MATRIX, int[] gate) {
        super(ID, ad, tur, coords_x, coords_y,NODE_MATRIX, gate);
        this.dusmanID = ID;
        this.dusmanAdi = ad;
        this.dusmanTur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
        this.gate = gate;
    }

    public String getDusmanID() {
        return dusmanID;
    }

    public void setDusmanID(String dusmanID) {
        this.dusmanID = dusmanID;
    }

    public String getDusmanAdi() {
        return dusmanAdi;
    }

    public void setDusmanAdi(String dusmanAdi) {
        this.dusmanAdi = dusmanAdi;
    }

    public String getDusmanTur() {
        return dusmanTur;
    }

    public void setDusmanTur(String dusmanTur) {
        this.dusmanTur = dusmanTur;
    }

    public DusmanLokasyon getDusmanLokasyon() {
        return dusmanLokasyon;
    }

    public void setDusmanLokasyon(DusmanLokasyon dusmanLokasyon) {
        this.dusmanLokasyon = dusmanLokasyon;
        setCoords_x(dusmanLokasyon.getX());
        setCoords_y(dusmanLokasyon.getY());
    }

    public int[] getGate() {
        return gate;
    }

    public void setGate(int[] gate) {
        this.gate = gate;
    }
}
