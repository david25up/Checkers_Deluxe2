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
    private final int row;
    private final int col;

    private static int isBlack;

    public Tile(int row, int col) {
        value = Value.EMPTY;
        isKing = false;
        this.row = row;
        this.col = col;

        for (int i = 0; i < 64; i++) {
            if (i % 2 == 0) {
                this.isBlack = 0; // black tiles are 0
            }
            else {
                this.isBlack = 1; // red tiles are 1
            }
        }
    }//ctor

    ////////////////////////////
    // --- GETTER METHODS --- //
    ////////////////////////////
    public Value getValue() {return value;}//getValue
    public boolean getKing() {return isKing;}//getKing
    public int getRow() {return row;}//getKing
    public int getCol() {return col;}//getCol

    ////////////////////////////
    // --- SETTER METHODS --- //
    ////////////////////////////
    public void setValue(Value value) {this.value = value;}//setInTile
    public void setKing(Boolean givenKing) {isKing = givenKing;} //setKing


}//Tile
