package de.fhws.gamefieldcreator;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Comparable<Cell>{

    private static final double PERCENTAGE = 0.20;
    private final BufferedImage img;
    private final List<Openings> openingsList = new ArrayList<>();
    private final int x, y;

    public Cell(BufferedImage img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;

        if (hasTopOpening(img))
            openingsList.add(Openings.TOP);
        if (hasBotOpening(img))
            openingsList.add(Openings.BOTTOM);
        if (hasLeftOpening(img))
            openingsList.add(Openings.LEFT);
        if (hasRightOpening(img))
            openingsList.add(Openings.RIGHT);

    }

    public List<Openings> getOpeningsList() {
        return openingsList;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean hasTopOpening(BufferedImage img) {
        for (int y = (int) (img.getHeight() * PERCENTAGE); y >= 0; y--) {
            int baseColor = getGrey(img, (int) (img.getWidth() * PERCENTAGE), y);
            for (int x = (int) (img.getWidth() * PERCENTAGE); x < img.getWidth() * (1 - PERCENTAGE); x++) {
                if ((getGrey(img, x, y) - baseColor) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasBotOpening(BufferedImage img) {
        for (int y = (int) (img.getHeight() * (1 - PERCENTAGE)); y < img.getHeight(); y++) {
            int baseColor = getGrey(img, (int) (img.getWidth() * PERCENTAGE), y);
            for (int x = (int) (img.getWidth() * PERCENTAGE); x < img.getWidth() * (1 - PERCENTAGE); x++) {
                if ((getGrey(img, x, y) - baseColor) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasLeftOpening(BufferedImage img) {
        for (int x = (int) (img.getWidth() * PERCENTAGE); x >= 0; x--) {
            int baseColorGrey = getGrey(img, x, (int) (img.getHeight() * PERCENTAGE));

            for (int y = (int) (img.getHeight() * PERCENTAGE); y < img.getHeight() * (1 - PERCENTAGE); y++) {
                int grey = getGrey(img, x, y);
                if (grey - baseColorGrey != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getGrey(BufferedImage img, int x, int y) {
        Color baseColor = new Color(img.getRGB(x, y));
        return (baseColor.getRed() + baseColor.getGreen() + baseColor.getBlue()) / 3;
    }


    private boolean hasRightOpening(BufferedImage img) {
        for (int x = (int) (img.getWidth() * (1 - PERCENTAGE)); x < img.getWidth(); x++) {
            int baseColor = getGrey(img, x, (int) (img.getHeight() * PERCENTAGE));
            for (int y = (int) (img.getHeight() * PERCENTAGE); y < img.getHeight() * (1 - PERCENTAGE); y++) {
                if ((getGrey(img, x, y) - baseColor) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int compareTo(@NotNull Cell o) {
        if(o.y < y)
            return 1;
        if(o.y == y)
            return Integer.compare(x, o.x);
        return -1;
    }

    public enum Openings {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT;
    }
}
