package com.example.checkers_deluxe2;

import android.util.Log;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.actionMessage.CheckersMoveAction;
import com.example.checkers_deluxe2.actionMessage.CheckersTapAction;

import java.util.ArrayList;

public class LocalCheckers extends LocalGame {
    // Tag for logging //
    private static final String TAG = "LocalCheckers";

    /* --- INSTANCE VARIABLES --- */

    /** Default constructor for LocalCheckers */
    public LocalCheckers() {
        super();
        CheckersState checkersState = new CheckersState();
        checkersState.initBoard();
        super.state = checkersState;
    }//default ctor

    /** Constructor for LocalCheckers given a CheckersState object */
    public LocalCheckers(CheckersState checkersState) {
        super();
        checkersState.initBoard();
        super.state = new CheckersState(checkersState);
    }//ctor

    /**
     *  This is where you should initialize anything specific to the
     *  number of players.  For example you may need to init your
     *  game state or part of it.  Loading data could also happen here.
     *
     * 	 @param players
     * 	      the array that holds all of the players in the game
     */
    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    }//start

    /**
     * Sends the updated state of the game to the player
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new CheckersState((CheckersState) state));
    }//sendUpdatedStateTo

    /**
     * --- HELPER METHOD ---
     * Makes sure the player can move
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     *      returns true if the given player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return (playerIdx == ((CheckersState)state).getTurn());
    }//canMove

    @Override
    protected boolean makeMove(GameAction action) {
        CheckersTapAction cm = (CheckersTapAction) action;

        int row = cm.getRow();
        int col = cm.getCol();
        int playerId = getPlayerIdx(cm.getPlayer());
        Tile[][] board = ((CheckersState)state).getBoard();


        if (board[row][col].getValue().equals(Tile.Value.AVAIL)) {
            Log.d(TAG, "Piece will be moved");

            ((CheckersState)state).swapPieces(findStart(board), board[row][col]);
            ((CheckersState)state).setBoard((revertAvail(board)));
            ((CheckersState)state).flipTurn();
        } else if (!board[row][col].getValue().equals(Tile.Value.EMPTY)) {
            Log.d(TAG, "Piece was tapped");

            ((CheckersState)state).setBoard(revertAvail(board));
            ((CheckersState)state).setBoard(toggleAvail(availMoves(board[row][col], board), board));
        }
        return true;
    }//makeMove

    /**
     * --- HELPER METHOD ---
     * Finds the marked spot on the board
     * @param board
     * @return
     */
    private Tile findStart(Tile[][] board) {
        for (int row = 0; row < CheckersState.HEIGHT; row++) {
            for (int col = 0; col < CheckersState.WIDTH; col++) {
                if (board[row][col].getIsStart()) return board[row][col];
            }
        }
        return null;
    }//findStart

    /**
     * Checks the surrounding tiles for valid and available moves
     * @param start
     *      The starting tile the user clicked on
     * @param board
     *      The board object itself
     * @return
     *      The list of all the available moves to be used as a means of comparison
     */
    private ArrayList<Tile> availMoves(Tile start, Tile[][] board) {
        // if captureResult has any moves, remove moveResult from possible moves (force capture)
        ArrayList<Tile> moveResult = new ArrayList<Tile>();
        ArrayList<Tile> captureResult = new ArrayList<Tile>();

        //Adds the starting Tile at the beginning of the array list
        moveResult.add(start); captureResult.add(start);

        int row = start.getRow();
        int col = start.getCol();
        boolean startIsKing = start.getIsKing();
        Tile.Value startVal = start.getValue();

        //Black piece movements
        if (startVal.equals(Tile.Value.BLACK) || startIsKing) {
            if (validMove(row - 1, col - 1, board)) {//Top left
                moveResult.add(board[row - 1][col - 1]);
            }
            if (validMove(row - 1, col + 1, board)) {//Top right
                moveResult.add(board[row - 1][col + 1]);
            }
        }

        //Red piece movements
        if (startVal == Tile.Value.RED || startIsKing) {
            if (validMove(row + 1, col - 1, board)) {//Bottom left
                moveResult.add(board[row + 1][col - 1]);
            }
            if (validMove(row + 1, col + 1, board)) {//Bottom right
                moveResult.add(board[row + 1][col + 1]);
            }
        }

        captureResult = capturePiece(start, board, captureResult);

        if (captureResult.size() != 1) {
            return captureResult;
        } else {
            return moveResult;
        }
    }//availMoves

    /**
     * --- HELPER METHOD  ---
     * Checks if the given tile is valid
     * @return True if valid, false if out of bounds or is empty
     */
    private boolean validMove(int row, int col, Tile[][] board) {
        if (row < 0 || col < 0 || row >= CheckersState.WIDTH || col >= CheckersState.HEIGHT) {
            return false;
        } else if (board[row][col].getValue() != Tile.Value.EMPTY) {
            return false;
        }
        return true;
    }//validMove

    /**
     * --- HELPER METHOD (for availMoves) ---
     * A recursive function that checks for moves that will
     * capture a diagonally placed piece
     * @param start
     *      The starting tile we are checking
     * @param board
     *      The board itself
     * @param captureResult
     *      The arraylist of tiles that are constantly being updated with
     *      all possible moves that capture a piece
     * @return
     *      The completed list of possible capture moves
     */
    private ArrayList<Tile> capturePiece(Tile start, Tile[][] board, ArrayList<Tile> captureResult) {
        int row = start.getRow();
        int col = start.getCol();
        boolean startIsKing = start.getIsKing();
        Tile.Value startVal = start.getValue();

        // Black piece captures + Red King captures
        if (startVal.equals(Tile.Value.BLACK) || (startVal.equals(Tile.Value.RED) && startIsKing)) {
            //makes sure the diagonal piece is an opposing piece + the space afterwards is valid + the spot had not already been checked
            if (validMove(row - 2, col - 2, board) && oppPiece(startVal, board[row - 1][col - 1].getValue()) && //Top Left
                    !captureResult.contains(board[row - 2][col - 2])) {

                captureResult.add(board[row - 2][col - 2]);

                //Recursive call for double captures
                capturePiece(board[row - 2][col - 2], board, captureResult);
            }
            if (validMove(row - 2, col + 2, board) && oppPiece(startVal, board[row - 1][col + 1].getValue()) && //Top right
                    !captureResult.contains(board[row - 2][col + 2])) {
                captureResult.add(board[row - 2][col + 2]);
                capturePiece(board[row - 2][col + 2], board, captureResult);
            }
        }

        // Red piece captures + Black King captures
        if (startVal.equals(Tile.Value.RED) || (startVal.equals(Tile.Value.BLACK) && startIsKing)) {
            if (validMove(row + 2, col - 2, board) && oppPiece(startVal, board[row + 1][col - 1].getValue()) && //Bottom left
                    !captureResult.contains(board[row + 2][col - 2])) {
                captureResult.add(board[row + 2][col - 2]);
                capturePiece(board[row + 2][col - 2], board, captureResult);
            }
            if (validMove(row + 2, col + 2, board) && oppPiece(startVal, board[row + 1][col + 1].getValue()) && //Bottom right
                    !captureResult.contains(board[row + 2][col + 2])) {
                captureResult.add(board[row + 2][col + 2]);
                capturePiece(board[row + 2][col + 2], board, captureResult);
            }
        }

        return captureResult;
     }//capturePiece

    /**
     * --- HELPER METHOD (for capturePiece) ---
     * Checks if the piece to be captured is of the enemy team
     * @param start
     *      The value of the piece we are moving
     * @param target
     *      The value of the piece we are capturing
     * @return
     *      True if the target piece is an enemy piece, false if not
     */
    public boolean oppPiece(Tile.Value start, Tile.Value target) {
        if (start.equals(Tile.Value.RED)) {
            return target.equals(Tile.Value.BLACK);
        } else if (start.equals(Tile.Value.BLACK)) {
            return target.equals(Tile.Value.RED);
        }
        return false;
    }//oppPiece

    /**
     * Updates all available spots to the corresponding value
     * within the board
     * @param availMoves
     *      The arraylist of all possible moves on the clicked piece
     */
    private Tile[][] toggleAvail(ArrayList<Tile> availMoves, Tile[][] board) {
        if (availMoves.size() == 0) return null;

        //Marks the clicked piece
        Tile start = availMoves.get(0);
        board[start.getRow()][start.getCol()].setIsStart(true);

        for (int i = 1; i < availMoves.size(); i++) {
            int row = availMoves.get(i).getRow();
            int col = availMoves.get(i).getCol();
            board[row][col].setValue(Tile.Value.AVAIL);
        }
        return board;
    }//toggleAvail

    /**
     * Reverts back all the available tiles back into empty tiles
     * and unmarks the marked starting piece
     * @param board
     */
    public Tile[][] revertAvail(Tile[][] board) {
        Tile temp;
        for (int row = 0; row < CheckersState.HEIGHT; row++) {
            for (int col = 0; col < CheckersState.WIDTH; col++) {
                temp = board[row][col];
                if (temp.getValue().equals(Tile.Value.AVAIL)) {
                    board[row][col].setValue(Tile.Value.EMPTY);
                } else if (temp.getIsStart()){
                    board[row][col].setIsStart(false);
                }
            }
        }
        return board;
    }//toggleBoard

    @Override
    protected String checkIfGameOver() {
        //if (# of available moves for player 1) == 0
        //return "Player 2 has won the game";
        //else if (# of available moves for player 1 == 0){//Checks player2
        //return "Player 1 has won the game";

        //assign playerValue
        int turn = ((CheckersState) state).getTurn();

        Tile.Value playerValue;
        if(turn == 0) {
            playerValue = Tile.Value.BLACK;
        }
        else {
            playerValue = Tile.Value.RED;
        }
        boolean hasMoves = false;

        // Loop through all tiles on the board and call availMoves
        Tile[][] board = ((CheckersState) state).getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Tile tile = board[row][col];
                if (tile.getValue() == playerValue) {
                    ArrayList<Tile> moves = availMoves(tile, board);
                    if (!moves.isEmpty()) {
                        hasMoves = true;
                        break;
                    }
                }
            }
            if (hasMoves) {
                break;
            }
        }

        if (!hasMoves) {
            String winner = (turn == 0) ? "Player 2" : "Player 1";
            return winner + " has won the game";
        }

        //else return null;
        return null;

    }//checkIfGameOver

}//LocalCheckers
