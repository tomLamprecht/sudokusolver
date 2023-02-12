package de.fhws.gamefieldcreator;

import java.awt.image.BufferedImage;

public class Splitter {

    private final static int SIZE = 9;

    public static BufferedImage[][] split(BufferedImage img) {
        double tileWidthAct = img.getWidth() / (double) (SIZE);
        double tileHeightAct = img.getHeight() / (double) (SIZE);
        int tileWidth = (int) Math.round(tileWidthAct);
        int tileHeight = (int) Math.round(tileHeightAct);

        BufferedImage[][] imageMap = new BufferedImage[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int x = (int) Math.round(i * tileWidthAct);
                int y = (int) Math.round(j * tileHeightAct);
                imageMap[i][j] = img.getSubimage(x, y, i != SIZE - 1 ? tileWidth : img.getWidth() - x, j != SIZE - 1 ? tileHeight : img.getHeight() - y);
            }
        }
        return imageMap;
    }

    public static Cell[][] createCells(BufferedImage img){
        BufferedImage[][] imgMap = split(img);
        Cell[][] cells = new Cell[imgMap.length][imgMap[0].length];
        for (int i = 0; i < imgMap.length; i++) {
            for (int j = 0; j < imgMap[i].length; j++) {
                cells[i][j] = new Cell(imgMap[i][j], i, j);
            }
        }
        return cells;
    }
}
