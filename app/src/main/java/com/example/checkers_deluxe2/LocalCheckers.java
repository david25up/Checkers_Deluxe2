package com.example.checkers_deluxe2;

/*
 * Makes changes to the game state given specific
 * actions from players.
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  April 2023
 */

import static android.os.SystemClock.sleep;

import android.util.Log;

import com.example.GameFramework.LocalGame;
import com.example.GameFramework.actionMessage.GameAction;
import com.example.GameFramework.players.GamePlayer;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.actionMessage.CheckersDumbAIAction;
import com.example.checkers_deluxe2.actionMessage.CheckersResetAction;
import com.example.checkers_deluxe2.actionMessage.CheckersSmartAIAction;
import com.example.checkers_deluxe2.actionMessage.CheckersTapAction;
import com.example.GameFramework.gameConfiguration.GamePlayerType;
import com.example.checkers_deluxe2.Players.CheckersSmartAIPlayer;

import java.util.ArrayList;

public class LocalCheckers extends LocalGame {
    // Tag for logging //
    private static final String TAG = "LocalCheckers";

    /**
     * Default constructor for LocalCheckers
     */
    public LocalCheckers() {
        super();
        CheckersState checkersState = new CheckersState();
        checkersState.initBoard();
        super.state = checkersState;
    }//default ctor

    /**
     * Constructor for LocalCheckers given a CheckersState object
     */
    public LocalCheckers(CheckersState checkersState) {
        super();
        checkersState.initBoard();
        super.state = new CheckersState(checkersState);
    }//ctor

    /**
     * This is where you should initialize anything specific to the
     * number of players.  For example you may need to init your
     * game state or part of it.  Loading data could also happen here.
     *
     * @param players the array that holds all of the players in the game
     */
    @Override
    public void start(GamePlayer[] players) {
        super.start(players);
    }//start

    /**
     * Sends the updated state of the game to the player
     *
     * @param p the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new CheckersState((CheckersState) state));
    }//sendUpdatedStateTo

    /**
     * --- HELPER METHOD ---
     * Makes sure the player can move
     *
     * @param playerIdx the player's player-number (ID)
     * @return returns true if the given player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return (playerIdx == ((CheckersState) state).getTurn());
    }//canMove

    @Override
    protected boolean makeMove(GameAction action) {
        Tile.Value playerValue = findPlayerVal();

        if (action instanceof CheckersDumbAIAction) {
            ArrayList<ArrayList<TileTraversal>> allPossMoves = new ArrayList<>();
            ArrayList<TileTraversal> moves;
            Tile[][] board = ((CheckersState) state).getBoard();

            for (int row = 0; row < CheckersState.HEIGHT; row++) {
                for (int col = 0; col < CheckersState.WIDTH; col++) {
                    if (board[row][col].getValue().equals(playerValue)) {
                        moves = availMoves(board[row][col], board);
                        if (moves.size() != 0) {allPossMoves.add(moves);}
                    }
                }
            }

            int randPiece = (int) (Math.random() * (allPossMoves.size()));
            moves = allPossMoves.get(randPiece);
            int randMove = (int) (Math.random() * (moves.size()));
            Log.d("Dumb AI", "Moving piece"); sleep(1000);
            ((CheckersState) state).setBoard(toggleAvail(moves, board));
            ((CheckersState) state).movePieces(moves.get(randMove));
            ((CheckersState) state).setBoard((revertAvail(board)));
            ((CheckersState) state).flipTurn();//ends turn here
            Log.d("Dumb AI", "Job's done");
            return true;
        }//Dumb AI's turn :)

        else if (action instanceof CheckersSmartAIAction) {
            ArrayList<ArrayList<TileTraversal>> allPossMoves = new ArrayList<>();
            ArrayList<TileTraversal> moves, maxMoves;
            int numBackRow = 0;
            maxMoves = new ArrayList<>();
            Tile[][] board = ((CheckersState) state).getBoard();

            //Counts the number of pieces in the back row
            for (int col = 0; col < CheckersState.WIDTH; col++) {
                if (board[0][col].getValue().equals(playerValue)) {
                    numBackRow++;
                }
            }

            //Gets all possible moves excluding the back row
            for (int row = 1; row < CheckersState.HEIGHT; row++) {
                for (int col = 0; col < CheckersState.WIDTH; col++) {
                    if (board[row][col].getValue().equals(playerValue)) {
                        moves = availMoves(board[row][col], board);
                        if (moves.size() != 0) {allPossMoves.add(moves);}
                    }
                }
            }

            //Only checks the back row if there are no more possible moves forwards
            //or one of the back row pieces have already been moved
            if (allPossMoves.size() == 0 || numBackRow < 4) {
                for (int col = 0; col < CheckersState.WIDTH; col++) {
                    if (board[0][col].getValue().equals(playerValue)) {
                        moves = availMoves(board[0][col], board);
                        if (moves.size() != 0) {allPossMoves.add(moves);}
                    }
                }
            } if (allPossMoves.size() == 0) {return true;}//No possible moves

            //Finds the longest piece so among all the traversals to emphasize capturing pieces
            int greatestSoFar = 0;
            for (int i = 0; i < allPossMoves.size(); i++) {
                moves = allPossMoves.get(i);
                for (int j = 0; j < moves.size(); j++) {
                    if (moves.get(j).getTraversalLength() > greatestSoFar) {
                        maxMoves = new ArrayList<>();
                        greatestSoFar = moves.get(j).getTraversalLength();//resets the list
                        maxMoves.add(moves.get(j));
                    } else if (moves.get(j).getTraversalLength() == greatestSoFar) {
                        maxMoves.add(moves.get(j));
                    }
                }
            }

            int randMove = (int) (Math.random() * (maxMoves.size()));
            Log.d("Smart AI", "Moving piece"); sleep(1000);
            ((CheckersState) state).setBoard(toggleAvail(maxMoves, board));
            ((CheckersState) state).movePieces(maxMoves.get(randMove));
            ((CheckersState) state).setBoard((revertAvail(board)));
            ((CheckersState) state).flipTurn();//ends turn here
            Log.d("Smart AI", "Job's done");
            return true;
        }//Smart AI's turn :)

        else if (action instanceof CheckersTapAction) {
            CheckersTapAction cm = (CheckersTapAction) action;

            int row = cm.getRow();
            int col = cm.getCol();
            Tile[][] board = ((CheckersState) state).getBoard();
            int turn = ((CheckersState) state).getTurn();
            if (board[row][col].getValue().equals(Tile.Value.AVAIL)) {
                ArrayList<TileTraversal> moves = ((CheckersState) state).getMoves();
                ((CheckersState) state).movePieces(findTT(board[row][col], moves));
                ((CheckersState) state).setMoves(null);
                ((CheckersState) state).setBoard((revertAvail(board)));
                ((CheckersState) state).flipTurn();//ends turn here
                Log.d("Human", "Job's done");
            } else if (board[row][col].getValue().equals(Tile.Value.BLACK) && turn == 0) {//This needs to be changed so it's dependant on the player's color, not always black
                Log.d("Human", "Piece was tapped");
                ((CheckersState) state).setBoard(revertAvail(board));
                ArrayList<TileTraversal> moves = availMoves(board[row][col], board);
                ((CheckersState) state).setMoves(moves);
                if (toggleAvail(moves, board) != null) {
                    ((CheckersState) state).setBoard(toggleAvail(moves, board));
                }
                //does not end the player's turn, but changes visuals so the
                //possible moves are shown
            } else if (board[row][col].getValue().equals(Tile.Value.RED) && turn == 1) {
                ((CheckersState) state).setBoard(revertAvail(board));
                ArrayList<TileTraversal> moves = availMoves(board[row][col], board);
                ((CheckersState) state).setMoves(moves);
                if (toggleAvail(moves, board) != null) {
                    ((CheckersState) state).setBoard(toggleAvail(moves, board));
                }
            }
            return true;
        }//Human Player's turn

        if (action instanceof CheckersResetAction) {
            ((CheckersState) state).resetBoard();
        }//Reset Button Clicked

        return false;
    }//makeMove

    /**
     * --- HELPER METHOD ---
     * Finds the TileTraversal corresponding to the available
     * tile the user clicked
     *
     * @param avail The spot the user clicked to move the piece to
     * @return The TileTraversal corresponding to the available
     * tile the user clicked
     */
    private TileTraversal findTT(Tile avail, ArrayList<TileTraversal> moves) {
        Tile dest;
        int row = avail.getRow();
        int col = avail.getCol();
        for (int i = 0; i < moves.size(); i++) {
            dest = moves.get(i).getDestination();
            if ((dest.getRow() == row && dest.getCol() == col)) {
                return moves.get(i);
            }
        }
        return null;
    }//findTT

    /**
     * Checks the surrounding tiles for valid and available moves
     *
     * @param start The starting tile the user clicked on
     * @param board The board object itself
     * @return The list of all the available moves to be used as a means of comparison
     */
    private ArrayList<TileTraversal> availMoves(Tile start, Tile[][] board) {
        // if captureResult has any moves, remove moveResult from possible moves (force capture)
        ArrayList<TileTraversal> moveResult = new ArrayList<TileTraversal>();
        ArrayList<TileTraversal> captureResult = new ArrayList<TileTraversal>();

        int row = start.getRow();
        int col = start.getCol();
        boolean startIsKing = start.getIsKing();
        Tile.Value startVal = start.getValue();

        //Black piece movements
        if (startVal.equals(Tile.Value.BLACK) || startIsKing) {
            if (validMove(row - 1, col - 1, board)) {//Top left
                TileTraversal result = new TileTraversal(start);
                result.addTile(board[row - 1][col - 1]);
                moveResult.add(result);
            }
            if (validMove(row - 1, col + 1, board)) {//Top right
                TileTraversal result = new TileTraversal(start);
                result.addTile(board[row - 1][col + 1]);
                moveResult.add(result);
            }
        }

        //Red piece movements
        if (startVal == Tile.Value.RED || startIsKing) {
            if (validMove(row + 1, col - 1, board)) {//Bottom left
                TileTraversal result = new TileTraversal(start);
                result.addTile(board[row + 1][col - 1]);
                moveResult.add(result);
            }
            if (validMove(row + 1, col + 1, board)) {//Bottom right
                TileTraversal result = new TileTraversal(start);
                result.addTile(board[row + 1][col + 1]);
                moveResult.add(result);
            }
        }

        captureResult = (capturePiece(start, captureResult, board, start));

        if (captureResult.size() != 0) {
            return captureResult;
        } else {
            return moveResult;
        }
    }//availMoves

    /**
     * --- HELPER METHOD  ---
     * Checks if the given tile is valid
     *
     * @return True if valid, false if out of bounds or is empty
     */
    private boolean validMove(int row, int col, Tile[][] board) {
        if (row < 0 || col < 0 || row >= CheckersState.HEIGHT || col >= CheckersState.WIDTH) {
            return false;
        } else if (board[row][col].getValue() == Tile.Value.RED || board[row][col].getValue() == Tile.Value.BLACK) {
            return false;
        }
        return true;
    }//validMove

    /**
     * --- HELPER METHOD (for availMoves) ---
     * A recursive function that checks for moves that will
     * capture a diagonally placed piece
     *
     * @param start         The starting tile of the piece that has been clicked
     * @param board         The board itself
     * @param captureResult The arraylist of tiles that are constantly being updated
     *                      with all possible moves that capture a piece
     * @return The completed list of possible capture moves
     */
    private ArrayList<TileTraversal> capturePiece(Tile start, ArrayList<TileTraversal> captureResult, Tile[][] board, Tile updated) {
        int row = updated.getRow();
        int col = updated.getCol();
        boolean startIsKing = start.getIsKing();
        Tile.Value startVal = start.getValue();
        ArrayList<Tile> destinations = getDestList(captureResult);

        // Black piece captures + Red King captures
        if (startVal.equals(Tile.Value.BLACK) || (startVal.equals(Tile.Value.RED) && startIsKing)) {
            //makes sure the diagonal piece is an opposing piece + the space afterwards is valid + the spot had not already been checked
            if (validMove(row - 2, col - 2, board)) {
                if (oppPiece(startVal, board[row - 1][col - 1].getValue()) && //Top Left
                        !destinations.contains(board[row - 2][col - 2])) {

                    TileTraversal path;
                    TileTraversal temp = findTT(board[row][col], captureResult);
                    if (temp == null) {//Checks to see if this is a recursive call
                        path = new TileTraversal(start);
                    } else {
                        path = new TileTraversal(temp);
                    }
                    path.addTile(board[row - 1][col - 1]); //Adds the piece to be captured as well
                    path.addTile(board[row - 2][col - 2]);
                    captureResult.add(path);

                    //Recursive call for double captures
                    ArrayList<TileTraversal> recCall = capturePiece(start, captureResult, board, board[row - 2][col - 2]);
                    ArrayList<Tile> recDest = getDestList(recCall);
                    Tile dest;
                    for (int i = 0; i < destinations.size(); i++) {//Double check for duplicates
                        dest = destinations.get(i);
                        for (int j = 0; j < recDest.size(); j++)
                            if (!recDest.contains(dest)) {
                            captureResult.add(recCall.get(i));
                        }
                    }
                }
            }
            if (validMove(row - 2, col + 2, board)) {
                if (oppPiece(startVal, board[row - 1][col + 1].getValue()) && //Top Right
                        !destinations.contains(board[row - 2][col + 2])) {

                    TileTraversal path;
                    TileTraversal temp = findTT(board[row][col], captureResult);
                    if (temp == null) {
                        path = new TileTraversal(start);
                    } else {
                        path = new TileTraversal(temp);
                    }
                    path.addTile(board[row - 1][col + 1]);
                    path.addTile(board[row - 2][col + 2]);
                    captureResult.add(path);

                    ArrayList<TileTraversal> recCall = capturePiece(start, captureResult, board, board[row - 2][col + 2]);
                    ArrayList<Tile> recDest = getDestList(recCall);
                    Tile dest;
                    for (int i = 0; i < destinations.size(); i++) {
                        dest = destinations.get(i);
                        for (int j = 0; j < recDest.size(); j++)
                            if (!recDest.contains(dest)) {
                                captureResult.add(recCall.get(i));
                            }
                    }
                }
            }
        }

        // Red piece captures + Red recursive call + Black King captures
        if (startVal.equals(Tile.Value.RED) || (startVal.equals(Tile.Value.BLACK) && startIsKing)) {
            if (validMove(row + 2, col - 2, board)) {
                if (oppPiece(startVal, board[row + 1][col - 1].getValue()) && //Bottom Left
                        !destinations.contains(board[row + 2][col - 2])) {

                    TileTraversal path;
                    TileTraversal temp = findTT(board[row][col], captureResult);
                    if (temp == null) {
                        path = new TileTraversal(start);
                    } else {
                        path = new TileTraversal(temp);
                    }
                    path.addTile(board[row + 1][col - 1]);
                    path.addTile(board[row + 2][col - 2]);
                    captureResult.add(path);

                    ArrayList<TileTraversal> recCall = capturePiece(start, captureResult, board, board[row + 2][col - 2]);
                    ArrayList<Tile> recDest = getDestList(recCall);
                    Tile dest;
                    for (int i = 0; i < destinations.size(); i++) {
                        dest = destinations.get(i);
                        for (int j = 0; j < recDest.size(); j++)
                            if (!recDest.contains(dest)) {
                                captureResult.add(recCall.get(i));
                            }
                    }
                }
            }
            if (validMove(row + 2, col + 2, board)) {
                if (oppPiece(startVal, board[row + 1][col + 1].getValue()) && //Bottom Right
                        !destinations.contains(board[row + 2][col + 2])) {

                    TileTraversal path;
                    TileTraversal temp = findTT(board[row][col], captureResult);
                    if (temp == null) {
                        path = new TileTraversal(start);
                    } else {
                        path = new TileTraversal(temp);
                    }
                    path.addTile(board[row + 1][col + 1]);
                    path.addTile(board[row + 2][col + 2]);
                    captureResult.add(path);

                    ArrayList<TileTraversal> recCall = capturePiece(start, captureResult, board, board[row + 2][col + 2]);
                    ArrayList<Tile> recDest = getDestList(recCall);
                    Tile dest;
                    for (int i = 0; i < destinations.size(); i++) {
                        dest = destinations.get(i);
                        for (int j = 0; j < recDest.size(); j++)
                            if (!recDest.contains(dest)) {
                                captureResult.add(recCall.get(i));
                            }
                    }
                }
            }
        }

        return captureResult;
    }//capturePiece

    /**
     * --- HELPER METHOD (for capturePiece) ---
     * Checks if the piece to be captured is of the enemy team
     *
     * @param start  The value of the piece we are moving
     * @param target The value of the piece we are capturing
     * @return True if the target piece is an enemy piece, false if not
     */
    private boolean oppPiece(Tile.Value start, Tile.Value target) {
        if (start.equals(Tile.Value.RED)) {
            return target.equals(Tile.Value.BLACK);
        } else if (start.equals(Tile.Value.BLACK)) {
            return target.equals(Tile.Value.RED);
        }
        return false;
    }//oppPiece

    /**
     * --- HELPER METHOD (for capturePiece) --
     * Used to check if a spot has been checked already to avoid
     * going backwards/stuck in a loop
     *
     * @param moves The ArrayList of possible moves
     * @return An ArrayList of all the possible destinations
     */
    private ArrayList<Tile> getDestList(ArrayList<TileTraversal> moves) {
        ArrayList<Tile> destinations = new ArrayList<Tile>();
        for (int i = 0; i < moves.size(); i++) {
            destinations.add(moves.get(i).getDestination());
        }
        return destinations;
    }//getDestList

    /**
     * Updates all available spots to the corresponding value
     * within the board
     *
     * @param moves The arraylist of all possible moves on the clicked piece
     */
    private Tile[][] toggleAvail(ArrayList<TileTraversal> moves, Tile[][] board) {
        if (moves.size() == 0) {return null;}

        //Marks the clicked piece
        Tile start = moves.get(0).getStart();
        board[start.getRow()][start.getCol()].setIsStart(true);

        Tile dest;
        for (int i = 0; i < moves.size(); i++) {
            dest = moves.get(i).getDestination();
            board[dest.getRow()][dest.getCol()].setValue(Tile.Value.AVAIL);
        }
        return board;
    }//toggleAvail

    /**
     * Reverts back all the available tiles back into empty tiles
     * and unmarks the marked starting piece
     *
     * @param board
     */
    public Tile[][] revertAvail(Tile[][] board) {
        Tile temp;
        for (int row = 0; row < CheckersState.WIDTH; row++) {
            for (int col = 0; col < CheckersState.HEIGHT; col++) {
                temp = board[row][col];
                if (temp.getValue().equals(Tile.Value.AVAIL)) {
                    board[row][col].revertTile();
                } else if (temp.getIsStart()) {
                    board[row][col].setIsStart(false);
                }
            }
        }
        return board;
    }//toggleBoard

    /**
     * Determines what color the current player is
     * @return
     *      The color value of the player moving, where player 0
     *      is always black and player 1 is always red
     */
    private Tile.Value findPlayerVal() {
        if (((CheckersState) state).getTurn() == 0) {
            return Tile.Value.BLACK;
        } else {
            return Tile.Value.RED;
        }
    }//findPlayerVal

    /**
     * Determines whether or not a game is over based on the amount
     * of available moves left for the current player's turn
     *
     * @return Corresponding "Player _ wins" when an opposing player
     * has run out of moves
     */
    @Override
    protected String checkIfGameOver() {
        int turn = ((CheckersState) state).getTurn();

        Tile.Value playerValue = findPlayerVal();
        boolean hasMoves = false;

        // Loop through all tiles on the board and call availMoves
        Tile[][] board = ((CheckersState) state).getBoard();
        for (int row = 0; row < CheckersState.HEIGHT; row++) {
            for (int col = 0; col < CheckersState.WIDTH; col++) {
                Tile tile = board[row][col];
                if (tile.getValue().equals(playerValue)) {
                    ArrayList<TileTraversal> moves = availMoves(tile, board);
                    //Looks for a case where the player has moves, in which
                    //case the game is not over
                    if (moves.size() != 0) {
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
            //This is a one-line if-else statement where if the condition is true,
            //the first option is used, while the second option is used when false
            String winner = (turn == 0) ? "Player 2" : "Player 1";
            Log.d("GAME OVER", winner + " has won! ");
            return winner + " has won! ";
        }

        return null;
    }//checkIfGameOver

}//LocalCheckers
