package com.example.checkers_deluxe2;

/*
 * An ArrayList of ArrayLists that is used to track
 * down the path of a capture move so that all captured
 * pieces are removed properly.
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  April 2023
 */

import java.util.ArrayList;

public class TileTraversal {
    /* --- INSTANCE VARIABLES --- */
    Tile start; //The tile that contains the piece we are moving
    ArrayList<Tile> traversal;

    /** Constructor for LocalCheckers given a CheckersState object */
    public TileTraversal(Tile start) {
        this.start = start;
        this.traversal = new ArrayList<Tile>();
        traversal.add(start);
    }//ctor
}
