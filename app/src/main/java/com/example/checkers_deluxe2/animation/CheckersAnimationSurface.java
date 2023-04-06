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
import android.util.Log;
import android.view.MotionEvent;

import com.example.GameFramework.animation.AnimationSurface;
import com.example.GameFramework.utilities.GameTimer;
import com.example.GameFramework.utilities.Tickable;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Tile;

public class CheckersAnimationSurface extends AnimationSurface implements Tickable {
    private final static String TAG = "CheckersAnimationSurface";
    /* --- BOARD DIMENSIONS (in percentages) --- */
    private final static float LEFT_PADDING = 30; //The space to the left of the board
    private final static float WIDTH = 125; //The width of the board itself
    private final static float HEIGHT = 100; //The height of the board itself
    private final static float TILE_SIZE = 3.75F; //Individual squares or tiles on the board
    private final static float PADDING = 2; //Padding on the edge of the board

    // The top left percentage of the board //
    private final static float leftX = LEFT_PADDING + (PADDING * 2);
    private final static float leftY = PADDING * 2;

    // The top right percentage of the board //
    private final static float rightX = WIDTH - (PADDING * 2);
    private final static float rightY = HEIGHT - (PADDING * 2);

    // The individual tiles //
    private final static float TILE_WIDTH = (rightX - leftX) / 8;
    private final static float TILE_HEIGHT = (rightY - leftY) / 8;


    /* --- INSTANCE VARIABLES --- */
    protected CheckersState checkersState;
    protected float hBase;
    protected float vBase;
    protected float fullSquare;

    // The coordinates of the point the user clicks on the screen //
    protected int rowClick;
    protected int colClick;


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
    public int redPiece() {return Color.RED;}//redPiece
    public int darkPiece() {return Color.GRAY;}//darkPiece
    public int availPiece() {return Color.GREEN;}//availPiece


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

        p.setColor(whiteTile());
        g.drawRect(h(leftX), v(leftY), h(rightX), v(rightY), p); //Black Base

        p.setColor(blackTile());
        for (int row = 0; row < 8; row++) { //White Tiles
            for (int col = 0; col < 8; col++) {
                if ((row % 2 != 0 && col % 2 == 0) || (row % 2 == 0 && col % 2 != 0)) {
                    g.drawRect(h(leftX + (TILE_WIDTH * col)), v(leftY + (TILE_HEIGHT * row)),
                                h(leftX + (TILE_WIDTH * col) + TILE_WIDTH), v(leftY + (TILE_HEIGHT * row) + TILE_HEIGHT), p);
                }
            }
       }

        // If we don't have any state, there's nothing more to draw, so return
        if (checkersState == null) {
            return;
        }

        // Draws the different types of pieces (either black, red, empty, or highlighted)
        Tile[][] temp = checkersState.getBoard(); // get board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                drawSymbol(g, temp[row][col]);
            }
        }
    }//onDraw

    private void drawSymbol(Canvas g, Tile piece) {
        Tile.Value temp = piece.getValue();

        int row = piece.getRow();
        int col = piece.getCol();
        float pieceX = leftX + (TILE_WIDTH * col);
        float pieceY = leftY + (TILE_HEIGHT * row);
        Paint p = new Paint();
        switch (temp) {
            case RED: {
                p.setColor(redPiece());
                g.drawOval(h(pieceX), v(pieceY), h(pieceX + TILE_WIDTH), v(pieceY + TILE_HEIGHT), p);
                break;
            }
            case BLACK: {
                p.setColor(darkPiece());
                g.drawOval(h(pieceX), v(pieceY), h(pieceX + TILE_WIDTH), v(pieceY + TILE_HEIGHT), p);
                break;
            }
            case AVAIL: {
                p.setColor(availPiece());
                g.drawOval(h(pieceX), v(pieceY), h(pieceX + TILE_WIDTH), v(pieceY + TILE_HEIGHT), p);
                break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //Screen was clicked
            case MotionEvent.ACTION_DOWN:
                if (withinBoard(event.getX(), event.getY())){
                    Log.d(TAG, "User touches screen in: " + rowClick + " " + colClick);
                } else {
                    Log.d(TAG, "The spot clicked was not on the board");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

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

    /**
     * Checks to make sure that the spot the user clicked was on the board
     * @param x
     *      The x coordinate the user clicked
     * @param y
     *      The y coordinate the user clicked
     */
    private boolean withinBoard(float x, float y) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                float left = h(leftX + (TILE_WIDTH * col));
                float right = h(left + TILE_WIDTH);
                float top = v(leftY + (TILE_HEIGHT * row));
                float bottom = v(top + TILE_HEIGHT);
                if ((x > left) != (x > right) && (y > top) != (y > bottom)) {
                    this.rowClick = row;
                    this.colClick = col;
                    return true;
                }
            }
        }
        return false;
    }//pixelToTile

}//CheckersAnimationSurface
