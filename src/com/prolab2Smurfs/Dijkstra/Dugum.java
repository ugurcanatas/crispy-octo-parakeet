package com.prolab2Smurfs.Dijkstra;

import java.util.Comparator;

public class Dugum implements Comparator<Dugum> {
    private int node;
    private int cost;

    public Dugum() {
    }

    public Dugum(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }

    @Override
    public int compare(Dugum o1, Dugum o2) {
        return Integer.compare(o1.cost, o2.cost);
        /*
        * if (o1.cost < o2.cost) {
            return -1;
        }
        if (o1.cost > o2.cost) {
            return 1;
        }
        return 0;
        * */
    }
}
