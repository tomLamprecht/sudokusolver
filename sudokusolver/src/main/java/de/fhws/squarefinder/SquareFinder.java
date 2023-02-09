package de.fhws.squarefinder;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SquareFinder {

    private static final int MIN_BLACKNESS = 170;
    private static final int MIN_SIZE = 170;
    private static final int MAX_SIZE = 800;

    private static JFrame frame;
    private static JLabel label;

    public static BufferedImage findSquare(BufferedImage img){
        BufferedImage result = img;
        boolean[][] imgArray = getImgAsBooleanArray(img);

        result = checkSquares(img, result, imgArray);
        return result;

    }

    private static BufferedImage checkSquares(BufferedImage img, BufferedImage result, boolean[][] imgArray) {
        for (int size = MIN_SIZE; size < MAX_SIZE; size++) {
            for (int x = 0; x < imgArray.length - size; x++) {
                for (int y = 0; y < imgArray[x].length - size; y++) {
                    if(isSquare(imgArray,x,y,size)) {
                        result = img.getSubimage(x,y,size,size);
                    }
                }
            }
        }
        return result;
    }

    @NotNull
    private static boolean[][] getImgAsBooleanArray(BufferedImage img) {
        boolean[][] imgArray = new boolean[Math.min(img.getWidth(), img.getHeight())][Math.min(img.getWidth(), img.getHeight())];
        for (int x = 0; x < Math.min(img.getWidth(), img.getHeight()); x++) {
            for (int y = 0; y < Math.min(img.getWidth(), img.getHeight()); y++) {
                int color = img.getRGB(x, y) & 0xFF;
                imgArray[x][y] = color < MIN_BLACKNESS;
            }
        }
        return imgArray;
    }


    public static void main(String[] args) throws AWTException, InterruptedException {
        Thread.sleep(2000);
        Robot robot = new Robot();
        Rectangle rectangle = new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);

        BufferedImage img = robot.createScreenCapture(rectangle);
        display(findSquare(img));
    }

    private static boolean isSquare(boolean[][] data, int x, int y, int size) {
        for (int i = x; i < x + size ; i++) {
            if(!data[i][y])
                return false;
            if(!data[i][y+size])
                return false;
        }
        for (int i = y; i < y + size ; i++) {
            if(!data[x][i])
                return false;
            if(!data[x+size][i])
                return false;
        }
        return true;
    }


    public static void display(BufferedImage image) {
        if (frame == null) {
            frame = new JFrame();
            frame.setTitle("stained_image");
            frame.setSize(image.getWidth(), image.getHeight());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            label = new JLabel();
            label.setIcon(new ImageIcon(image));
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
        } else label.setIcon(new ImageIcon(image));
    }

}
