package com.prolab2Smurfs.Prizes;

import com.prolab2Smurfs.Utils.Tiles;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Mushroom extends Prizes{
    MushroomSubclassInterface m;
    private static long visibleFor = 7000;
    private static int maxSeconds = 20;
    private List<Tiles> PATHS;
    private Timer timer;
    boolean isTimerActive = true;

    public Mushroom (List<Tiles> PATHS, OnTimerInterface onTimerInterface, MushroomSubclassInterface m) {
        super(maxSeconds, "MUSHROOM",onTimerInterface);
        this.m = m;
        this.PATHS = PATHS;
    }

    public Tiles pickTileToPopIn () {
        int rnd = new Random().nextInt(PATHS.size());
        System.out.println("PICKED POS: " + rnd);
        return PATHS.get(rnd);
    }

    public void hideShroom () {
        if (isTimerActive) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("RUNNİNG M TIMER");
                    m.onMushroomTimeout();//Dont forget cancel
                    //timer.cancel();
                }
            };

            timer = new Timer();
            timer.schedule(task,visibleFor);
        }
    }

    public void resetT(){
        isTimerActive = false;
        timer.cancel();
        timer.purge();

    }

    public interface MushroomSubclassInterface {
        void onMushroomTimeout();
    }

}
