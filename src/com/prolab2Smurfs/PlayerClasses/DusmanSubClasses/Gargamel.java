package com.prolab2Smurfs.PlayerClasses.DusmanSubClasses;

import com.prolab2Smurfs.PlayerClasses.Dusman;

public class Gargamel extends Dusman {
    public Gargamel(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon) {
        super(ID, ad, tur, dusmanLokasyon.getX(),dusmanLokasyon.getY(),dusmanLokasyon);
    }

}
