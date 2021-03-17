package com.prolab2Smurfs;

import com.prolab2Smurfs.Dijkstra.Dijkstra;
import com.prolab2Smurfs.Dijkstra.Dugum;
import com.prolab2Smurfs.Dijkstra.ShortestPath;
import com.prolab2Smurfs.Dijkstra.SingleNode;
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

    int [][] MAP_MATRIX;

    SingleNode[][] NODE_MATRIX;

    //Init Player Class
    Karakter MYSELF = new Oyuncu("MYSELF","MYSELF","GOZLUKLU",7,6,20);

    public Main() {
        setTitle("Smurfs");
        setSize(600,600);
        setVisible(true);

        MapReader m = new MapReader();
        m.readMap();
        mapString = m.getMapString();
        c1 = m.getC1();
        c2 = m.getC2();
        mapList = m.getMapList();

        //print("MAP LIST \n" + mapList);
        //print("MAP LIST \n" + mapList.size());
        //print("MAP LIST \n" + mapList.get(0).split("\t").length);

        int rowLength = mapList.get(0).split("\t").length;
        print("ROW LEN" + rowLength);
        print("MAP LIST ROWS" + mapList.size());
        //Create 13by13 Matrix
        NODE_MATRIX = new SingleNode[rowLength][rowLength];

        // This is going to determine y coords
        for (int i = 1; i <= mapList.size(); i++) {
            String[] mapRow = mapList.get(i-1).split("\t");
            // This is going to determine x coords
            for (int j = 1; j <= mapRow.length; j++) {
                //print("MAP ROW IS 1 OR 0" + mapRow[j]);
                Tiles tile = new Tiles(BLOCK_W * j, BLOCK_H * i, mapRow[j-1],j-1,i-1);
                tileList.add(tile);

                if (mapRow[j-1].equals("1")) {
                    //path
                    NODE_MATRIX[i-1][j-1] = new SingleNode(3,j-1,i-1);
                }else {
                    //wall
                    NODE_MATRIX[i-1][j-1] = new SingleNode(2,j-1,i-1);
                }
            }
        }

        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(MYSELF.getImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); System.exit(0);
            }
        });
        addKeyListener(this);
    }

    public class Node {
        private int nType;
        private int lastX;
        private int lastY;
        private int x;
        private int y;
        private int dist;


        public Node(int type, int x, int y) {
            nType = type;
            this.x = x;
            this.y = y;
            dist = 0;
        }

        public int getX() {return x;}		//GET METHODS
        public int getY() {return y;}
        public int getLastX() {return lastX;}
        public int getLastY() {return lastY;}
        public int getType() {return nType;}
        private int getDist() {return dist;}

        public void setType(int type) {nType = type;}		//SET METHODS
        public void setLastNode(int x, int y) {lastX = x; lastY = y;}
        public void setDist(int dist){ this.dist = dist;}
    }

    public static void main(String[] args) {
        new Main();
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

