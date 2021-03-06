/*
* Author: Uğurcan Emre Ataş
* */

package com.prolab2Smurfs;

import com.prolab2Smurfs.Dijkstra.SingleNode;
import com.prolab2Smurfs.PlayerClasses.Dusman;
import com.prolab2Smurfs.PlayerClasses.DusmanSubClasses.DusmanLokasyon;
import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.PlayerClasses.OyuncuSubClasses.Puan;
import com.prolab2Smurfs.Prizes.Gold;
import com.prolab2Smurfs.Prizes.Mushroom;
import com.prolab2Smurfs.Prizes.Prizes;
import com.prolab2Smurfs.Utils.MapReader;
import com.prolab2Smurfs.Utils.Tiles;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
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

public class Main extends Frame implements KeyListener, Prizes.OnTimerInterface, Mushroom.MushroomSubclassInterface, Gold.GoldSubclassInterface, Puan.OnPointInterface, Karakter.DijkstraResultInterface {
    Image smurfetteImage,
            playerImage,
            mushroomImage,
            goldImage,
            brickwallImage,
            azraelImage,
            gargamelImage;
    Clip goldAppearedSound,
            mushroomAppeared,
            mushroomDisappear,
            gameOverSound;

    SingleNode[][] NODE_MATRIX;
    SingleNode[][] NODE_CLONED;
    Tiles[][] FIXED_TILES;
    int startx = 3;
    int starty = 0;
    SingleNode start = new SingleNode(TYPE_START,startx,starty);

    //Init Player Class
    public Karakter PLAYER;
    public Puan KARAKTER_PUAN;
    public MapReader mapReader = new MapReader();

    public HashMap<String, Dusman> enemiesHash;

    HashMap<String,SingleNode> movesTo = new HashMap<>();

    Mushroom mushroom;
    Tiles mushroomTile;
    boolean isShroomVisible = false;

    Gold gold;
    List<Tiles> goldTiles = new ArrayList<>();
    boolean isGoldVisible = false;
    boolean isGoldPickedUp = false;

    private void readMap(){
        mapReader.readMap();
        PLAYER = mapReader.getPLAYER();
        KARAKTER_PUAN = new Puan(playerDefPoints, this);

        //initialize 2D Node & 2D TileMap
        //NODE_MATRIX = mapReader.getNodes();
        NODE_CLONED = mapReader.getCloned();
        FIXED_TILES = mapReader.getFixedTiles();

        enemiesHash = mapReader.getCharacterHash();
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            Dusman enemyObject = entry.getValue();
            print("ID 1: " + enemyObject.getDusmanID());
            print("ID 2: " + enemyObject.getID());
            //Solve on first load
            enemyObject.setDijkstraResultInterface(this);
            enemyObject.enKisaYoluBul();
            printMap(enemyObject.getNODE_MATRIX());
        }
    }

    private void readAssets() {
        //Load assets after initializing the PLAYER object
        try {
            goldAppearedSound = AudioSystem.getClip();
            goldAppearedSound.open(AudioSystem.getAudioInputStream(new File(soundGoldAppeared)));
            mushroomAppeared = AudioSystem.getClip();
            mushroomAppeared.open(AudioSystem.getAudioInputStream(new File(soundMushroomAppeared)));
            mushroomDisappear = AudioSystem.getClip();
            mushroomDisappear.open(AudioSystem.getAudioInputStream(new File(soundMushroomDisappear)));
            gameOverSound = AudioSystem.getClip();
            gameOverSound.open(AudioSystem.getAudioInputStream(new File(soundGameOver)));

            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
            playerImage = ImageIO.read(new File(PLAYER.getImg()));
            mushroomImage = ImageIO.read(new File(assetsShroom));
            goldImage = ImageIO.read(new File(assetsCoin));
            brickwallImage = ImageIO.read(new File(assetsBrickwall));
            azraelImage = ImageIO.read(new File(assetsAzrael));
            gargamelImage = ImageIO.read(new File(assetsGargamel));
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    private void initPrizes () {
        mushroom = new Mushroom(mapReader.getPATHS(),this,this);
        gold = new Gold(mapReader.getPATHS(),this,this);
    }


    public Main() {
        setTitle("Smurfs");
        setSize(WINDOW_W,WINDOW_H);
        //Start reading the harita.txt file
        readMap();
        readAssets();

        setFocusable(true);
        setVisible(true);
        repaint();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        addKeyListener(this);

        initPrizes();
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
                        graphics2D.drawImage(brickwallImage,(x+1)*BLOCK_DIMEN,(y+1)*BLOCK_DIMEN,
                                BLOCK_DIMEN,BLOCK_DIMEN,null);
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
            String enemyType = enemyObject.getDusmanTur();
            System.out.println("DRAW DUSMNA NAME: " + enemyObject.getDusmanTur());
            if (enemyType.equals(GARGAMEL)) {
                graphics2D.drawImage(gargamelImage,(enemyX+1)*BLOCK_DIMEN,(enemyY+1)*BLOCK_DIMEN,
                        BLOCK_DIMEN,BLOCK_DIMEN,null);
            }else if (enemyType.equals(AZRAEL)) {
                graphics2D.drawImage(azraelImage,(enemyX+1)*BLOCK_DIMEN,(enemyY+1)*BLOCK_DIMEN,
                        BLOCK_DIMEN,BLOCK_DIMEN,null);
            }

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

        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.setFont(new Font("Noto Serif",Font.PLAIN,16));
        graphics2D.drawString("Oyuncu: ", 750,100);
        graphics2D.setColor(Color.decode("#fcba03"));
        graphics2D.setFont(new Font("Noto Serif",Font.BOLD,18));
        graphics2D.drawString(PLAYER.getAd(), 750,120);
        graphics2D.setColor(Color.decode("#000"));
        graphics2D.setFont(new Font("Noto Serif",Font.BOLD,16));
        graphics2D.drawString("PUAN:", 750,160);
        graphics2D.setColor(Color.decode("#ed2874"));
        graphics2D.setFont(new Font("Noto Serif",Font.BOLD,18));
        graphics2D.drawString(String.valueOf(KARAKTER_PUAN.PuaniGoster()), 750,180);
    }

    //resets dijkstra maps for all enemy characters
    private void resetDijkstraArray () {
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            System.out.println("RESET ARRAY");
            Dusman enemyObject = entry.getValue();

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
            enemyObject.setCoords_x(enemyX);
            enemyObject.setCoords_y(enemyY);
            System.out.println("ENEMY X: " + enemyX);
            System.out.println("ENEMY Y: " + enemyY);
            enemyObject.enKisaYoluBul();

            printMap(enemyObject.getNODE_MATRIX());
        }
    }

    @Override
    public void paint(Graphics g) {
        //Runs everytime when repaint() called
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;

        //DRAW FIXED TILES FIRST
        drawFixedTiles(graphics2D);

        if (isShroomVisible) {
            int shroomX = mushroomTile.getX();
            int shroomY = mushroomTile.getY();
            graphics2D.drawImage(mushroomImage,(shroomX+1)*BLOCK_DIMEN + 10,(shroomY+1)*BLOCK_DIMEN + 10,
                    30,30,null);
        }

        if (isGoldVisible) {
            for (Tiles t : goldTiles) {
                int goldX = t.getX();
                int goldY = t.getY();
                graphics2D.drawImage(goldImage,(goldX+1)*BLOCK_DIMEN + 10,(goldY+1)*BLOCK_DIMEN + 10,
                        30,30,null);
            }
        }
        //DRAW PLAYER
        drawPlayer(graphics2D);
        //DRAW CHARACTERS AND ROUTE (DYNAMIC)
        drawCharacters(graphics2D);
        //Final Point
        graphics2D.drawImage(smurfetteImage,13*BLOCK_DIMEN,8*BLOCK_DIMEN,BLOCK_DIMEN,BLOCK_DIMEN,null);
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
        System.out.println("LENNNNN" + FIXED_TILES[0].length);
        //assign coords to temp vars
        int tempX = PLAYER.getCoords_x();
        int tempY = PLAYER.getCoords_y();
        int increment = PLAYER.getMovement();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                tempX = tempX + increment;
                if (tempX < FIXED_TILES.length) {
                    if (!detectCollusion(tempX,tempY)) {
                        int cX = PLAYER.getCoords_x();
                        cX = cX + increment;
                        PLAYER.setCoords_x(cX);
                        playEnemy();
                        resetDijkstraArray();
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                tempX = tempX - increment;
                if (tempX > -1) {
                    if (!detectCollusion(tempX,tempY)) {
                        int cX = PLAYER.getCoords_x();
                        cX = cX - increment;
                        PLAYER.setCoords_x(cX);
                        playEnemy();
                        resetDijkstraArray();
                    }
                }
                break;
            case KeyEvent.VK_UP:
                tempY = tempY - increment;
                 if (tempY > -1) {
                     if (!detectCollusion(tempX,tempY)) {
                         int cY = PLAYER.getCoords_y();
                         cY = cY - increment;
                         PLAYER.setCoords_y(cY);
                         playEnemy();
                         resetDijkstraArray();
                     }
                 }
                break;
            case KeyEvent.VK_DOWN:
                tempY = tempY + increment;
                if (tempY < FIXED_TILES[0].length) {
                    if (!detectCollusion(tempX,tempY)) {
                        System.out.println("CAN GO DOWN");
                        int cY = PLAYER.getCoords_y();
                        cY = cY + increment;
                        PLAYER.setCoords_y(cY);
                        playEnemy();
                        resetDijkstraArray();
                    }
                }
                break;
            default:
        }

        if (PLAYER.getCoords_x() == 12 && PLAYER.getCoords_y() == 7) {
            //You win the game
            System.out.println("YOU WIN THE GAME");
            restartApp( "Oyunu kazandınız, tekrar başlamak isterseniz butona basınız.","Oyun Bitti");
        }
        checkMusroom();
        checkGold();
        checkCollusionWithEnemy();

        System.out.println("TOPLAM PUAN: " + KARAKTER_PUAN.PuaniGoster());

        //repaint after moving the character
        repaint();
    }

    public void checkGold () {
        if (isGoldVisible) {
            List<Tiles> removeIndexes = new ArrayList<>();
            for (int i = 0; i < goldTiles.size(); i++) {
                int gX = goldTiles.get(i).getX();
                int gY = goldTiles.get(i).getY();
                if (PLAYER.getCoords_x() == gX && PLAYER.getCoords_y() == gY) {
                    removeIndexes.add(goldTiles.get(i));
                    KARAKTER_PUAN.scoreGold();
                    isGoldPickedUp = true;
                }
            }
            if (removeIndexes.size() > 0) {
                if (goldTiles.size() > 0) {
                    goldTiles.remove(removeIndexes.get(0));
                }else {
                    //All golds removed
                    isGoldVisible = false;
                    goldTiles = new ArrayList<>();
                }
            }
        }
    }

    public void checkMusroom () {
        if (isShroomVisible) {
            System.out.println("SHROOM VISIBLE");
            int mX = mushroomTile.getX();
            int mY = mushroomTile.getY();
            if (PLAYER.getCoords_x() == mX && PLAYER.getCoords_y() == mY) {
                KARAKTER_PUAN.scoreMushroom();
                isShroomVisible = false;
                System.out.println("HIDE MUSHROOM AFTER GETTING IT");
                mushroomTile = null;
            }
        }
    }

    public void checkCollusionWithEnemy() {
        int playerX = PLAYER.getCoords_x();
        int playerY = PLAYER.getCoords_y();
        for (Map.Entry<String, Dusman> entry : enemiesHash.entrySet()) {
            Dusman enemyObject = entry.getValue();
            String enemyType = enemyObject.getDusmanTur();
            int enemyX = enemyObject.getDusmanLokasyon().getX();
            int enemyY = enemyObject.getDusmanLokasyon().getY();
            int[] startGate = enemyObject.getGate();
            String ID = enemyObject.getID();
            SingleNode move = movesTo.get(ID);
            //if there is a collusion
            if (playerX == enemyX && playerY == enemyY) {
                enemyObject.setDusmanLokasyon(new DusmanLokasyon(startGate[0],startGate[1]));
                move.setX(startGate[0]);
                move.setY(startGate[1]);

                //Check enemy type before point loss.
                if (enemyType.equals(GARGAMEL)) {
                    KARAKTER_PUAN.lowerScoreGargamel();
                    System.out.println("GARGAMEL CAUGHT YOU ! " + KARAKTER_PUAN.PuaniGoster());
                }else {
                    KARAKTER_PUAN.lowerScoreAzman();
                    System.out.println("AZMAN CAUGHT YOU ! " + KARAKTER_PUAN.PuaniGoster());
                }
            }
        }
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
    public void onTimerUpdateGold() {
        goldAppearedSound.start();
        System.out.println("SHOW 5 GOLDS");
        isGoldVisible = true;
        goldTiles = gold.pickGoldToPopIn();
        repaint();
        gold.hideGold();
    }

    @Override
    public void onTimerUpdateMushroom() {
        if (!isShroomVisible) {
            mushroomAppeared.start();
        }
        isShroomVisible = true;
        System.out.println("SHOW A MUSHROOM !");
        mushroomTile = mushroom.pickTileToPopIn();
        repaint();
        mushroom.hideShroom();
    }

    @Override
    public void onMushroomTimeout() {
        mushroomAppeared.setFramePosition(0);
        if (isShroomVisible) {
            mushroomDisappear.setFramePosition(0);
            mushroomDisappear.start();
        }
        isShroomVisible = false;
        System.out.println("HIDE MUSHROOM");
        mushroomTile = null;
        repaint();
    }

    @Override
    public void onGoldTimeout() {
        goldAppearedSound.setFramePosition(0);
        isGoldVisible = false;
        goldTiles = new ArrayList<>();
        System.out.println("HIDE GOLD");
        repaint();
    }

    @Override
    public void onPointUpdate(int Skor) {
        System.out.println("POINT UPDATE RECEIVED: " + Skor);
        if (Skor <= 0) {
            System.out.println("GAME OVER");
            gameOverSound.setFramePosition(0);
            gameOverSound.start();
            restartApp("Oyun Bitti","Oyunu kaybettiniz, tekrar başlamak için butona basın.");
        }
    }

    private void restartApp (String message, String title) {
        Object[] options = {"Evet, yeniden başlat"};
        int n = JOptionPane.showOptionDialog(
                this, message, title,
                JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (n == 0) {
            //restart
            mushroom.resetTimer();
            mushroom.resetT();
            gold.resetTimer();
            gold.resetT();
            mushroomTile = null;
            goldTiles = new ArrayList<Tiles>();
            readMap();
            initPrizes();
            repaint();
        }
    }

    @Override
    public void onDijkstraResult(ArrayList<SingleNode> result, String ID) {
        System.out.println("RECEIVED MESSAGE IN HERE" + result.size());
        Dusman a = enemiesHash.get(ID);
        if (result.size() != 0) {
            SingleNode sNode = result.get(result.size() - a.getMovement());
            movesTo.put(a.getID(),sNode);
        }else {
            //Check here...
            SingleNode sNode = new SingleNode(TYPE_DESTINATION,PLAYER.getCoords_x(),PLAYER.getCoords_y());
            movesTo.put(a.getID(),sNode);
        }
        System.out.println("ENEMY RECEIVED WITH ID IN HASHMAP: " + a.getID());
    }

    private void printMap(SingleNode[][] MATRIX) {
        System.out.println("PRINTING MAP::");
        for (SingleNode[] matrix : MATRIX) {
            for (int j = 0; j < MATRIX[0].length; j++) {
                System.out.print(matrix[j].getType() + " \t");
            }
            System.out.println("");
        }
    }
}

