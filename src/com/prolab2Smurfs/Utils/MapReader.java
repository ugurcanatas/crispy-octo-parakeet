package com.prolab2Smurfs.Utils;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.prolab2Smurfs.Utils.Constants.*;

public class MapReader {
    private String mapString = "";
    private ArrayList<String> mapList = new ArrayList<>();

    private SingleNode[][] nodes = new SingleNode[13][13];

    public MapReader() {
    }

    public String getMapString() {
        return mapString;
    }

    public ArrayList<String> getMapList() {
        return mapList;
    }

    public SingleNode[][] getNodes() {
        return nodes;
    }

    public void readMap () {
        File file = new File(mapPath);
        try {
            Scanner reader = new Scanner(file);
            int i = 0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                //System.out.println("Line " + i + " - " + data);
                String rowF = data.split("\t")[0];
                if (rowF.equals("1") || rowF.equals("0")) {
                    String [] row = data.split("\t");
                    for (int j = 0; j < row.length; j++) {
                        if (row[j].equals("1")) {
                            nodes[i][j] = new SingleNode(TYPE_PATH,i,j);
                        }else {
                            //WALL
                            nodes[i][j] = new SingleNode(TYPE_WALL,i,j);
                        }
                    }
                    i++;
                }
            }
            for (int k = 0; k < 13; k++) {
                nodes[11][k] = new SingleNode(TYPE_WALL,k,11);
                nodes[12][k] = new SingleNode(TYPE_WALL,k,12);
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
