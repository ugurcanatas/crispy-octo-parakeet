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
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Main extends Frame implements KeyListener {

    String c1,c2;
    ArrayList<Tiles> tileList = new ArrayList<>();

    Image smurfetteImage;
    Image playerImage;

    SingleNode[][] NODE_MATRIX;
    Tiles[][] TILE_MATRIX;
    int nodeRows = 0;
    int startx = 3;
    int starty = 0;
    int finishx = 6;
    int finishy = 5;
    SingleNode start = new SingleNode(0,startx,starty);
    SingleNode dest = new SingleNode(1,finishx,finishy);
    boolean solving = true;

    //Init Player Class
    Karakter MYSELF = new Oyuncu("MYSELF","MYSELF","GOZLUKLU",7,6,20);

    public Main() {
        //Start reading the harita.txt file
        MapReader m = new MapReader();
        m.readMap();
        //initialize 2D Node & 2D TileMap
        NODE_MATRIX = m.getNodes();
        nodeRows = NODE_MATRIX.length;
        TILE_MATRIX = new Tiles[nodeRows][nodeRows];

        for (int i = 0; i < nodeRows; i++) {
            for (int j = 0; j < nodeRows; j++) {
                System.out.print(NODE_MATRIX[i][j].getType() + " \t");
            }
            System.out.println("");
        }

        // This is going to determine y coords
        for (int i = 1; i <= nodeRows; i++) {
            // This is going to determine x coords
            for (int j = 1; j <= nodeRows; j++) {
                TILE_MATRIX[i-1][j-1] = new Tiles(BLOCK_W * j, BLOCK_H * i,
                        NODE_MATRIX[i-1][j-1].getType(),
                        j-1,i-1,
                        true);
                if (i > 11) {
                    TILE_MATRIX[i-1][j-1].setVisible(false);
                }
            }
        }

        NODE_MATRIX[6][5] = dest;
        NODE_MATRIX[3][0] = start;


        //new Algorithm().Dijkstra();

        //Dijkstra d = new Dijkstra(start,NODE_MATRIX);
        //d.start();

        System.out.println("");
        System.out.println("");
        for (int i = 0; i < nodeRows; i++) {
            for (int j = 0; j < nodeRows; j++) {
                System.out.print(NODE_MATRIX[i][j].getType() + " \t");
            }
            System.out.println("");
        }

        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(MYSELF.getImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("Smurfs");
        setSize(WINDOW_W,WINDOW_H);
        setVisible(true);
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

    @Override
    public void paint(Graphics g) {
        //Runs everytime when repaint() called
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D)g;

        for (int i = 0; i < nodeRows; i++) {
            for (int j = 0; j < nodeRows; j++) {
                Tiles tile = TILE_MATRIX[i][j];
                int tiletype = tile.getTILE_TYPE();
                boolean isVisibleTile = tile.isVisible();
                int x = tile.getX();
                int y = tile.getY();
                //Dont print last two rows that are not visible
                if (isVisibleTile) {
                    switch (tiletype) {
                        case TYPE_WALL:
                            graphics2D.setColor(Color.decode("#000000"));
                            Stroke s = graphics2D.getStroke();
                            graphics2D.setStroke(new BasicStroke(2));
                            graphics2D.drawRect(x,y,BLOCK_W,BLOCK_H);
                            graphics2D.setStroke(s);
                            graphics2D.setColor(Color.decode("#9e9e9e"));
                            graphics2D.fillRect(x,y,BLOCK_W,BLOCK_H);
                            break;
                        case TYPE_PATH:
                            graphics2D.setColor(Color.decode("#000000"));
                            Stroke p = graphics2D.getStroke();
                            graphics2D.setStroke(new BasicStroke(2));
                            graphics2D.drawRect(x,y,BLOCK_W,BLOCK_H);
                            graphics2D.setStroke(p);
                            //Draw path
                            graphics2D.setColor(Color.decode("#d9d9d9"));
                            graphics2D.fillRect(x,y,BLOCK_W,BLOCK_H);
                            break;
                        case TYPE_DESTINATION:
                            break;
                        case TYPE_START:
                            break;
                        case TYPE_FINAL:
                            break;
                    }
                    //graphics2D.setColor(Color.decode("#000000"));
                    //graphics2D.drawString(tile.getCoord_x() + "," + tile.getCoord_y(),x + 15,y + 15);
                }
            }
        }

        //graphics2D.drawImage(smurfetteImage,13*BLOCK_W,8*BLOCK_H,40,40,null);

        //DRAW MYSELF 👻
        //graphics2D.drawImage(playerImage,BLOCK_W * MYSELF.getCoords_x(),BLOCK_H * MYSELF.getCoords_y(),40,40,null);

        // Draw x,y coords
        //graphics2D.setColor(Color.decode("#000000"));
        //graphics2D.drawString("X: " + (MYSELF.getCoords_x()-1),500,525);

        //graphics2D.setColor(Color.decode("#000000"));
        //graphics2D.drawString("Y: " + (MYSELF.getCoords_y()-1),500,550);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public Boolean detectCollusion (int tempX, int tempY) {
        //calculate tile index
        int index = (tempX - 1) + 13*(tempY-1);
        //Get tile type
        int tiletype = tileList.get(index).getTILE_TYPE();
        //Check if it's a wall or not & return
        return tiletype == TYPE_WALL;
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

