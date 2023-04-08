package com.example.checkers_deluxe2;

/*
 * The individual tiles on the board
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

public class Tile {
    public enum Value {
        EMPTY,
        RED,
        BLACK,
        AVAIL, //Available move when displaying possible moves
    }//Value

    // Instance Variables //
    private Value value;
    private boolean isKing; //Empty spaces are assumed to be false
    private boolean isStart; //A marker to signify the piece is clicked
    private final int row;
    private final int col;

    public Tile(int row, int col) {
        this.value = Value.EMPTY;
        this.isKing = false;
        this.row = row;
        this.col = col;
    }//ctor

    /**
     * Reverts tile back to its "empty state"
     */
    public void revertTile() {
        setValue(Value.EMPTY);
        setIsKing(false);
    }//revertTile

    ////////////////////////////
    // --- GETTER METHODS --- //
    ////////////////////////////
    public Value getValue() {return value;}//getValue
    public boolean getIsKing() {return isKing;}//getIsKing
    public boolean getIsStart() {return isStart;}//getIsStart
    public int getRow() {return row;}//getKing
    public int getCol() {return col;}//getCol

    ////////////////////////////
    // --- SETTER METHODS --- //
    ////////////////////////////
    public void setValue(Value value) {this.value = value;}//setInTile
    public void setIsKing(Boolean givenKing) {isKing = givenKing;}//setKing
    public void setIsStart(Boolean givenStart) {isStart = givenStart;}//setIsStart



}//Tile
