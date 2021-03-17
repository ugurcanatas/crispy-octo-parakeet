package com.prolab2Smurfs;

import com.prolab2Smurfs.Dijkstra.Dugum;
import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.GozlukluSirin;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.TembelSirin;
import com.prolab2Smurfs.Utils.MapReader;
import com.prolab2Smurfs.Utils.Tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Main extends Frame implements KeyListener {

    String mapString = "";
    ArrayList<String> mapList;
    String c1,c2;
    ArrayList<Tiles> tileList = new ArrayList<>();

    Image smurfetteImage;
    Image playerImage;

    //Init Player Class
    Karakter MYSELF = new Oyuncu("MYSELF","MYSELF","GOZLUKLU",7,6,20);

    public Main() {
        MapReader m = new MapReader();
        m.readMap();
        mapString = m.getMapString();
        c1 = m.getC1();
        c2 = m.getC2();
        mapList = m.getMapList();

        //print("MAP LIST \n" + mapList);
        //print("MAP LIST \n" + mapList.size());
        //print("MAP LIST \n" + mapList.get(0).split("\t").length);

        int [][] MAP_MATRIX = new int[mapList.size()][mapList.size()];
        // This is going to determine y coords
        for (int i = 0; i < mapList.size(); i++) {
            String[] mapRow = mapList.get(i).split("\t");
            // This is going to determine x coords
            for (int j = 0; j < mapRow.length; j++) {
                //print("MAP ROW IS 1 OR 0" + mapRow[j]);
                Tiles tile = new Tiles(BLOCK_W * (j+1), BLOCK_H * (i+1), mapRow[j],j,i);
                tileList.add(tile);
            }
        }
        int a = 0;
        List<List<Integer>> MAP_NODES = new ArrayList<List<Integer>>();

        //Create Matrix
        //you did it you crazy mf
        for (int i = 0; i < tileList.size(); i++){
            //Düğüm ekle
            String tiletype = tileList.get(i).getTILE_TYPE();
            List<Integer> intList = new ArrayList<>();
            MAP_NODES.add(intList);
            if (tiletype.equals("PATH")) {
                //komşuluk kontrolü
                if (i < 13) {
                    //ilk rowdaki komşuluklara bakarken üstte komşuları olamaz ve yanda da olamaz
                    //Sadece sağ ve sola bak
                    int left = i - 1;
                    int right = i + 1;
                    int down = i + 13;
                    Tiles tilesLeft = tileList.get(left);
                    Tiles tilesRight = tileList.get(right);
                    Tiles tilesDown = tileList.get(down);
                    for (int j = 0; j < tileList.size(); j++){
                        if (j == left && tilesLeft.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(left,1);
                        }
                        else if (j == right && tilesRight.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(right,1);
                        }
                        else if (j == down && tilesDown.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(down,1);
                        }else {
                            MAP_NODES.get(i).add(0);
                        }
                    }
                }
                //Son row
                else if ( i > 131) {
                    int left = i - 1;
                    int right = i + 1;
                    int up = i - 13;
                    Tiles tilesLeft = tileList.get(left);
                    Tiles tilesRight = tileList.get(right);
                    Tiles tilesUp = tileList.get(up);
                    for (int j = 0; j < tileList.size(); j++){
                        if (j == left && tilesLeft.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(left,1);
                        }
                        else if (j == right && tilesRight.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(right,1);
                        }
                        else if (j == up && tilesUp.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(up,1);
                        }else {
                            MAP_NODES.get(i).add(0);
                        }
                    }
                }
                //en soldaki
                else if (i == 65) {
                    int right = i + 1;
                    Tiles tilesRight = tileList.get(right);
                    for (int j = 0; j < tileList.size(); j++){
                        if (j == right && tilesRight.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(right,1);
                        }else {
                            MAP_NODES.get(i).add(0);
                        }
                    }
                }

                //en sağdaki
                else if (i == 103) {
                    int left = i + 1;
                    Tiles tilesLeft = tileList.get(left);
                    for (int j = 0; j < tileList.size(); j++){
                        if (j == left && tilesLeft.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(left,1);
                        }
                        MAP_NODES.get(i).add(0);
                    }
                }
                //other tiles
                else {
                    int left = i - 1;
                    int right = i + 1;
                    int down = i + 13;
                    int up = i - 13;
                    Tiles tilesLeft = tileList.get(left);
                    Tiles tilesRight = tileList.get(right);
                    Tiles tilesDown = tileList.get(down);
                    Tiles tilesUp = tileList.get(up);
                    for (int j = 0; j < tileList.size(); j++){
                        if (j == left && tilesLeft.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(left,1);
                        }
                        else if (j == right && tilesRight.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(right,1);
                        }
                        else if (j == down && tilesDown.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(down,1);
                        }
                        else if (j == up && tilesUp.getTILE_TYPE().equals("PATH")) {
                            MAP_NODES.get(i).add(up,1);
                        }else {
                            MAP_NODES.get(i).add(0);
                        }
                    }
                }
            }else {
                // row'u 0 la doldur
                for (int j = 0; j < tileList.size(); j++){
                    MAP_NODES.get(i).add(0);
                }
            }
        }

        print("TOTAL NODE SIZE: " + tileList.size());

        /*for (List<Integer> list : MAP_NODES) {
            for (int i : list) {
                System.out.print(i + "\t");
            }
            System.out.print("\n");
        }*/

        int[][] MAP_ADJ = MAP_NODES.stream().map(  u  ->  u.stream().mapToInt(i->i).toArray()  ).toArray(int[][]::new);


        print("MAP ADJ: ROW L \n" + MAP_ADJ.length);
        print("MAP ADJ: COL L \n" + MAP_ADJ[0].length);
        for (int[] map_matrix :  MAP_ADJ) {
            for (int j = 0; j <  MAP_ADJ[0].length; j++) {
                //System.out.print(map_matrix[j] + "\t");
            }
            //System.out.println("");
        }

        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(MYSELF.getImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTitle("Smurfs");
        setSize(600,600);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
        addKeyListener(this);
    }


    @Override
    public void paint(Graphics g) {
        //Runs everytime when repaint() called
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D)g;

        //Draw map tiles
        for (int i = 0; i < tileList.size(); i++) {
            String TILE_TYPE = tileList.get(i).getTILE_TYPE();
            int [] xy = tileList.get(i).getXY();
            if (TILE_TYPE.equals("WALL")) {
                graphics2D.setColor(Color.decode("#000000"));
                Stroke s = graphics2D.getStroke();
                graphics2D.setStroke(new BasicStroke(2));
                graphics2D.drawRect(xy[0],xy[1],BLOCK_W,BLOCK_H);
                graphics2D.setStroke(s);
                //Draw wall
                graphics2D.setColor(Color.decode("#9e9e9e"));
                graphics2D.fillRect(xy[0],xy[1],BLOCK_W,BLOCK_H);
                graphics2D.setColor(Color.decode("#000000"));
                graphics2D.drawString(String.valueOf(i),xy[0] + 15,xy[1] + 30);
            }else {
                //Draw stroke
                graphics2D.setColor(Color.decode("#000000"));
                Stroke s = graphics2D.getStroke();
                graphics2D.setStroke(new BasicStroke(2));
                graphics2D.drawRect(xy[0],xy[1],BLOCK_W,BLOCK_H);
                graphics2D.setStroke(s);


                //Draw path
                graphics2D.setColor(Color.decode("#d9d9d9"));
                graphics2D.fillRect(xy[0],xy[1],BLOCK_W,BLOCK_H);
            }
        }

        graphics2D.drawImage(smurfetteImage,13*BLOCK_W,8*BLOCK_H,40,40,null);

        //DRAW MYSELF 👻
        graphics2D.drawImage(playerImage,BLOCK_W * MYSELF.getCoords_x(),BLOCK_H * MYSELF.getCoords_y(),40,40,null);

        // Draw x,y coords
        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("X: " + (MYSELF.getCoords_x()-1),500,525);

        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("Y: " + (MYSELF.getCoords_y()-1),500,550);

    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public Boolean detectCollusion (int tempX, int tempY) {
        //calculate tile index
        int index = (tempX - 1) + 13*(tempY-1);
        //Get tile type
        String tiletype = tileList.get(index).getTILE_TYPE();
        //Check if it's a wall or not & return
        return tiletype.equals("WALL");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //assign coords to temp vars
        int tempX = MYSELF.getCoords_x();
        int tempY = MYSELF.getCoords_y();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                tempX++;
                if (!detectCollusion(tempX,tempY)) {
                    int cX = MYSELF.getCoords_x();
                    cX++;
                    MYSELF.setCoords_x(cX);
                }
                break;
            case KeyEvent.VK_LEFT:
                tempX--;
                if (!detectCollusion(tempX,tempY)) {
                    int cX = MYSELF.getCoords_x();
                    cX--;
                    MYSELF.setCoords_x(cX);
                }
                break;
            case KeyEvent.VK_UP:
                tempY--;
                if (!detectCollusion(tempX,tempY)) {
                    int cY = MYSELF.getCoords_y();
                    cY--;
                    MYSELF.setCoords_y(cY);
                }
                break;
            case KeyEvent.VK_DOWN:
                tempY++;
                if (!detectCollusion(tempX,tempY)) {
                    int cY = MYSELF.getCoords_y();
                    cY++;
                    MYSELF.setCoords_y(cY);
                }
                break;
            default:
        }
        //repaint after moving the character
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void print (String m) {
        System.out.println(m);
    }
}

