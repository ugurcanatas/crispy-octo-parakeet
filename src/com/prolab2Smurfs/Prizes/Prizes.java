package com.prolab2Smurfs.Prizes;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Prizes{
    private long seconds;
    Timer timer;
    String TYPE;
    OnTimerInterface onTimerInterface;

    public Prizes(long seconds, String TYPE, OnTimerInterface onTimerInterface) {
        this.TYPE = TYPE;
        this.onTimerInterface = onTimerInterface;
        this.seconds = seconds;
        //int secondsRandom = new Random().nextInt(seconds);
        timer = new Timer();
        timer.schedule(new TimerClass(),5000,seconds*1000);
    }

    class TimerClass extends TimerTask {
        @Override
        public void run() {
            if (TYPE.equals("GOLD")) {
                onTimerInterface.onTimerUpdateGold();
            }else {
                onTimerInterface.onTimerUpdateMushroom();
            }
        }
    }

    public interface OnTimerInterface {
        void onTimerUpdateGold();
        void onTimerUpdateMushroom();
    }

    /*public interface OnGoldInterface {
        void onTimerUpdateGold();
    }

    public interface OnMushroomInterface {
        void onTimerUpdateMushroom();
    }*/
}
