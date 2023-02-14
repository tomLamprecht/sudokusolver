package de.fhws.gamefieldcreator;

import de.fhws.OCR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private static final double NUMBER_SIZE_PERCENTAGE_WIDTH = 0.28;
    private static final double NUMBER_SIZE_PERCENTAGE_HEIGHT = 0.25;
    private static final int LEFT_PIXEL_OFFSET = 3;
    private static final int TOP_PIXEL_OFFSET = 3;

    List<Cell> cells = new ArrayList<>();
    private int sum;
    private boolean distinct;

    public Group(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean containsIndex(int index){
        return cells.stream().map(Cell::getIndex).anyMatch(i -> i == index);
    }

    public Group(List<Cell> cells, int sum) {
        this.cells = cells;
        this.sum = sum;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void addAll(List<Cell> cells) {
        this.cells.addAll(cells);
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void processGroupSum() {
        Collections.sort(cells);
        Cell topLeftCell = cells.get(0);
        int width = (int) (topLeftCell.getImg().getWidth() * NUMBER_SIZE_PERCENTAGE_WIDTH);
        int height = (int) (topLeftCell.getImg().getHeight() * NUMBER_SIZE_PERCENTAGE_HEIGHT);
        sum = OCR.getInstance().getNumber(topLeftCell.getImg().getSubimage(LEFT_PIXEL_OFFSET, TOP_PIXEL_OFFSET, width, height));
        missleading7hack(String.valueOf(sum));
    }

    private void missleading7hack(String sumString) {
        //Sadly the OCR sometimes detects a 7 at the end of the String
        //This hack just detects if its even possible there could be a 7 at the end and if not remove it
        if(sumString.charAt(sumString.length()-1) == '7'){
            if(sum > 45 || sum == 37 && cells.size() < 6 || sum == 27 && cells.size() < 4 || sum == 17 && cells.size() < 2){
                sum = Integer.parseInt(sumString.substring(0,1));
                System.out.println("Changed " + sumString + " to " + sum);
            }
        }
    }

    public int getSum() {
        return sum;
    }
}
