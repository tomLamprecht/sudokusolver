package de.fhws.player;

import org.chocosolver.solver.constraints.nary.nvalue.amnv.rules.R;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    private static final int KEY_PRESS_DELAY = 60;

    private int[][] gamefield;
    private Robot robot = new Robot();

    public Player(int[][] gamefield) throws AWTException {
        this.gamefield = gamefield;
    }

    private int transform(int i){
        return String.valueOf(i).charAt(0);
    }

    private void resetToTop() throws InterruptedException {
        for (int i = 0; i < 8; i++) {
            robot.keyPress(KeyEvent.VK_UP);
            Thread.sleep(KEY_PRESS_DELAY);
        }
    }

    public void play() throws InterruptedException {
        for (int i = 0; i < gamefield.length; i++) {
            for (int j = 0; j < gamefield[i].length; j++) {
                robot.keyPress(transform(gamefield[i][j]));
                Thread.sleep(KEY_PRESS_DELAY);
                robot.keyPress(KeyEvent.VK_DOWN);
            }
            resetToTop();
            Thread.sleep(KEY_PRESS_DELAY);
            robot.keyPress(KeyEvent.VK_RIGHT);
        }
    }

}
