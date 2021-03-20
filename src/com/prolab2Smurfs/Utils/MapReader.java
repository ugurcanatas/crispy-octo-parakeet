package com.prolab2Smurfs.Utils;

import com.prolab2Smurfs.Dijkstra.Dijkstra;
import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.Main;
import com.prolab2Smurfs.PlayerClasses.Dusman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.Azman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.Gargamel;
import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.GozlukluSirin;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.TembelSirin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static com.prolab2Smurfs.Utils.Constants.*;

public class MapReader {
    private SingleNode[][] nodes;
    private SingleNode[][] cloned;
    private Tiles[][] fixedTiles;
    ArrayList<String> enemies = new ArrayList<>();
    ArrayList<Tiles> PATHS = new ArrayList<>();
    HashMap<String,Dusman> enemiesHashset = new HashMap<>();
    private Oyuncu PLAYER;

    HashMap<String,int[]> gates = new HashMap<>();

    public MapReader() {
        //add gates at the beginning
        gates.put("A",GATE_A_COORDS);
        gates.put("B",GATE_B_COORDS);
        gates.put("C",GATE_C_COORDS);
        gates.put("D",GATE_D_COORDS);

    }

    private Tiles[][] createFixedTiles(ArrayList<String[]> grid) {
        Tiles[][] tiles = new Tiles[grid.get(0).length][grid.size()];
        for (int i = 0; i < grid.get(0).length; i++) {
            for (int j = 0; j < grid.size(); j++) {
                String type = grid.get(j)[i];
                if (type.equals("1")) {
                    tiles[i][j] = new Tiles(i,j,TYPE_PATH,i*BLOCK_DIMEN,j*BLOCK_DIMEN);
                    PATHS.add(tiles[i][j]);
                } else {
                    tiles[i][j] = new Tiles(i,j,TYPE_WALL,i*BLOCK_DIMEN,j*BLOCK_DIMEN);
                }
            }
        }
        return tiles;
    }

    private SingleNode[][] createGridDynamic(ArrayList<String[]> grid, SingleNode[][] nodes) {
        nodes = new SingleNode[grid.get(0).length][grid.size()];
        for (int i = 0; i < grid.get(0).length; i++) {
            for (int j = 0; j < grid.size(); j++) {
                String type = grid.get(j)[i];
                if (type.equals("1")) {
                    nodes[i][j] = new SingleNode(TYPE_PATH,i,j);
                } else {
                    nodes[i][j] = new SingleNode(TYPE_WALL,i,j);
                }
            }
        }
        return nodes;
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

            fixedTiles = createFixedTiles(grid);
            cloned = createGridDynamic(grid,cloned);

            //Initialize 2D Nodes array & 2D FixedTiles array.
            //nodes = new SingleNode[grid.get(0).length][grid.size()];

            int player =  new Random().nextInt(2);
            //Select random player
            switch (player) {
                case 0 -> PLAYER = new GozlukluSirin("PLAYER-GOZLUK", "GOZLUKLU", "GOZLUKLU",
                        playerDefStartX, playerDefStartY, playerDefPoints);
                case 1 -> PLAYER = new TembelSirin("PLAYER-TEMBEL", "TEMBEL", "TEMBEL",
                        playerDefStartX, playerDefStartY, playerDefPoints);
            }




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
                SingleNode start = new SingleNode(TYPE_START,dusmanLokasyon.getX(),dusmanLokasyon.getY());
                SingleNode[][] newNodes = new SingleNode[grid.get(0).length][grid.size()];
                newNodes = createGridDynamic(grid,newNodes);
                newNodes[PLAYER.getCoords_x()][PLAYER.getCoords_y()]
                        = new SingleNode(TYPE_DESTINATION, PLAYER.getCoords_x(), PLAYER.getCoords_y());
                if (character.equals("Azman")) {
                    Dusman a = new Azman(ID,character, character,dusmanLokasyon,newNodes);
                    enemiesHashset.put(ID,a);
                }else {
                    Dusman a = new Gargamel(ID,character, character,dusmanLokasyon,newNodes);
                    enemiesHashset.put(ID,a);
                }
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Oyuncu getPLAYER() {
        return PLAYER;
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

    public ArrayList<Tiles> getPATHS() {
        return PATHS;
    }
}
