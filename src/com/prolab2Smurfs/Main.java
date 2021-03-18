/*
* Author: Uğurcan Emre Ataş
* */

package com.prolab2Smurfs;

import com.prolab2Smurfs.Dijkstra.Dijkstra;
import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.GozlukluSirin;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.TembelSirin;
import com.prolab2Smurfs.Utils.MapReader;
import com.prolab2Smurfs.Utils.Tiles;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Main extends Frame implements KeyListener, Dijkstra.OnResult {
    Dijkstra algo;

    Image smurfetteImage;
    Image playerImage;

    SingleNode[][] NODE_MATRIX;
    SingleNode[][] NODE_CLONED;
    Tiles[][] FIXED_TILES;
    int nodeRows = 0;
    int startx = 3;
    int starty = 0;
    SingleNode start = new SingleNode(TYPE_START,startx,starty);

    //Init Player Class
    Karakter PLAYER = new Oyuncu();
    MapReader mapReader = new MapReader();

    public Main() {
        setTitle("Smurfs");
        setSize(WINDOW_W,WINDOW_H);
        setVisible(true);

        //Start reading the harita.txt file
        mapReader.readMap();
        int character = mapReader.getRandomCharacter();
        //Select random player
        switch (character) {
            case 0 -> PLAYER = new Oyuncu("PLAYER", "PLAYER", "GOZLUKLU",
                    playerDefStartX, playerDefStartY, playerDefPoints);
            case 1 -> PLAYER = new Oyuncu("PLAYER", "PLAYER", "TEMBEL",
                    playerDefStartX, playerDefStartY, playerDefPoints);
        }
        print("KARAKTER PLAYER: " + character);

        //initialize 2D Node & 2D TileMap
        NODE_MATRIX = mapReader.getNodes();
        NODE_CLONED = mapReader.getCloned();

        FIXED_TILES = mapReader.getFixedTiles();
        nodeRows = NODE_MATRIX.length;

        //init start & destination nodes
        NODE_MATRIX[3][0] = new SingleNode(TYPE_START,3,0); //Change this to enemy player
        NODE_MATRIX[6][5] = new SingleNode(TYPE_DESTINATION,6,5);


        algo = new Dijkstra(start,NODE_MATRIX,this);
        algo.start();

        System.out.println("");
        for (int i = 0; i < nodeRows; i++) {
            for (int j = 0; j < NODE_MATRIX[0].length; j++) {
                System.out.print(NODE_MATRIX[i][j].getType() + " \t");
            }
            System.out.println("");
        }

        repaint();

        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(PLAYER.getImg()));
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

    public static void main(String[] args) {
        new Main();
    }

    private void drawFixedTiles (Graphics2D graphics2D) {
        for (Tiles[] fixed_tile : FIXED_TILES) {
            for (int j = 0; j < FIXED_TILES[0].length; j++) {
                Tiles tile = fixed_tile[j];
                int tiletype = tile.getTILE_TYPE();
                int x = tile.getX();
                int y = tile.getY();
                switch (tiletype) {
                    case TYPE_WALL -> {
                        graphics2D.setColor(Color.decode("#000000"));
                        Stroke s = graphics2D.getStroke();
                        graphics2D.setStroke(new BasicStroke(2));
                        graphics2D.drawRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                        graphics2D.setStroke(s);
                        graphics2D.setColor(Color.decode("#9e9e9e"));
                        graphics2D.fillRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                    case TYPE_PATH -> {
                        graphics2D.setColor(Color.decode("#000000"));
                        Stroke p = graphics2D.getStroke();
                        graphics2D.setStroke(new BasicStroke(2));
                        graphics2D.drawRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                        graphics2D.setStroke(p);
                        graphics2D.setColor(Color.decode("#d9d9d9"));
                        graphics2D.fillRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                }
            }
        }
    }

    private void drawDynamics (Graphics2D graphics2D) {
        for (SingleNode[] node_matrix : NODE_MATRIX) {
            for (int j = 0; j < NODE_MATRIX[0].length; j++) {
                SingleNode node = node_matrix[j];
                int tiletype = node.getType();
                int x = node.getX();
                int y = node.getY();
                switch (tiletype) {
                    case TYPE_START -> {
                        graphics2D.setColor(Color.green);
                        graphics2D.fillRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                    case TYPE_DESTINATION -> {
                        //this is the player
                        graphics2D.setColor(Color.red);
                        graphics2D.fillRect((PLAYER.getCoords_x() + 1) * BLOCK_DIMEN, (PLAYER.getCoords_y() + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                    case TYPE_FINAL -> {
                        graphics2D.setColor(new Color(243, 123, 44, 50));
                        graphics2D.fillRect((x + 1) * BLOCK_DIMEN, (y + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        //Runs everytime when repaint() called
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D)g;

        //DRAW FIXED TILES FIRST
        drawFixedTiles(graphics2D);
        //DRAW CHARACTERS AND ROUTE (DYNAMIC)
        drawDynamics(graphics2D);

        /*
        graphics2D.drawImage(smurfetteImage,13*BLOCK_DIMEN,8*BLOCK_DIMEN,40,40,null);
        //DRAW PLAYER 👻
        graphics2D.drawImage(playerImage,BLOCK_DIMEN * (PLAYER.getCoords_x()+1),BLOCK_DIMEN * (PLAYER.getCoords_y()+1),40,40,null);
*/
        //NODE_MATRIX[PLAYER.getCoords_x()][PLAYER.getCoords_y()] = new SingleNode(TYPE_FINAL,PLAYER.getCoords_x(),PLAYER.getCoords_y());
        // Draw x,y coords
        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("X: " + (PLAYER.getCoords_x()),650,100);
        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("Y: " + (PLAYER.getCoords_y()),650,120);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
    * @param tempX: Temporary X value of the player
    * @param tempY: Temporary Y value of the player
    * Looks at the FIXED_TILES array and checks the movement is permitted.
    * */
    public Boolean detectCollusion (int tempX, int tempY) {
        int tiletype = FIXED_TILES[tempX][tempY].getTILE_TYPE();
        return tiletype == TYPE_WALL;
    }

    /**
    * Called after player moves.
    * Steps ----
    * -> After player moves, enemy players needs to move.
    * -> Update enemy player coordinates after step 1
    * -> Reset NODE_MATRIX to first state using NODE_CLONED (which contains just walls and paths)
    * -> Update player and enemies on NODE_MATRIX array of arrays
    * -> Reset the Dijkstra class & create a new Object. Then repaint changes;
    * */
    private void clearAndStart () {
        System.out.println("RESET");
        for (int i = 0; i < NODE_CLONED.length; i++) {
            for (int j = 0; j < NODE_CLONED[0].length; j++) {
                System.out.print(NODE_CLONED[i][j].getType() + " \t");
                int type = NODE_CLONED[i][j].getType();
                int x = NODE_CLONED[i][j].getX();
                int y = NODE_CLONED[i][j].getY();
                NODE_MATRIX[i][j] = new SingleNode(type,x,y);
            }
            System.out.println("");
        }
        int playerX = PLAYER.getCoords_x();
        int playerY = PLAYER.getCoords_y();
        NODE_MATRIX[playerX][playerY] = new SingleNode(TYPE_DESTINATION,playerX,playerY);
        NODE_MATRIX[3][0] = new SingleNode(TYPE_START,3,0);
        algo = null;
        algo = new Dijkstra(NODE_MATRIX[3][0],NODE_MATRIX,this);
        algo.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //assign coords to temp vars
        int tempX = PLAYER.getCoords_x();
        int tempY = PLAYER.getCoords_y();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                tempX++;
                if (tempX < 13) {
                    if (!detectCollusion(tempX,tempY)) {
                        int cX = PLAYER.getCoords_x();
                        cX++;
                        PLAYER.setCoords_x(cX);
                        clearAndStart();
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                tempX--;
                if (tempX > -1) {
                    if (!detectCollusion(tempX,tempY)) {
                        int cX = PLAYER.getCoords_x();
                        cX--;
                        PLAYER.setCoords_x(cX);
                        clearAndStart();
                    }
                }
                break;
            case KeyEvent.VK_UP:
                tempY--;
                 if (tempY > -1) {
                     if (!detectCollusion(tempX,tempY)) {
                         int cY = PLAYER.getCoords_y();
                         cY--;
                         PLAYER.setCoords_y(cY);
                         clearAndStart();
                     }
                 }
                break;
            case KeyEvent.VK_DOWN:
                tempY++;
                if (tempY < 11) {
                    if (!detectCollusion(tempX,tempY)) {
                        int cY = PLAYER.getCoords_y();
                        cY++;
                        PLAYER.setCoords_y(cY);
                        clearAndStart();
                    }
                }
                break;
            default:
        }
        //repaint after moving the character
        //repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void print (String m) {
        System.out.println(m);
    }

    @Override
    public void OnDijkstraResult(ArrayList<SingleNode> nodes) {
        System.out.println("RESULT RECEIVED NODES");
        for (SingleNode node : nodes) {
            System.out.println("POSITION XY: " + node.getX() + " - " + node.getY());
            System.out.println("POSITION XY LAST: " + node.getLastX() + " - " + node.getLastY());
            System.out.println("POSITION TYPE: " + node.getType());
            System.out.println("########");
        }
    }
}

