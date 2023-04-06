package com.example.checkers_deluxe2;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.infoMessage.GameState;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;

import java.util.ArrayList;

public class LocalCheckers extends LocalGame {

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
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new CheckersState((CheckersState) state));
    }//sendUpdatedStateTo

    /**
     *
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
    protected String checkIfGameOver() {
        //if (# of available moves for player 1) == 0
            //return "Player 2 has won the game";
        //else if (# of available moves for player 1 == 0){//Checks player2
            //return "Player 1 has won the game";
        //else return null;

        //temp
        return null;
    }//checkIfGameOver

    @Override
    protected boolean makeMove(GameAction action) {
        //if (action instanceof ...)
            //perform action here
            //return true

        //temp
        return true;
    }//makeMove

    public ArrayList<Tile> availMoves(Tile start, Tile[][] board) {
        ArrayList<Tile> moveResult = new ArrayList<Tile>();
        // if captureResult has any moves, remove moveResult
        ArrayList<Tile> captureResult = new ArrayList<Tile>();
        int row = start.getRow();
        int col = start.getCol();
        boolean isKing = start.getKing();

        if (start.getValue() == Tile.Value.BLACK) {
            if ((row+1 < 8) && (col-1 > 0) && board[row+1][col-1].getValue() == Tile.Value.EMPTY) {
                moveResult.add(board[row+1][col-1]);
                }
            if ((row+1 < 8) && (col+1 < 8) && board[row+1][col+1].getValue() == Tile.Value.EMPTY) {
                moveResult.add(board[row+1][col+1]);
                }
            if ((isKing && (row-1 > 0) && (col-1 > 0)) && board[row-1][col-1].getValue() == Tile.Value.EMPTY) {
                moveResult.add(board[row-1][col-1]);
                }
            if ((isKing && (row-1 > 0) && (col+1 < 8)) && board[row-1][col+1].getValue() == Tile.Value.EMPTY) {
                moveResult.add(board[row-1][col+1]);
                }
            // captures
            if ((row+2 < 8) && (col-2 > 0) && board[row+1][col-1].getValue() == Tile.Value.RED && board[row+2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col-2]);
                availMovesHelper(captureResult, board[row+2][col-2], isKing, Tile.Value.BLACK, board);
            }
            if ((row+2 < 8) && (col+2 < 8) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row+2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col+2]);
                availMovesHelper(captureResult, board[row+2][col+2], isKing, Tile.Value.BLACK, board);
            }
            if (isKing && (row-2 > 0) && (col-2 > 0) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row-2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col-2]);
                availMovesHelper(captureResult, board[row-2][col-2], isKing, Tile.Value.BLACK, board);
            }
            if (isKing && (row-2 > 0) && (col+2 > 8) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row-2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col+2]);
                availMovesHelper(captureResult, board[row-2][col+2], isKing, Tile.Value.BLACK, board);
            }
        } else if (start.getValue() == Tile.Value.RED) {
                if ((row-1 > 0) && (col-1 > 0) && board[row-1][col-1].getValue() == Tile.Value.EMPTY) {
                    moveResult.add(board[row-1][col-1]);
                }
                if ((row-1 > 0) && (col+1 < 8) && board[row-1][col+1].getValue() == Tile.Value.EMPTY) {
                    moveResult.add(board[row-1][col+1]);
                }
                if ((isKing && row+1 < 8) && (col-1 > 0) && board[row+1][col-1].getValue() == Tile.Value.EMPTY) {
                    moveResult.add(board[row+1][col-1]);
                }
                if ((isKing && row+1 < 8) && (col+1 < 8) && board[row-1][col+1].getValue() == Tile.Value.EMPTY) {
                    moveResult.add(board[row+1][col+1]);
                }
                if (isKing && (row+2 < 8) && (col-2 > 0) && board[row+1][col-1].getValue() == Tile.Value.BLACK && board[row+2][col-2].getValue() == Tile.Value.EMPTY) {
                    captureResult.add(board[row+2][col-2]);
                    availMovesHelper(captureResult, board[row+2][col-2], isKing, Tile.Value.RED, board);
                }
                if (isKing && (row+2 < 8) && (col+2 < 8) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row+2][col+2].getValue() == Tile.Value.EMPTY) {
                    captureResult.add(board[row+2][col+2]);
                    availMovesHelper(captureResult, board[row+2][col+2], isKing, Tile.Value.RED, board);
                }
                if ((row-2 > 0) && (col-2 > 0) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row-2][col-2].getValue() == Tile.Value.EMPTY) {
                    captureResult.add(board[row-2][col-2]);
                    availMovesHelper(captureResult, board[row-2][col-2], isKing, Tile.Value.RED, board);
                }
                if ((row-2 > 0) && (col+2 > 8) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row-2][col+2].getValue() == Tile.Value.EMPTY) {
                    captureResult.add(board[row-2][col+2]);
                    availMovesHelper(captureResult, board[row-2][col+2], isKing, Tile.Value.RED, board);
                }
            }
        if (captureResult.size() > 1) {
            return captureResult;
        } else {
            captureResult.addAll(moveResult);
            return captureResult;
        }
        }

    private void availMovesHelper(ArrayList<Tile> captureResult, Tile tile, boolean isKing, Tile.Value color, Tile[][] board) {
        int row = tile.getRow();
        int col = tile.getCol();
        if (color == Tile.Value.BLACK) {
            if ((row+2 < 8) && (col-2 > 0) && board[row+1][col-1].getValue() == Tile.Value.RED && board[row+2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col-2]);
                availMovesHelper(captureResult, board[row+2][col-2], isKing, Tile.Value.BLACK, board);
            }
            if ((row+2 < 8) && (col+2 < 8) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row+2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col+2]);
                availMovesHelper(captureResult, board[row+2][col+2], isKing, Tile.Value.BLACK, board);
            }
            if (isKing && (row-2 > 0) && (col-2 > 0) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row-2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col-2]);
                availMovesHelper(captureResult, board[row-2][col-2], isKing, Tile.Value.BLACK, board);
            }
            if (isKing && (row-2 > 0) && (col+2 > 8) && board[row+1][col+1].getValue() == Tile.Value.RED && board[row-2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col+2]);
                availMovesHelper(captureResult, board[row-2][col+2], isKing, Tile.Value.BLACK, board);
            }
        } else if (color == Tile.Value.RED) {
            if (isKing &&(row+2 < 8) && (col-2 > 0) && board[row+1][col-1].getValue() == Tile.Value.BLACK && board[row+2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col-2]);
                availMovesHelper(captureResult, board[row+2][col-2], isKing, Tile.Value.RED, board);
            }
            if (isKing && (row+2 < 8) && (col+2 < 8) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row+2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row+2][col+2]);
                availMovesHelper(captureResult, board[row+2][col+2], isKing, Tile.Value.RED, board);
            }
            if ( (row-2 > 0) && (col-2 > 0) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row-2][col-2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col-2]);
                availMovesHelper(captureResult, board[row-2][col-2], isKing, Tile.Value.RED, board);
            }
            if (isKing && (row-2 > 0) && (col+2 > 8) && board[row+1][col+1].getValue() == Tile.Value.BLACK && board[row-2][col+2].getValue() == Tile.Value.EMPTY) {
                captureResult.add(board[row-2][col+2]);
                availMovesHelper(captureResult, board[row-2][col+2], isKing, Tile.Value.RED, board);
            }

        }
    }


/*
    public void swapPieces(Tile piece, Tile blank) {
        if (!validMove(piece.getRow(), piece.getCol())) {
            return;
        }
        //Start putting piece1's data into piece2
        board[blank.getRow()][blank.getRow()] = piece;
        //Make piece1 empty
        board[piece.getRow()][piece.getRow()].setValue(Tile.Value.EMPTY);
        piece.setKing(false);
    }//swapPieces
*/

    public int whoWon(){
        String gameOver = checkIfGameOver();
        if(gameOver == null || gameOver.equals("It's a cat's game.")) return -1;
        if(gameOver.equals(playerNames[0]+" is the winner.")) return 0;
        return 1;
    }//whoWon
}
