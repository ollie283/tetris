package myTetris.game.view;

/**
 * Created by Ollie on 26/11/2014.
 */

import myTetris.game.model.BoardModel;
import myTetris.mino.Mino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

public class BoardView extends JComponent implements Observer {
    // Store model to get values.
    private BoardModel model;
    // Store length of square sides.
    private final static int SQUARESIZE = 20;

    /**
     * Creates boardModel.
     * @param model  Model containing logic of boardModel.
     */
    public BoardView(BoardModel model) {
        this.model = model;
        model.addObserver(this);
        this.addMouseListener(new MouseController());
    }

    /**
     * Draw specified mino onto boardModel.
     * @param g    Graphics object to draw with
     * @param mino Mino to draw on boardModel.
     */
    private void drawMino(Graphics g, Mino mino) {
        g.setColor(mino.getColor());

        for (Point p : mino.getPositions()) {
            g.fill3DRect((int) p.getX() * SQUARESIZE, (int) p.getY() * SQUARESIZE,
                    SQUARESIZE, SQUARESIZE, true);
        }
    }

    /**
     * Draw the current falling mino.
     * @param g Graphics object to draw with.
     */
    private void drawCurrentMino(Graphics g) {
        drawMino(g, model.getCurrentFallingMino());
    }

    /**
     * Draw ghost mino.
     * @param g Graphics object to draw with.
     */
    private void drawGhostMino(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Make transparent.
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        drawMino(g, model.getGhost());
    }

    /**
     * Paint current state of boardModel.
     * @param g Graphics object to paint with.
     */
    public void paintComponent(Graphics g) {
        Color[][] blockCol = model.getBlockColors();
        for (int i = 0; i < model.getWidth(); i++) {
            for (int j = 0; j < model.getHeight(); j++) {
                g.setColor(blockCol[i][j]);
                g.fill3DRect(i * SQUARESIZE, j * SQUARESIZE,
                        SQUARESIZE, SQUARESIZE, true);
            }
        }

        if (model.getCurrentFallingMino() != null) {
            drawCurrentMino(g);
            drawGhostMino(g);
        }
    }

    /**
     * Set preferred size to all display of entire boardModel.
     * @return The preferred size as a Dimension.
     */
    public Dimension getPreferredSize() {
        return new Dimension(model.getWidth() * SQUARESIZE, model.getHeight() * SQUARESIZE);
    }

    /**
     * React to model update.
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }

    /**
     * Handle mouse events.
     */
    class MouseController extends MouseAdapter {

        /**
         * On click check for nature of click and run appropriate method.
         */
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if (e.isMetaDown()) {
                model.moveMinoRight();
            } else if (e.isAltDown()) {
                model.rotateMinoClockwise();
            } else {
                model.moveMinoLeft();
            }
        }
    }
}
