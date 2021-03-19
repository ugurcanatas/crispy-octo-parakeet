package com.prolab2Smurfs.PlayerClasses.DusmanSubClasses;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;

public class Azman extends Dusman {
    String ID, ad, tur;
    DusmanLokasyon dusmanLokasyon;

    public Azman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon,
                 SingleNode startNode, SingleNode[][] NODE_MATRIX, OnResult onResult) {
        super(ID, ad, tur, dusmanLokasyon.getX(), dusmanLokasyon.getY(), dusmanLokasyon,
                startNode,NODE_MATRIX,onResult);
        this.ID = ID;
        this.ad = ad;
        this.tur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
    }

}
