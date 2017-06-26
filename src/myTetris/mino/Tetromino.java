package myTetris.mino;

import java.awt.*;
import java.util.*;

import static java.awt.Color.*;
import static java.awt.Color.cyan;

/**
 * Created by Ollie on 26/11/2014.
 */
public class Tetromino extends Mino {

    //Store possible offsets from centre
    private static final Map<String, Point[]> positionsMap = new HashMap<String, Point[]>();
    //Store colours of each Tetrmino, simpler than one map that stores both.
    private static final Map<String, Color> colorMap = new HashMap<String, Color>();

    //Populate static map values.
    static {
        positionsMap.put("I", new Point[]{new Point(2, 0), new Point(1, 0), new Point(0, 0), new Point(-1, 0)});
        colorMap.put("I", cyan);
        positionsMap.put("O", new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)});
        colorMap.put("O", yellow);
        positionsMap.put("T", new Point[]{new Point(1, 0), new Point(0, 0), new Point(0, 1), new Point(-1, 0)});
        colorMap.put("T", pink);
        positionsMap.put("J", new Point[]{new Point(-1, 0), new Point(0, 0), new Point(1, 0), new Point(1, 1)});
        colorMap.put("J", blue);
        positionsMap.put("L", new Point[]{new Point(-1, 1), new Point(-1, 0), new Point(0, 0), new Point(1, 0)});
        colorMap.put("L", orange);
        positionsMap.put("S", new Point[]{new Point(-1, 0), new Point(0, 0), new Point(0, -1), new Point(1, -1)});
        colorMap.put("S", green);
        positionsMap.put("Z", new Point[]{new Point(-1, 0), new Point(0, 0), new Point(0, 1), new Point(1, 1)});
        colorMap.put("Z", red);

    }

    /**
     * Create Tetromino.
     */
    public Tetromino(String type, int cx, int cy) {
        super(type, positionsMap.get(type), colorMap.get(type), cx, cy);
    }

    /**
     * Rotate Tetromino clockwise
     */
    public void rotateClockwise() {
        if (!getType().equals("O")) super.rotateClockwise();
    }

    /**
     * Get array of types of Tetrominos
     */
    private static String[] getTypes() {
        String[] a = positionsMap.keySet().toArray(new String[]{""});
        return Arrays.copyOf(a, a.length);
    }

    /**
     * Copy the Tetromino that the method is called on by value.
     */
    public Mino copy() {
        Tetromino cloneResult = new Tetromino(getType(), getCentreX(), getCentreY());
        cloneResult.setOffsetPoints(this.getOffsetPoints());
        return cloneResult;
    }

    /**
     * Get the next Tetromino, random selection.
     */
    public Mino nextPiece(int x, int y) {
        String[] tetroTypes = Tetromino.getTypes();
        Random r = new Random();
        Tetromino newTetro = new Tetromino(tetroTypes[r.nextInt(tetroTypes.length)], x, y);
        newTetro.setCenterY(y - newTetro.maxY());
        return newTetro;
    }

}
