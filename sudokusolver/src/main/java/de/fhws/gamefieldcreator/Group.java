package de.fhws.gamefieldcreator;

import de.fhws.OCR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private static final double NUMBER_SIZE_PERCENTAGE = 0.35;

    List<Cell> cells = new ArrayList<>();
    private int sum;

    public Group() {
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

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void processGroupSum() {
        Collections.sort(cells);
        Cell topLeftCell = cells.get(0);
        int width = (int) (topLeftCell.getImg().getWidth() * NUMBER_SIZE_PERCENTAGE);
        int height = (int) (topLeftCell.getImg().getHeight() * NUMBER_SIZE_PERCENTAGE);
        sum = OCR.getInstance().getNumber(topLeftCell.getImg().getSubimage(0, 0, width, height));
    }

}
