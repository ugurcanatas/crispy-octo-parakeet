package com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;

public class Puan extends Oyuncu {
    interface PuanGosterInterface {
        int PuanGoster();
    }

    @Override
    public int PuaniGoster(String ENEMY_TYPE) {
        if (ENEMY_TYPE.equals("GARGAMEL")) {

        }else if (ENEMY_TYPE.equals("AZMAN")) {

        }
        return super.PuaniGoster(ENEMY_TYPE);
    }
}
