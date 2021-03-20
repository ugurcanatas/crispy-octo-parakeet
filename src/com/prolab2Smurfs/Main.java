/*
* Author: Uğurcan Emre Ataş
* */

package com.prolab2Smurfs;

import com.prolab2Smurfs.Dijkstra.Dijkstra;
import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;
import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.Utils.MapReader;
import com.prolab2Smurfs.Utils.Tiles;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.prolab2Smurfs.Utils.Constants.*;

public class Main extends Frame implements KeyListener, Dijkstra.OnResult {
    Image smurfetteImage;
    Image playerImage;

    SingleNode[][] NODE_MATRIX;
    SingleNode[][] NODE_CLONED;
    Tiles[][] FIXED_TILES;
    int startx = 3;
    int starty = 0;
    SingleNode start = new SingleNode(TYPE_START,startx,starty);

    //Init Player Class
    public Karakter PLAYER;
    public MapReader mapReader = new MapReader();

    public HashMap<String, Dusman> enemiesHash;

    HashMap<String,SingleNode> movesTo = new HashMap<>();

    public Main() {
        setTitle("Smurfs");
        setSize(WINDOW_W,WINDOW_H);

        //Start reading the harita.txt file
        mapReader.readMap();
        PLAYER = mapReader.getPLAYER();
        //Load assets after initializing the PLAYER object
        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(PLAYER.getImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initialize 2D Node & 2D TileMap
        NODE_MATRIX = mapReader.getNodes();
        NODE_CLONED = mapReader.getCloned();

        FIXED_TILES = mapReader.getFixedTiles();

        enemiesHash = mapReader.getCharacterHash();
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            Dusman enemyObject = entry.getValue();
            print("ID 1: " + enemyObject.getDusmanID());
            print("ID 2: " + enemyObject.getID());
            //Solve on first load
            SingleNode start =
                    new SingleNode(TYPE_START,enemyObject.getDusmanLokasyon().getX(),enemyObject.getDusmanLokasyon().getY());
            Dijkstra d = new Dijkstra(start,enemyObject.getNODE_MATRIX(),this,enemyObject.getID());
            d.start();

            System.out.println("PRINTING FOR ENEMY MAP::");
            for (int i = 0; i < enemyObject.getNODE_MATRIX().length; i++) {
                for (int j = 0; j < enemyObject.getNODE_MATRIX()[0].length; j++) {
                    System.out.print(enemyObject.getNODE_MATRIX()[i][j].getType() + " \t");
                }
                System.out.println("");
            }
        }

        setFocusable(true);
        setVisible(true);
        repaint();
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

    private void drawCharacters (Graphics2D graphics2D) {
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            Dusman enemyObject = entry.getValue();
            int enemyX = enemyObject.getDusmanLokasyon().getX();
            int enemyY = enemyObject.getDusmanLokasyon().getY();
            graphics2D.setColor(Color.green);
            graphics2D.fillRect((enemyX + 1) * BLOCK_DIMEN, (enemyY + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);

            //Draw route
            for (int i = 0; i < enemyObject.getNODE_MATRIX().length; i++) {
                for (int j = 0; j < enemyObject.getNODE_MATRIX()[0].length; j++) {
                    SingleNode node = enemyObject.getNODE_MATRIX()[i][j];
                    if (node.getType()==TYPE_FINAL) {
                        int finalX = node.getX();
                        int finalY = node.getY();
                        //System.out.println("Drawing final path");
                        graphics2D.setColor(new Color(243, 123, 44, 50));
                        graphics2D.fillRect((finalX + 1) * BLOCK_DIMEN, (finalY + 1) * BLOCK_DIMEN, BLOCK_DIMEN, BLOCK_DIMEN);
                    }
                }
            }
        }
    }

    private void drawPlayer(Graphics2D graphics2D) {
        int playerX = PLAYER.getCoords_x();
        int playerY = PLAYER.getCoords_y();
        graphics2D.drawImage(playerImage,(playerX+1)*BLOCK_DIMEN,(playerY+1)*BLOCK_DIMEN,
                BLOCK_DIMEN,BLOCK_DIMEN,null);
    }

    //resets dijkstra maps for all enemy characters
    private void resetDijkstraArray () {
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            System.out.println("RESET ARRAY");
            Dusman enemyObject = entry.getValue();
            //enemyObject.reset();

            int playerX = PLAYER.getCoords_x();
            int playerY = PLAYER.getCoords_y();
            int enemyX = enemyObject.getDusmanLokasyon().getX();
            int enemyY = enemyObject.getDusmanLokasyon().getY();

            for (int i = 0; i < NODE_CLONED.length; i++) {
                for (int j = 0; j < NODE_CLONED[0].length; j++) {
                    enemyObject.setNodes(i,j,NODE_CLONED[i][j].getType());
                }
            }

            enemyObject.setNodes(playerX,playerY,TYPE_DESTINATION);
            System.out.println("ENEMY X: " + enemyX);
            System.out.println("ENEMY Y: " + enemyY);
            SingleNode start =
                    new SingleNode(TYPE_START,enemyX,enemyY);
            Dijkstra d = new Dijkstra(start,enemyObject.getNODE_MATRIX(),this,enemyObject.getID());
            d.start();


            //enemyObject.setNODE_MATRIX(NODE_CLONED);

            //enemyObject.setDestinationPoint(playerX,playerY);
            //enemyObject.setStartPoint(enemyX,enemyY);
            //enemyObject.start();
            //Dijkstra d = new Dijkstra(new SingleNode(TYPE_START,enemyX,enemyY),NODE_CLONED,this,enemyObject.getID());
            //d.start();

            System.out.println("UPDATE ");
            for (int i = 0; i < enemyObject.getNODE_MATRIX().length; i++) {
                for (int j = 0; j < enemyObject.getNODE_MATRIX()[0].length; j++) {
                    System.out.print(enemyObject.getNODE_MATRIX()[i][j].getType() + "\t");
                }
                System.out.println("");
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
        //DRAW PLAYER
        drawPlayer(graphics2D);
        //DRAW CHARACTERS AND ROUTE (DYNAMIC)
        drawCharacters(graphics2D);

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
                        playEnemy();
                        resetDijkstraArray();
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
                        playEnemy();
                        resetDijkstraArray();
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
                         playEnemy();
                         resetDijkstraArray();
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
                        playEnemy();
                        resetDijkstraArray();
                    }
                }
                break;
            default:
        }

        //repaint after moving the character
        repaint();
    }

    private void playEnemy () {
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            Dusman enemyObject = entry.getValue();
            String ID = enemyObject.getID();
            System.out.println("ID:  ===>" + ID);
            SingleNode move = movesTo.get(ID);
            System.out.println("MOVE TO THE POS: X: " + move.getX());
            System.out.println("MOVE TO THE POS: Y: " + move.getY());
            enemyObject.setDusmanLokasyon(new DusmanLokasyon(move.getX(),move.getY()));
        }
        movesTo = new HashMap<>();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void print (String m) {
        System.out.println(m);
    }

    @Override
    public void OnDijkstraResult(ArrayList<SingleNode> nodes, String FROM_ID) {
        System.out.println("RECEIVED MESSAGE IN HERE" + nodes.size());
        Dusman a = enemiesHash.get(FROM_ID);
        if (nodes.size() != 0) {
            SingleNode sNode = nodes.get(nodes.size() - 1);
            movesTo.put(a.getID(),sNode);
        }else {
            //Check here...
            SingleNode sNode = new SingleNode(TYPE_DESTINATION,PLAYER.getCoords_x(),PLAYER.getCoords_y());
            movesTo.put(a.getID(),sNode);
        }
        System.out.println("ENEMY RECEIVED WITH ID IN HASHMAP: " + a.getID());
    }
}

