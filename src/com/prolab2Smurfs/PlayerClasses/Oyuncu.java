package com.prolab2Smurfs.PlayerClasses;

import static com.prolab2Smurfs.Utils.Constants.assetsBrainy;
import static com.prolab2Smurfs.Utils.Constants.assetsLazy;

public class Oyuncu extends Karakter {
    private String oyuncuID, oyuncuAdi, oyuncuTur;
    private int Skor;

    public Oyuncu() {
        super();
    }

    public Oyuncu(int Skor) {
        this.Skor = Skor;
    }

    public Oyuncu(String ID, String ad, String tur,int coords_x, int coords_y,int Skor) {
        super(ID, ad, tur, coords_x, coords_y);
        this.Skor = Skor;
        this.oyuncuAdi = ad;
        this.oyuncuID = ID;
        this.oyuncuTur = tur;
    }

    public int PuaniGoster () {
        return this.Skor;
    }

    public String getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(String oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;
    }

    public String getOyuncuTur() {
        return oyuncuTur;
    }

    public void setOyuncuTur(String oyuncuTur) {
        this.oyuncuTur = oyuncuTur;
    }

    public int getSkor() {
        return Skor;
    }

    public void setSkor(int skor) {
        Skor = skor;
    }

    @Override
    public String getImg() {
        if (this.oyuncuTur.equals("TEMBEL")) {
            return assetsLazy;
        }else {
            return assetsBrainy;
        }
    }
}
