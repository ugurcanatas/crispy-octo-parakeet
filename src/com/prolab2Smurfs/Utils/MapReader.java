package com.prolab2Smurfs.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.prolab2Smurfs.Utils.Constants.mapPath;

public class MapReader {
    private String c1 = "";
    private String c2 = "";
    private String mapString = "";
    private ArrayList<String> mapList = new ArrayList<>();

    public MapReader() {
    }

    public String getC1() {
        return c1;
    }

    public String getC2() {
        return c2;
    }

    public String getMapString() {
        return mapString;
    }

    public ArrayList<String> getMapList() {
        return mapList;
    }

    public void readMap () {
        File file = new File(mapPath);
        try {
            Scanner reader = new Scanner(file);
            int i = 0;
            while (reader.hasNextLine()) {
                i++;
                String data = reader.nextLine();
                //System.out.println("Line " + i + " - " + data);
                if (i == 1) {
                    c1 = data;
                }else if (i == 2) {
                    c2 = data;
                }else {
                    mapList.add(data);
                    mapString = mapString.concat(data);
                }
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
