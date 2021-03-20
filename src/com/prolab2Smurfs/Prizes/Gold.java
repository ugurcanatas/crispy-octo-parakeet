package com.prolab2Smurfs.Prizes;

import com.prolab2Smurfs.Utils.Tiles;

import java.util.List;

public class Gold extends Prizes {
    private static long visibleFor = 5000;
    private static int maxSeconds = 20;
    private List<Tiles> PATHS;
    GoldSubclassInterface m;

    public Gold(List<Tiles> PATHS, OnTimerInterface onTimerInterface, GoldSubclassInterface m) {
        super(maxSeconds, "GOLD", onTimerInterface);
        this.m = m;
    }

    public void hideGold () {

    }

    public interface GoldSubclassInterface {
        void onGoldTimeout();
    }
}
