package com.prolab2Smurfs.Prizes;

import com.prolab2Smurfs.Utils.Tiles;

import java.util.*;

public class Gold extends Prizes {
    private static int point = 5;
    private static long visibleFor = 5000;
    private static int maxSeconds = 10;
    private List<Tiles> PATHS;
    private List<Tiles> PICKED;
    private List<Integer> pickedNumbers;
    GoldSubclassInterface m;
    private Timer timer;
    boolean isTimerActive = true;

    public Gold(List<Tiles> PATHS, OnTimerInterface onTimerInterface, GoldSubclassInterface m) {
        super(maxSeconds, "GOLD", onTimerInterface);
        this.m = m;
        this.PATHS = PATHS;
        this.PICKED = new ArrayList<>();
        this.pickedNumbers = new ArrayList<>();
    }

    public List<Tiles> pickGoldToPopIn(){
        //Pick 5 golds
        pickedNumbers = new ArrayList<>();
        PICKED = new ArrayList<>();
        Random rnd = new Random();
        while (pickedNumbers.size() < 5) {
            int picked = rnd.nextInt(PATHS.size());
            System.out.println("PICKING GOLD POSITIONS: " + picked);
            pickedNumbers.add(picked);
        }

        for (int i : pickedNumbers) {
            Tiles t = PATHS.get(i);
            PICKED.add(t);
        }

        return PICKED;
    }

    public void resetT(){
        isTimerActive = false;
        timer.cancel();
        timer.purge();
    }

    public void hideGold () {
        if (isTimerActive) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    m.onGoldTimeout();
                }
            };

            timer = new Timer();
            timer.schedule(task,visibleFor);
        }
    }

    public interface GoldSubclassInterface {
        void onGoldTimeout();
    }
}
