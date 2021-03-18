package com.prolab2Smurfs.Utils;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import static com.prolab2Smurfs.Utils.Constants.*;

public class MapReader {
    private String mapString = "";
    private ArrayList<String> mapList = new ArrayList<>();

    private SingleNode[][] nodes = new SingleNode[13][11];

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

            ArrayList<String[]> grid = new ArrayList<>();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] arr = data.split("\t");
                System.out.println("READING" + Arrays.toString(arr));
                //testing
                if (arr.length > 4) {
                    grid.add(arr);
                }//else part is characters
            }
            System.out.println("ROWS: " + grid.size());
            System.out.println("COLS: " + grid.get(0).length);

            //< 13
            for (int i = 0; i < grid.get(0).length; i++) {
                // < 11
                for (int j = 0; j < grid.size(); j++) {
                    String type = grid.get(j)[i];
                    if (type.equals("1")) {
                        nodes[i][j] = new SingleNode(TYPE_PATH,i,j);
                    } else {
                        nodes[i][j] = new SingleNode(TYPE_WALL,i,j);
                    }
                }
            }

            System.out.println("");
            for (int k = 0; k < 13; k++) {
                for (int z = 0; z < 11; z++) {
                    System.out.print(nodes[k][z].getType() + " \t");
                }
                System.out.println("");
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getRandomCharacter () {
        return new Random().nextInt(2);
    }


}
