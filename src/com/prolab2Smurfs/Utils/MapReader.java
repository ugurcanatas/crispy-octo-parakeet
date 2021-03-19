package com.prolab2Smurfs.Utils;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.Azman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.Gargamel;
import com.prolab2Smurfs.PlayerClasses.Karakter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.prolab2Smurfs.Utils.Constants.*;

public class MapReader{
    private SingleNode[][] nodes;
    private SingleNode[][] cloned;
    private Tiles[][] fixedTiles;
    ArrayList<String> enemies = new ArrayList<>();
    HashMap<String,Dusman> enemiesHashset = new HashMap<>();

    HashMap<String,int[]> gates = new HashMap<>();

    public MapReader() {
        //add gates at the beginning
        gates.put("A",GATE_A_COORDS);
        gates.put("B",GATE_B_COORDS);
        gates.put("C",GATE_C_COORDS);
        gates.put("D",GATE_D_COORDS);

    }

    public SingleNode[][] getNodes() {
        return nodes;
    }

    public SingleNode[][] getCloned() {
        return cloned;
    }

    public HashMap<String, Dusman> getCharacterHash() {
        return enemiesHashset;
    }

    public Tiles[][] getFixedTiles() {
        return fixedTiles;
    }

    public void readMap () {
        File file = new File(mapPath);
        try {
            Scanner reader = new Scanner(file);

            ArrayList<String[]> grid = new ArrayList<>();
            //Reading the text file
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                //Split read line
                String[] arr = data.split("\t");
                //Look at the lines that is bigger than certain number
                if (arr.length > 4) {
                    grid.add(arr);
                }else {
                    //Characters.
                    enemies.add(arr[0]);
                }
            }

            //Close file reader
            reader.close();

            for (int i = 0; i < enemies.size(); i++) {
                String[] splitted = enemies.get(i).split(",");
                String character = splitted[0].split(":")[1];
                String gate = splitted[1].split(":")[1];
                System.out.println("ENEMY INFO:");
                System.out.println("CHARACTER: " + character);
                System.out.println("GATE: " + gate);
                int[] gatecoords = gates.get(gate);
                DusmanLokasyon dusmanLokasyon = new DusmanLokasyon(gatecoords[0],gatecoords[1]);
                String ID = character.concat("-" + i);
                if (character.equals("Azman")) {
                    Dusman a = new Azman(ID,character, character,dusmanLokasyon);
                    enemiesHashset.put(ID,a);
                }else {
                    Dusman a = new Gargamel(ID,character, character,dusmanLokasyon);
                    enemiesHashset.put(ID,a);
                }

            }

            //Initialize 2D Nodes array & 2D FixedTiles array.
            nodes = new SingleNode[grid.get(0).length][grid.size()];
            cloned = new SingleNode[grid.get(0).length][grid.size()];
            fixedTiles = new Tiles[grid.get(0).length][grid.size()];

            // First loop is rows (13 length)
            for (int i = 0; i < grid.get(0).length; i++) {
                // Second loop is cols (11 length)
                for (int j = 0; j < grid.size(); j++) {
                    //Pick string. (1 or 0)
                    String type = grid.get(j)[i];
                    //If string is 1 than it's a path else it's a wall.
                    //Add to nodes & fixed tiles 2d arrays.
                    if (type.equals("1")) {
                        nodes[i][j] = new SingleNode(TYPE_PATH,i,j);
                        cloned[i][j] = new SingleNode(TYPE_PATH,i,j);
                        fixedTiles[i][j] = new Tiles(i,j,TYPE_PATH,i*BLOCK_DIMEN,j*BLOCK_DIMEN);
                    } else {
                        nodes[i][j] = new SingleNode(TYPE_WALL,i,j);
                        cloned[i][j] = new SingleNode(TYPE_WALL,i,j);
                        fixedTiles[i][j] = new Tiles(i,j,TYPE_WALL,i*BLOCK_DIMEN,j*BLOCK_DIMEN);
                    }
                }
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Returns 1 or 0 to pick a random player
    public int getRandomCharacter () {
        return new Random().nextInt(2);
    }
}
