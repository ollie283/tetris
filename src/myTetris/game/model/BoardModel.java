package myTetris.game.model;

/**
 * Created by Ollie on 27/11/2014.
 */

import myTetris.mino.Mino;
import myTetris.mino.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

public class BoardModel extends Observable {

    private final static Color BACKGROUNDCOLOR = Color.BLACK;
    private Color[][] blockColors;
    private int[][] blockLocations;
    private int width, height;
    private Mino currentFallingMino;
    private Mino minoType;
    private int speed = 350;
    private boolean gameOver = false;

    Timer speedTimer;
    PlayerModel playerModel;

    /**
     * Get color of each block stored in blockLocations grid.
     *
     * @return color for each block.
     */
    public Color[][] getBlockColors() {
        return blockColors;
    }

    /**
     * Get width of grid.
     *
     * @return width of grid.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get height of grid.
     *
     * @return height of grid.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get current falling Mino.
     *
     * @return The current falling Mino.
     */
    public Mino getCurrentFallingMino() {
        return currentFallingMino;
    }

    /**
     * Initialise color array to be all background colors.
     */
    private void initColorArray() {
        blockColors = new Color[width][height];

        for (int i = 0; i < blockColors.length; i++) {
            for (int j = 0; j < blockColors[i].length; j++) {
                blockColors[i][j] = BACKGROUNDCOLOR;
            }
        }
    }

    public BoardModel(PlayerModel player, Mino pieceType, int w, int h) {
        this.playerModel = player;
        this.minoType = pieceType;
        this.width = w;
        this.height = h;
        initColorArray();
        blockLocations = new int[w][h];
    }

    public BoardModel(PlayerModel player, int w, int h) {
        this(player, new Tetromino("O", 0, 0), w, h);
    }

    /**
     * Checks if a given array of positions (of a Mino) is valid at the moment.
     *
     * @param pointArr array of points to check for validity.
     * @return Whether positions could be drawn logically in a game of Tetris.
     */
    private boolean isCollision(Point[] pointArr) {
        //Check for overlap on existing blocks and boundaries of boardModel, not including top edge.
        for (Point p : pointArr) {
            int x = (int) p.getX();
            int y = (int) p.getY();
            if (x >= width || x < 0 || y >= height || (y >= 0 && blockLocations[x][y] != 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if moving right is a valid move and if so moves current mino right.
     */
    public void moveMinoRight() {
        Mino currentToRight = currentFallingMino.copy();
        currentToRight.moveRight();
        if (!isCollision(currentToRight.getPositions()) && !gameOver) {
            currentFallingMino.moveRight();
            notifyObservers();
        }
    }

    /**
     * Tests if moving left is a valid move and if so moves current mino left.
     */
    public void moveMinoLeft() {
        Mino currentToLeft = currentFallingMino.copy();
        currentToLeft.moveLeft();
        if (!isCollision(currentToLeft.getPositions()) && !gameOver) {
            currentFallingMino.moveLeft();
            notifyObservers();
        }
    }

    /**
     * Tests if rotating clockwise is a valid move and if so rotates the current mino left.
     */
    public void rotateMinoClockwise() {
        //See if rotated mino would collide.
        Mino preCheck = currentFallingMino.copy();
        preCheck.rotateClockwise();
        if (!isCollision(preCheck.getPositions()) && !gameOver) {
            currentFallingMino.rotateClockwise();
            notifyObservers();
        }
    }

    /**
     * Commit mino to boardModel via blockLocations and blockColors grids.
     *
     * @param mino mino to commit.
     */
    private void commitMinoToBoard(Mino mino) {
        for (Point p : mino.getPositions()) {
            int x = (int) p.getX();
            int y = (int) p.getY();
            //may be commiting final piece that is half on boardModel.
            if (y >= 0) {
                blockLocations[x][y] = 1;
                blockColors[x][y] = mino.getColor();
            } else {
                gameOver = true;
            }
        }
    }


    /**
     * Add current Tetris piece to boardModel.
     */
    private void commitCurrentToBoard() {
        commitMinoToBoard(currentFallingMino);
    }


    /**
     * Get next piece from whatever Mino subclass is stored inside minoType.
     *
     * @return The next mino to start falling.
     */
    private Mino nextMino() {
        return minoType.nextPiece(width / 2, 0);
    }

    /**
     * Checks if a given row contains any blocks.
     *
     * @param row row to check.
     * @return result of check.
     */
    private boolean isClearLine(int row) {
        for (int i = 0; i < width; i++) {
            if (blockLocations[i][row] != 0) return false;
        }
        return true;
    }

    /**
     * Checks if a given row is full of blocks.
     *
     * @param row row to check.
     * @return result of check.
     */
    private boolean isFullLine(int row) {
        for (int i = 0; i < width; i++) {
            if (blockLocations[i][row] == 0) return false;
        }
        return true;
    }

    /**
     * Clears a given row of all blocks
     *
     * @param row row to clear.
     */
    private void clearLine(int row) {
        for (int i = width - 1; i >= 0; i--) {
            blockLocations[i][row] = 0;
            blockColors[i][row] = BACKGROUNDCOLOR;
        }
    }

    /**
     * Check for lines of blocks and clear them.
     */
    private void checkForLine() {
        boolean lineFound = false;
        //check each row for full line
        for (int j = height - 1; j >= 0; j--) {
            if (isFullLine(j)) {
                clearLine(j);
                playerModel.incScore(10);
                lineFound = true;
            }
        }
        if (lineFound) {
            moveLinesDown();
        }
    }



    /**
     * Shift rows of blocks down as necessary.
     */
    private void moveLinesDown() {
        for (int j = height - 1; j >= 0; j--) {
            boolean allEmptyAbove = true;
            if (isClearLine(j)) {
                // Check if any rows above blank are empty.
                for (int k = j - 1; k >= 0; k--) {
                    if (!isClearLine(k)) {
                        allEmptyAbove = false;
                        int rowBelow = k + 1;
                        //shift row down
                        for (int i = 0; i < width; i++) {
                            blockLocations[i][rowBelow] = blockLocations[i][k];
                            blockColors[i][rowBelow] = blockColors[i][k];
                        }
                        clearLine(k);
                    }
                }
                // No need to iterate through blank rows
                if (allEmptyAbove) break;
                else j++;
            }
            notifyObservers();
        }
    }

    /**
     * Start game of Tetris
     */
    public void startGame() {

        currentFallingMino = nextMino();
        speedTimer = new Timer(speed, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (gameOver) {
                    endGame();
                } else {
                    nextStep();
                }
            }
        });
    }

    /**
     * End game of Tetris.
     */
    private void endGame() {

        JOptionPane.showMessageDialog(null, "Game Over! :( You Scored: " + playerModel.getCurScore());
        speedTimer.stop();
    }

    /**
     * Run next step in Tetris game.
     */
    private void nextStep() {
        Mino currentMovedDown = currentFallingMino.copy();
        currentMovedDown.moveDown();
        if (!isCollision(currentMovedDown.getPositions())) {
            currentFallingMino.moveDown();
        } else {
            commitCurrentToBoard();
            checkForLine();

            //Prevent new piece overlaying old one on first row.
            if (!gameOver) {
                Mino potentialNewCurrent = nextMino();
                gameOver = isCollision(potentialNewCurrent.getPositions());
                if (!gameOver) currentFallingMino = potentialNewCurrent;
            }
        }
        notifyObservers();
    }

    /**
     * Extends notify observers class because a change definitely occurs before notification.
     */
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    /**
     * Drop mino all the way to bottom.
     */
    public void moveToBottom() {
        Mino currentMovedDown = currentFallingMino.copy();
        currentMovedDown.moveDown();
        while (!isCollision(currentMovedDown.getPositions())) {
            currentFallingMino.moveDown();
            currentMovedDown.moveDown();
        }
    }

    /**
     * Pause Tetris.
     */
    public void pause() {
        speedTimer.stop();
    }

    /**
     * Unpause Tetris if a game is actually still running.
     */
    public void unPause() {
        if (!gameOver) {
            speedTimer.restart();
        }
    }

    /**
     * Create ghost of the current falling mino.
     *
     * @return the ghost of the current falling mino.
     */
    public Mino getGhost() {

        Mino ghostPiece = currentFallingMino.copy();
        Mino ghostTest = ghostPiece.copy();
        ghostTest.moveDown();

        while (!isCollision(ghostTest.getPositions())) {
            ghostTest.moveDown();
            ghostPiece.moveDown();
        }
        return ghostPiece;
    }
}
