package com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;

import static com.prolab2Smurfs.Utils.Constants.assetsBrainy;

public class GozlukluSirin extends Oyuncu {

    public GozlukluSirin() {
    }

    public GozlukluSirin(String ID, String ad, String tur, int coords_x, int coords_y, int Skor) {
        super(ID, ad, tur, coords_x, coords_y, Skor);
    }

    @Override
    public int getMovement() {
        return 1;
    }
}
