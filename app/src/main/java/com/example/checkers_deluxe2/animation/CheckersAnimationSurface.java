package com.example.checkers_deluxe2.animation;

/*
 * An extension of SurfaceView taken from GameFramework's
 * "AnimationSurface" class to animate each piece's movement
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.GameFramework.animation.AnimationSurface;
import com.example.GameFramework.utilities.GameTimer;
import com.example.GameFramework.utilities.Tickable;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Tile;

public class CheckersAnimationSurface extends AnimationSurface implements Tickable {
    /** instance variables */
    private CheckersState checkersState;


    /**
     * The inherited constructor from AnimationSurface class
     * @param context   The activity the animation is run on
     */
    public CheckersAnimationSurface(Context context) {
        super(context);
        init();
    }//ctor

    /**
     * Called when a change is made so that the board itself
     * can be updated
     *
     * @param g   The canvas we are drawing on
     */
    public void onDraw(Canvas g) {
        // Paints the board itself as well as the lines
        Paint p = new Paint();
        p.setColor(Color.YELLOW);


        // if we don't have any state, there's nothing more to draw, so return
        if (checkersState == null) {
            return;
        }

        // Draws the different types of pieces (either black, red, empty, or highlighted)
        Tile[][] temp = checkersState.getBoard(); // get board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                drawSymbol(g, temp[row][col]);
            }
        }
    }//onDraw

    /**
    drawBoard(): This method should draw the game board, including the squares and any other visual elements (such as borders or labels) that are part of the game.
    drawPieces(): This method should draw the game pieces on the board based on their current positions.
            movePiece(): This method should allow the user to select a piece and move it to a new location based on their input.
            updateState(): This method should update the state of the game (such as the positions of the pieces) when a move is made.
    animatePiece(): This method should handle the animation of a piece as it moves from one location to another.
            tick(): This method should update the game state on each tick of the game timer, and trigger any necessary animation or movement of the pieces.
*/


    /**
     * timer method
     * @param timer
     * 		the timer that is associated with the "tick"
     */
    @Override
    public void tick(GameTimer timer) {}


} // end of class
