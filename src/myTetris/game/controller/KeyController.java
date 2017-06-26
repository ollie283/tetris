package myTetris.game.controller;

import myTetris.game.model.BoardModel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Ollie on 01/12/2014.
 */

public class KeyController extends KeyAdapter {
    BoardModel boardModel;

    public KeyController(BoardModel board) {
        this.boardModel = board;
    }

    @Override
    /**
     * React to key pressed event during game.
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                boardModel.rotateMinoClockwise();
                break;
            case KeyEvent.VK_RIGHT:
                boardModel.moveMinoRight();
                break;
            case KeyEvent.VK_LEFT:
                boardModel.moveMinoLeft();
                break;
            case KeyEvent.VK_DOWN:
                boardModel.moveToBottom();
                break;
        }
    }
}
