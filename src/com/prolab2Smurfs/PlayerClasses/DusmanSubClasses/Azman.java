package com.prolab2Smurfs.PlayerClasses.DusmanSubClasses;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;

public class Azman extends Dusman {
    String ID, ad, tur;
    DusmanLokasyon dusmanLokasyon;

    public Azman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon,
                 SingleNode[][] NODE_MATRIX) {
        super(ID, ad, tur, dusmanLokasyon.getX(), dusmanLokasyon.getY(), dusmanLokasyon,NODE_MATRIX);
        this.ID = ID;
        this.ad = ad;
        this.tur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
    }

}
