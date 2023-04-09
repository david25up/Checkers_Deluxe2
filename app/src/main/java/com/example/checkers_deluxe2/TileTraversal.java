package com.example.checkers_deluxe2;

/*
 * Used to track down the path of a capture move
 * so that all captured pieces are removed properly.
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  April 2023
 */

import java.util.ArrayList;

public class TileTraversal {
    /* --- INSTANCE VARIABLES --- */
    Tile start;
    ArrayList<Tile> traversal;

    /** Constructor for LocalCheckers given a CheckersState object */
    public TileTraversal(Tile start) {
        this.start = start;
        this.traversal = new ArrayList<Tile>();
        traversal.add(start);
    }//ctor
}
