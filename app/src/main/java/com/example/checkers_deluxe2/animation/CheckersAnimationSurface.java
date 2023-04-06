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

import static android.graphics.Bitmap.Config.ARGB_8888;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.GameFramework.animation.AnimationSurface;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.R;
import com.example.checkers_deluxe2.Tile;

public class CheckersAnimationSurface extends AnimationSurface {
    private final static String TAG = "CheckersAnimationSurface";
    /* --- BOARD DIMENSIONS (in percentages) --- */
    private final static float LEFT_PADDING = 30; //The space to the left of the board
    private final static float WIDTH = 125; //The width of the board itself
    private final static float HEIGHT = 100; //The height of the board itself
    private final static float PADDING = 2; //Padding on the edge of the board
    private final static float KING_PADDING = 1; //Padding around the king's icon

    // x and y coordinates for the board itself //
    private final static float LEFT = LEFT_PADDING + (PADDING * 2);
    private final static float TOP = PADDING * 2;
    private final static float RIGHT = WIDTH - (PADDING * 2);
    private final static float BOTTOM = HEIGHT - (PADDING * 2);

    // Dimensions for the tiles //
    private final static float TILE_WIDTH = (RIGHT - LEFT) / 8;
    private final static float TILE_HEIGHT = (BOTTOM - TOP) / 8;


    /* --- INSTANCE VARIABLES --- */
    protected CheckersState checkersState;
    private float hBase;
    private float vBase;
    private float fullSquare;

    // The row and column of the point the user clicks on the screen //
    private int rowClick;
    private int colClick;

    /* --- COLOR RETURN METHODS --- */
    public int foregroundColor() {return Color.YELLOW;}//foregroundColor
    public int backgroundColor() {return Color.BLUE;}//backgroundColor
    public int whiteTile() {return Color.WHITE;}//whiteTile
    public int blackTile() {return Color.BLACK;}//blackTile
    public int lightPiece() {return Color.RED;}//lightPiece
    public int darkPiece() {return Color.GRAY;}//darkPiece
    public int availPiece() {return Color.GREEN;}//availPiece

    /* --- CROWN MARKER --- */
    Bitmap crown = BitmapFactory.decodeResource(getResources(), R.drawable.crown);

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
     * Called when a change is made so that the board itself
     * can be updated
     *
     * @param g   The canvas we are drawing on
     */
    public void onDraw(Canvas g) {
        getDimensions(g);
        Paint p = new Paint();

        //The trim around the board
        p.setColor(foregroundColor());
        g.drawRect(h(LEFT - PADDING), v(TOP - PADDING), h(RIGHT + PADDING), v(BOTTOM + PADDING), p);

        //White Base
        p.setColor(whiteTile());
        g.drawRect(h(LEFT), v(TOP), h(RIGHT), v(BOTTOM), p);

        //Black Tiles
        p.setColor(blackTile());
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row % 2 != 0 && col % 2 == 0) || (row % 2 == 0 && col % 2 != 0)) {
                    g.drawRect(h(LEFT + (TILE_WIDTH * col)), v(TOP + (TILE_HEIGHT * row)),
                                h(LEFT + (TILE_WIDTH * col) + TILE_WIDTH), v(TOP + (TILE_HEIGHT * row) + TILE_HEIGHT), p);
                }
            }
       }

        // If we don't have any state, there's nothing more to draw, so return
        if (checkersState == null) {
            return;
        }

        // Draws the different types of pieces (either black, red, empty, or available)
        Tile[][] temp = checkersState.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                drawSymbol(g, temp[row][col]);
            }
        }
    }//onDraw

    /**
     * --- HELPER METHOD (for onDraw) ---
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

    /**
     * Responsible for drawing the pieces themselves
     * @param g
     *      The canvas we are drawing on
     * @param tile
     *      The tile we are attempting to draw on
     */
    private void drawSymbol(Canvas g, Tile tile) {
        Tile.Value value = tile.getValue();

        int row = tile.getRow();
        int col = tile.getCol();
        float pieceX = LEFT + (TILE_WIDTH * col);
        float pieceY = TOP + (TILE_HEIGHT * row);
        Paint p = new Paint();
        switch (value) {
            case RED: {
                p.setColor(lightPiece());
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

        //Adds a king marker to kings
        if (tile.getIsKing()) {
            Rect rect = new Rect((int) h(pieceX + KING_PADDING), (int) v(pieceY + KING_PADDING),
                                    (int) h(pieceX + TILE_WIDTH - KING_PADDING), (int) v(pieceY + TILE_HEIGHT - KING_PADDING));
            g.drawBitmap(crown, null, rect, p);
        }
    }//drawSymbol

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //Screen was clicked
            case MotionEvent.ACTION_DOWN:
                if (withinBoard(event.getX(), event.getY())){
                    Log.d(TAG, "User touches screen in: " + rowClick + " " + colClick);
                    Tile[][] board = checkersState.getBoard();
                    if (board[rowClick][colClick].getValue() == Tile.Value.AVAIL) {
                        // checkers swap pieces with current piece
                    } else if (board[rowClick][colClick].getValue() == Tile.Value.EMPTY) {
                        // toggle board in local game idk where it should go
                    }
                    // else {
                        // call availmoves
                      //  }

                } else  {
                    Log.d(TAG, "The spot clicked was not on the board");
                }
                break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }//onTouchEvent

    /**
     * --- HELPER METHOD (for onTouchEvent)
     * Checks to make sure that the spot the user clicked was on the board
     * @param x
     *      The x coordinate the user clicked
     * @param y
     *      The y coordinate the user clicked
     * @return
     *      True if the spot clicked is on the board, false if not
     */
    private boolean withinBoard(float x, float y) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                float left = h(LEFT + (TILE_WIDTH * col));
                float right = h(LEFT + (TILE_WIDTH * col) + TILE_WIDTH);
                float top = v(TOP + (TILE_HEIGHT * row));
                float bottom = v(TOP + (TILE_HEIGHT * row) + TILE_HEIGHT);
                if ((x > left) != (x > right) && (y > top) != (y > bottom)) {
                    this.rowClick = row;
                    this.colClick = col;
                    return true;
                }
            }
        }
        return false;
    }//withinBoard

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

    /** --- SETTER METHOD --- */
    public void setState(CheckersState state) {
        checkersState = state;
    }//setState

}//CheckersAnimationSurface
