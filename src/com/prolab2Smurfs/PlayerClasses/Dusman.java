package com.prolab2Smurfs.PlayerClasses;

import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;

public class Dusman extends Karakter {
    private String dusmanID, dusmanAdi, dusmanTur;
    private DusmanLokasyon dusmanLokasyon;

    public Dusman(String ID, String ad, String tur, DusmanLokasyon dusmanLokasyon) {
        super(ID, ad, tur);
        this.dusmanID = ID;
        this.dusmanAdi = ad;
        this.dusmanTur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
    }

    public Dusman(String ID, String ad, String tur, int coords_x, int coords_y, DusmanLokasyon dusmanLokasyon) {
        super(ID, ad, tur, coords_x, coords_y);
        this.dusmanID = ID;
        this.dusmanAdi = ad;
        this.dusmanTur = tur;
        this.dusmanLokasyon = dusmanLokasyon;
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
    }
}
