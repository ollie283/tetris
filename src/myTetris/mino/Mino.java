package myTetris.mino;

import java.awt.*;

/**
 * Created by Ollie on 26/11/2014.
 */
public abstract class Mino {
    private String type;
    private Point[] offsetPoints;
    private int cx, cy; //centre coordinates
    private Color color;

    /**
     * Creates mino object.
     */
    public Mino(String type, Point[] minoPoints, Color color, int cx, int cy) {
        this.type = type;
        offsetPoints = copyPointArray(minoPoints);
        this.color = color;
        this.cx = cx;
        this.cy = cy;
    }

    /**
     * Get the type of mino.
     */
    public String getType() {
        return type;
    }


    /**
     * Setter for center y value
     */
    public void setCenterY(int cy) {
        this.cy = cy;
    }

    /**
     * Rotate mino clockwise.
     */
    public void rotateClockwise() {
        //Square doesn't need to be rotated.
        for (Point offsetPoint : offsetPoints) {
            double x = offsetPoint.getX();
            double y = offsetPoint.getY();
            offsetPoint.setLocation(-y, x);
        }
    }

    /**
     * Find highest y value, useful for initial placement purposes.
     */
    protected int maxY() {
        double maxy = offsetPoints[0].getY();
        for (Point p : offsetPoints) {
            double y = p.getY();
            maxy = (maxy > y) ? maxy : y;
        }
        return (int) maxy;
    }

    public abstract Mino nextPiece(int x, int y);

    /**
     * Get coordinates that are relevant to the board.
     */
    public Point[] getPositions() {
        //Calculate positions from offset and centre.
        Point[] positions = new Point[offsetPoints.length];
        for (int i = 0; i < offsetPoints.length; i++) {
            int x = (int) offsetPoints[i].getX() + cx;
            int y = (int) offsetPoints[i].getY() + cy;
            positions[i] = new Point(x, y);
        }
        return positions;
    }

    /**
     * Get offset points.
     */
    protected Point[] getOffsetPoints() {
        return copyPointArray(offsetPoints);
    }

    /**
     * Set offset points.
     */
    protected void setOffsetPoints(Point[] pointArray) {
        offsetPoints = copyPointArray(pointArray);
    }

    /**
     * Copy a pointArrays by value.
     */
    private static Point[] copyPointArray(Point[] pointArray) {
        Point[] copyTo = new Point[pointArray.length];
        for (int i = 0; i < copyTo.length; i++) {
            copyTo[i] = new Point(pointArray[i]);
        }
        return copyTo;
    }

    /**
     * Get center y position.
     */
    public int getCentreX() {
        return cx;
    }

    /**
     * Get center x position.
     */
    public int getCentreY() {
        return cy;
    }

    /**
     * Move mino downwards.
     */
    public void moveDown() {
        cy += 1;
    }

    /**
     * Move mino right.
     */
    public void moveRight() {
        cx += 1;
    }

    /**
     * Move mino left.
     */
    public void moveLeft() {
        cx -= 1;
    }

    /**
     * Copy mino
     */
    public abstract Mino copy();

    /**
     * Get colour of mino
     */
    public Color getColor() {
        return this.color;
    }

}
