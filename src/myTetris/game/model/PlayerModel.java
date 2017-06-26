package myTetris.game.model;

/**
 * Created by Ollie on 01/11/2014.
 */

import java.util.Observable;

public class PlayerModel extends Observable {
    private int currentScore;

    /**
     * Creates PlayerModel object
     *
     */
    public PlayerModel() {
        currentScore = 0;
    }

    /**
     * Increment score
     *
     * @param i value to increment score by.
     */
    public void incScore(int i) {
        currentScore += i;
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
     * Get current score of playerModel.
     *
     * @return Score of playerModel.
     */
    public int getCurScore() {
        return currentScore;
    }

}
