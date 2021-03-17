package com.prolab2Smurfs.Dijkstra;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath {

    private int[][] MAP_ADJ;
    private int source;
    private List<Dugum> unvisited = new ArrayList<>();
    private List<Dugum> visited = new ArrayList<>();
    private Dugum current;

    public ShortestPath(int[][] MAP_ADJ, int source) {
        this.MAP_ADJ = MAP_ADJ;
        this.source = source;
    }

    public void dijkstra () {
        //set distances to all nodes. Source is 0, others are INFINITY
        int[] distances = new int[this.MAP_ADJ.length];
        for (int i = 0; i < distances.length; i++) {
            if (i == this.source) {
                distances[i] = 0;
                current = new Dugum(MAP_ADJ[this.source],this.source,true);
                visited.add(current);
            }else {
                distances[i] = Integer.MAX_VALUE;
                unvisited.add(new Dugum(MAP_ADJ[i],i,false));
            }
        }

        //printResult();
    }

    private void printResult () {
        System.out.println("Visited List");
        for (Dugum d : this.visited) {
            System.out.println("Visited NODE: " + d.getNodeIndex());
        }

        for (Dugum d : this.unvisited) {
            System.out.println("Unvisited NODE: " + d.getNodeIndex());
        }
    }
}
