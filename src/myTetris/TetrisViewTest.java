package myTetris;

// code copied from Simon Lucas
// code copied by Udo Kruschwitz
// code altered by Oliver Holland

import myTetris.game.TetrisGame;
import utilities.JEasyFrame;

import javax.swing.*;

/**
 * Created by Ollie on 26/11/2014.
 */

public class TetrisViewTest extends JApplet {
    /**
     * The main class, essentially handles applet and initial execution
     */

    public void init() {
        new JEasyFrame("Oliver Holland - 1305261", new TetrisGame(10, 20)).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        createGame();
    }

    private static void createGame() {

        new JEasyFrame("Oliver Holland - 1305261", new TetrisGame(10,20));
    }
}