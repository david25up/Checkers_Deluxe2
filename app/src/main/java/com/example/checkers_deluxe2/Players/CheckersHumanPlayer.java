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
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.GameFramework.GameMainActivity;
import com.example.GameFramework.infoMessage.GameInfo;
import com.example.GameFramework.players.GameHumanPlayer;
import com.example.GameFramework.utilities.Logger;
import com.example.checkers_deluxe2.CheckersMainActivity;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.R;
import com.example.checkers_deluxe2.Tile;
import com.example.checkers_deluxe2.animation.CheckersAnimationSurface;

public class CheckersHumanPlayer extends GameHumanPlayer implements View.OnTouchListener{
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

        //Makes sure info is a CheckersState object
        if (!(info instanceof CheckersState)) {return;}

        surfaceView.setState((CheckersState)info);
        surfaceView.invalidate();
        Logger.log(TAG, "receiving");

        //Create an action state via: "NameOfAction" action = new "NameOfAction(this)"
        //and send action via: game.sendAction(action)

        boolean movePiece = false;
            while (movePiece) {
                // if tap a piece checkAvailMoves
                // if select square with AVAIL, move piece with checkersstate then send action

            }
        ((CheckersState) info).flipTurn();
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
        surfaceView = (CheckersAnimationSurface) myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
    }//setAsGui

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //Screen was clicked
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Point point = surfaceView.withinBoard(event.getX(), event.getY());
            if (point == null) {
                Log.d(TAG, "The spot clicked was not on the board");
            }

            rowClick = point.x;
            colClick = point.y;
            Log.d(TAG, "User touches screen in: " + rowClick + " " + colClick);
        }
        surfaceView.invalidate();
        return true;
    }
}
