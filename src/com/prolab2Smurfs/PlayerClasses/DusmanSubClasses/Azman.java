package com.prolab2Smurfs.PlayerClasses.DusmanSubClasses;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;

public class Azman extends Dusman {

    public Azman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon,
                 SingleNode[][] NODE_MATRIX, int[] gate) {
        super(ID, ad, tur, dusmanLokasyon.getX(), dusmanLokasyon.getY(), dusmanLokasyon,NODE_MATRIX, gate);
    }

    @Override
    public int getMovement() {
        return 1;
    }
}
