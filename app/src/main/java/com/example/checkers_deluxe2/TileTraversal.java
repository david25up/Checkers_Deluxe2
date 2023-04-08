package com.example.checkers_deluxe2;

import java.util.ArrayList;

public class TileTraversal {

    Tile start;

    ArrayList<Tile> traversal;

    TileTraversal(Tile start) {
        this.start = start;
        this.traversal = new ArrayList<Tile>();
        traversal.add(start);
    }
}
