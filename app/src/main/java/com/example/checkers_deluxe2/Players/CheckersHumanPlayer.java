package com.example.checkers_deluxe2.Players;

/*
 * The human player of the game
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.infoMessage.GameOverInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.players.GamePlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.R;
import com.example.checkers_deluxe2.actionMessage.CheckersResetAction;
import com.example.checkers_deluxe2.actionMessage.CheckersTapAction;
import com.example.checkers_deluxe2.animation.CheckersAnimationSurface;

public class CheckersHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    // Tag for logging //
    private static final String TAG = "CheckersHumanPlayer";


    /* --- INSTANCE VARIABLES --- */
    private CheckersAnimationSurface surfaceView;
    private int layoutId;

    // The row and column of the point the user clicks on the screen //
    private int rowClick;
    private int colClick;

    /**
     * The inherited constructor from GameComputerPlayer class
     * @param name   The name of the player
     */
    public CheckersHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }//ctor

    @Override
    public View getTopView() {
        return null;
    }//getTopView

    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) {return;}
        if (!(info instanceof CheckersState)) {return;}

        surfaceView.setState((CheckersState)info);
        surfaceView.invalidate();
        Logger.log(TAG, "receiving");
    }//receiveInfo

    /**
     * Taken from GameHumanPlayer
     * @param activity   The activity we are running under
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        // Load the layout resource for our GUI
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);

        //set up the reset button
        Button reset = myActivity.findViewById(R.id.reset_button);
        Logger.log("set reset button", "OnClick");
        reset.setOnClickListener(this);

        //set up the back button
        Button back = myActivity.findViewById(R.id.back_button);
        Logger.log("set back button", "OnClick");
        back.setOnClickListener(this);
    }//setAsGui

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //Screen was clicked
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Point point = surfaceView.withinBoard(event.getX(), event.getY());
            if (point == null) {
                Log.d(TAG, "The spot clicked was not on the board");
                return false;
            }

            rowClick = point.x;
            colClick = point.y;
            Log.d(TAG, "User touches screen in: " + rowClick + " " + colClick);

            game.sendAction(new CheckersTapAction(this, rowClick, colClick));
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        //reset button
        if (view.getId() == R.id.reset_button) {
            game.sendAction(new CheckersResetAction(this));
        }

        //back button
        if (view.getId() == R.id.back_button) {
            myActivity.setContentView(R.layout.game_config_main);
        }
    }
}
