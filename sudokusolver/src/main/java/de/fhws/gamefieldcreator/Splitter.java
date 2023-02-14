package de.fhws.gamefieldcreator;

import java.awt.image.BufferedImage;

public class Splitter {

    public final static int SUDOKU_SIZE = 9;

    public static BufferedImage[][] split(BufferedImage img) {
        double tileWidthAct = img.getWidth() / (double) (SUDOKU_SIZE);
        double tileHeightAct = img.getHeight() / (double) (SUDOKU_SIZE);
        int tileWidth = (int) Math.round(tileWidthAct);
        int tileHeight = (int) Math.round(tileHeightAct);

        BufferedImage[][] imageMap = new BufferedImage[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                int x = (int) Math.round(i * tileWidthAct);
                int y = (int) Math.round(j * tileHeightAct);
                imageMap[i][j] = img.getSubimage(x, y, i != SUDOKU_SIZE - 1 ? tileWidth : img.getWidth() - x, j != SUDOKU_SIZE - 1 ? tileHeight : img.getHeight() - y);
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
