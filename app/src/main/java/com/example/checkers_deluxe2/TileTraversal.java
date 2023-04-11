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
        this.start.setValue(start.getValue());
        this.start.setIsKing(start.getIsKing());
        this.traversal = new ArrayList<Tile>();
        traversal.add(start);
    }//ctor

    /* --- Getter Methods --- */

    public Tile getStart() {return this.start;}

    public ArrayList<Tile> getTraversal() {return this.traversal;}

    public int getTraversalLength() {return this.traversal.size();}

    /* --- Setter Variables --- */

    public void setStart(Tile start) {this.start = start;}

    public void setTraversal(ArrayList<Tile> traversal) {this.traversal = traversal;}
    /* --- Methods --- */
    public void addTile(Tile move) {this.traversal.add(move);}

}
