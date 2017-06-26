package myTetris.game.view;

/**
 * Created by Ollie on 26/11/2014.
 */

import myTetris.game.model.PlayerModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PlayerView extends JPanel implements Observer {
    private JLabel jCurScore;
    private PlayerModel model;


    /**
     * React to model update.
     */
    @Override
    public void update(Observable o, Object arg) {
        updateScoreComps();
    }

    /**
     * Creates PlayerModel view.
     */
    public PlayerView(PlayerModel model) {
        this.model = model;
        setLayout(new GridLayout());
        this.add(new JLabel("Score: "));
        jCurScore = new JLabel();
        this.add(jCurScore);
        updateScoreComps();
        model.addObserver(this);

    }

    /**
     * Update score view based on value of curScore instance variable.
     */
    private void updateScoreComps() {
        jCurScore.setText(Integer.toString(model.getCurScore()));
    }
}
