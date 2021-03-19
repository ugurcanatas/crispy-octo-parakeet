package com.prolab2Smurfs.PlayerClasses.DusmanSubClasses;

import com.prolab2Smurfs.PlayerClasses.Dusman;

public class Azman extends Dusman {
    String ID, ad, tur;
    DusmanLokasyon dusmanLokasyon;

    public Azman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon) {
        super(ID, ad, tur, dusmanLokasyon.getX(), dusmanLokasyon.getY(), dusmanLokasyon );
        this.ID = ID;
        this.ad = ad;
        this.tur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
    }

}
