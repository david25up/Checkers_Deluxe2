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
        this.start.setValue(start.getValue());
        this.start.setIsKing(start.getIsKing());
        this.traversal = new ArrayList<Tile>();
        traversal.add(start);
    }//ctor

    /**
     * Copy constructor of a given tile traversal
     * @param original   The tile traversal we are copying over
     */
    public TileTraversal (TileTraversal original) {
        this.start = original.getStart();
        this.start.setValue(original.start.getValue());
        this.start.setIsKing(original.start.getIsKing());
        this.traversal = new ArrayList<Tile>();
        for (int i = 0; i < original.traversal.size(); i++) {
            traversal.add(original.getTile(i));
        }
    }//ctor

    /* --- Getter Methods --- */

    public Tile getStart() {return this.start;}
    public int getTraversalLength() {return this.traversal.size();}
    public Tile getDestination() {return this.traversal.get(traversal.size() - 1);}
    public Tile getTile(int index) {return this.traversal.get(index);}

    /* --- Setter Variables --- */
    public void setStart(Tile start) {this.start = start;}
    /* --- Methods --- */
    public void addTile(Tile move) {this.traversal.add(move);}



}
