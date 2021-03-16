package com.prolab2Smurfs;

import com.prolab2Smurfs.PlayerClasses.Karakter;
import com.prolab2Smurfs.PlayerClasses.Oyuncu;
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

    int defX = 7;
    int defY = 6;
    int defIndex = 71;

    String mapString = "";
    ArrayList<String> mapList;
    String c1,c2;
    ArrayList<Tiles> tileList = new ArrayList<>();

    Image smurfetteImage;

    //Init Player Class
    Karakter MYSELF = new Oyuncu("MYSELF","MYSELF","DOTGÖZ",7,6,20);

    public Main() {
        print("Map Received: \n" + mapString );
        MapReader m = new MapReader();
        m.readMap();
        mapString = m.getMapString();
        c1 = m.getC1();
        c2 = m.getC2();
        mapList = m.getMapList();

        print("MAP LIST \n" + mapList);
        print("MAP LIST \n" + mapList.size());
        print("MAP LIST \n" + mapList.get(0).split("\t").length);

        // This is going to determine y coords
        for (int i = 0; i < mapList.size(); i++) {
            String[] mapRow = mapList.get(i).split("\t");
            // This is going to determine x coords
            for (int j = 0; j < mapRow.length; j++) {
                print("MAP ROW IS 1 OR 0" + mapRow[j]);
                Tiles tile = new Tiles(BLOCK_W * (j+1), BLOCK_H * (i+1), mapRow[j],j,i);
                tileList.add(tile);
            }
        }

        try {
            smurfetteImage = ImageIO.read(new File(assetsSmurfette));
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

        graphics2D.drawImage(smurfetteImage,13*BLOCK_H,9*BLOCK_W,40,40,null);

        //DRAW MYSELF 👻
        graphics2D.setColor(Color.blue);
        graphics2D.fillRect(BLOCK_W * MYSELF.getCoords_x(),BLOCK_H * MYSELF.getCoords_y(),BLOCK_W,BLOCK_H);

        // Draw x,y coords
        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("X: " + MYSELF.getCoords_x(),500,525);

        graphics2D.setColor(Color.decode("#000000"));
        graphics2D.drawString("Y: " + MYSELF.getCoords_y(),500,550);


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

