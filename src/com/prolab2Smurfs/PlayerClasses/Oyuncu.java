package com.prolab2Smurfs.PlayerClasses;

public class Oyuncu extends Karakter {
    private String oyuncuID, oyuncuAdi, oyuncuTur;
    private int Skor;

    public Oyuncu() {
        super();
    }

    public Oyuncu(String ID, String ad, String tur,int coords_x, int coords_y,int Skor) {
        super(ID, ad, tur, coords_x, coords_y);
        this.Skor = Skor;
        this.oyuncuAdi = ad;
        this.oyuncuID = ID;
        this.oyuncuTur = tur;
    }

    public int PuaniGoster (String ENEMY_TYPE) {
        return this.Skor;
    }
}
