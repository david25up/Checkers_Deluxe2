package com.example.checkers_deluxe2.tests;


import android.content.Context;

import com.example.GameFramework.animation.AnimationSurface;
import com.example.GameFramework.utilities.GameTimer;
import com.example.GameFramework.utilities.Tickable;
import com.example.checkers_deluxe2.InfoMessage.CheckersState;
import com.example.checkers_deluxe2.Tile;
import java.awt.*;



import org.testng.annotations.Test;
import static org.junit.Assert.*;

/** class to dummy test the game */
public class CheckersTest {


        @Test
        public void testTile() {
            Tile blackTile = new Tile(Tile.Color.BLACK, 2, 3);
            Tile redTile = new Tile(Tile.Color.RED, 5, 6);

            assertEquals(Tile.Color.BLACK, blackTile.getColor());
            assertEquals(2, blackTile.getRow());
            assertEquals(3, blackTile.getCol());

            assertEquals(Tile.Color.RED, redTile.getColor());
            assertEquals(5, redTile.getRow());
            assertEquals(6, redTile.getCol());
        }

        @Test
        public void testCheckersState() {
            CheckersState state = new CheckersState();

            // test initial state
            assertEquals(CheckersState.Player.RED, state.getCurrentPlayer());
            assertEquals(12, state.getRedPieces().size());
            assertEquals(12, state.getBlackPieces().size());

            // move red piece and check state updates
            CheckersState.Move move = new CheckersState.Move(5, 0, 4, 1);
            state.makeMove(move);
            assertEquals(CheckersState.Player.BLACK, state.getCurrentPlayer());
            assertEquals(11, state.getRedPieces().size());
            assertEquals(12, state.getBlackPieces().size());
            assertTrue(state.getTileAt(4, 1).isOccupied());
            assertFalse(state.getTileAt(5, 0).isOccupied());

            // move black piece and check state updates
            move = new CheckersState.Move(2, 1, 3, 0);
            state.makeMove(move);
            assertEquals(CheckersState.Player.RED, state.getCurrentPlayer());
            assertEquals(11, state.getRedPieces().size());
            assertEquals(11, state.getBlackPieces().size());
            assertTrue(state.getTileAt(3, 0).isOccupied());
            assertFalse(state.getTileAt(2, 1).isOccupied());
        }


}
