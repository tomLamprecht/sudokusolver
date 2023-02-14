package de.fhws;

import de.fhws.gamefieldcreator.*;
import de.fhws.player.Player;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


public class Main {

    private static JFrame frame;
    private static JLabel label;

    public static void main(String[] args) throws AWTException, InterruptedException, IOException, TesseractException {
        Thread.sleep(2000);
          BufferedImage img = takeImage();
        ITesseract tesseract = new Tesseract();
        //display(img);
       // BufferedImage img = ImageIO.read(new File("testData.png"));
        img = SquareFinder.findSquare(img);
        Cell[][] cells = Splitter.createCells(img);
        List<Group> groups = GroupCreator.createGroups(cells);
        MathSolver solver = new MathSolver(cells, groups);
        System.out.println("solving starts...");
        int[][] result = solver.solve();
        Player player = new Player(result);
        player.play();
    }


    private static BufferedImage takeImage() throws AWTException {
        Rectangle rectangle = new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        return new Robot().createScreenCapture(rectangle);
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
