package com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;

public class Puan extends Oyuncu {

    int Skor;
    public Puan(int Skor) {
        super(Skor);
        this.Skor = Skor;
    }

    public void scoreGold() {
        this.Skor += 5;
    }

    public void scoreMushroom() {
        this.Skor += 50;
    }

    public void lowerScoreAzman() {
        this.Skor -= 5;
    }

    public void lowerScoreGargamel() {
        this.Skor -= 15;
    }

    @Override
    public int PuaniGoster() {
        return this.Skor;
    }
}
