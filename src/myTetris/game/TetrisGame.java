package myTetris.game;

import myTetris.game.controller.KeyController;
import myTetris.game.model.BoardModel;
import myTetris.game.model.PlayerModel;
import myTetris.game.view.BoardView;
import myTetris.game.view.PlayerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Ollie on 26/11/2014.
 */

public class TetrisGame extends JPanel {
    BoardView boardView;

    public TetrisGame(int w, int h) {
        final BoardModel boardModel;

        PlayerModel playerModel = new PlayerModel();
        boardModel = new BoardModel(playerModel, w, h);


        //Set up layout of panel.
        this.setLayout(new BorderLayout());
        boardView = new BoardView(boardModel);
        this.add(boardView, BorderLayout.CENTER);
        this.add(new PlayerView(playerModel), BorderLayout.SOUTH);
        this.setFocusable(true);


        // Set up key controls.
        addKeyListener(new KeyController(boardModel));
        boardModel.startGame();

        // Pause off focus, unpause on focus.
        this.addFocusListener(new FocusListener() {

            @Override
            /**
             * Unpause on focus.
             */
            public void focusGained(FocusEvent e) {
                boardModel.unPause();
            }

            /**
             * Pause when window not in focus.
             */
            @Override
            public void focusLost(FocusEvent e) {
                boardModel.pause();
            }
        });
    }

    /**
     * Set preferred size to all display of this panel, boardModel plus some extra room for PlayerModel panel.
     *
     * @return The preferred size as a Dimension.
     */
    public Dimension getPreferredSize() {

        int w = (int) boardView.getPreferredSize().getWidth();
        int h = (int) boardView.getPreferredSize().getHeight() + 50;
        return new Dimension(w, h);
    }

}
