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
import android.util.AttributeSet;

import com.example.GameFramework.animation.AnimationSurface;
import com.example.GameFramework.utilities.GameTimer;
import com.example.GameFramework.utilities.Tickable;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Tile;

public class CheckersAnimationSurface extends AnimationSurface implements Tickable {
    /* --- BOARD DIMENSIONS (in percentages) --- */
    private final static float LEFT_PADDING = 30; //The space to the left of the board
    private final static float WIDTH = 125; //The width of the board itself
    private final static float HEIGHT = 100; //The height of the board itself
    private final static float TILE_SIZE = 3.75F; //Individual squares or tiles on the board
    private final static float PADDING = 2; //Padding on the edge of the board

    private final static float leftX = LEFT_PADDING + (PADDING * 2);
    private final static float leftY = PADDING * 2;
    private final static float rightX = WIDTH - (PADDING * 2);
    private final static float rightY = HEIGHT - (PADDING * 2);

    private final static float TILE_WIDTH ;


    /* --- INSTANCE VARIABLES --- */
    protected CheckersState checkersState;
    protected float hBase;
    protected float vBase;
    protected float fullSquare;


    /**
     * The inherited constructor from AnimationSurface class
     * @param context   The activity the animation is run on
     */
    public CheckersAnimationSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }//ctor

    /**
     * --- HELPER METHOD (for ctor) ---
     */
    private void init() {
        setBackgroundColor(backgroundColor());
    }//init

    /**
     * --- HELPER METHOD (for ctor) ---
     * @param g   The canvas we are drawing on
     */
    private void getDimensions(Canvas g) {
        // Gets the width and height of the drawing surface
        int width = g.getWidth();
        int height = g.getHeight();

        //Assigns the variables on the assumption the tablet is horizontal
        if (width > height) {
            fullSquare = height;
            vBase = 0;
            hBase = (width - height) / (float) 2.0;
        } else {
            fullSquare = width;
            hBase = 0;
            vBase = (height - width) / (float) 2.0;
        }
    }//getDimensions


    public void setState(CheckersState state) {
        checkersState = state;
    }


    /* --- COLOR RETURN METHODS --- */
    public int foregroundColor() {return Color.YELLOW;}//foregroundColor
    public int backgroundColor() {return Color.BLUE;}//backgroundColor
    public int whiteTile() {return Color.WHITE;}//whiteTile
    public int blackTile() {return Color.BLACK;}//blackTile


    /**
     * Called when a change is made so that the board itself
     * can be updated
     *
     * @param g   The canvas we are drawing on
     */
    public void onDraw(Canvas g) {
        getDimensions(g);
        Paint p = new Paint();

        // Paints the board itself with a trim around it
        p.setColor(foregroundColor());
        g.drawRect(h(leftX - PADDING), v(leftY - PADDING), h(rightX + PADDING), v(rightY + PADDING), p); //Trim

        p.setColor(blackTile());
        g.drawRect(h(leftX), v(leftY), h(rightX), v(rightY), p); //Black Base

        p.setColor(whiteTile());
        for (int i = 0; i < 9; i++) { //White Tiles
            for (int j = 0; j < 9; j++) {
                if ((j % 2 != 0 && i % 2 == 0) || (j % 2 == 0 && i % 2 != 0)) {
                    g.drawRect(leftX, leftY, leftX + (TILE_SIZE), TILE_SIZE, p);
                }
            }
       }

        // If we don't have any state, there's nothing more to draw, so return
        if (checkersState == null) {
            return;
        }

        // Draws the different types of pieces (either black, red, empty, or highlighted)
        Tile[][] temp = checkersState.getBoard(); // get board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                //drawSymbol(g, temp[row][col]);
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

    /**
     * --- HELPER METHOD ---
     *
     * @param percent
     * 		the percentage across the tablet horizontally
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float h(float percent) {
        return hBase + percent * fullSquare / 100;
    }//h

    /**
     * --- HELPER METHOD ---
     *
     * @param percent
     * 		the percentage across the tablet horizontally
     * @return
     * 		the pixel location that corresponds to that percentage
     */
    private float v(float percent) {
        return vBase + percent * fullSquare / 100;
    }//v

}//CheckersAnimationSurface
