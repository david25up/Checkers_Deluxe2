package com.example.checkers_deluxe2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.checkers_deluxe2.InfoMessage.CheckersState;

/*
 * Sets everything up
 *
 * @author   Ashton Char
 * @author   Eli Marcelino
 * @author   Matt David
 * @version  March 2023
 */

public class CheckersMainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button run_test = findViewById(R.id.run_test);
        run_test.setOnClickListener(this);
    }


    public void onClick(View button) {
        //Run Test Button
        if (button.getId() == R.id.run_test) {
            Log.d("Run Test Button", "Button has been clicked");
            EditText editText = findViewById(R.id.editTextTextMultiLine);
            editText.clearComposingText();

            //Instances before any changes/moves are made
            CheckersState firstInstance = new CheckersState();

            //The simulated turn will now begin here moving one black piece
            firstInstance.initBoard();
            Tile[][] tempBoard = firstInstance.getBoard();

            CheckersState secondInstance = new CheckersState(firstInstance);

            firstInstance.swapPieces(tempBoard[2][3], tempBoard[3][4]);
            editText.append("Player 1 (black) has moved their first piece\n\n");

            //Instances after any changes/moves are made
            CheckersState thirdInstance = firstInstance;


            // Prints out the text for both the before and after game
            // states and puts it into the multi-line Edit Text
            editText.append(secondInstance.toString());
            editText.append(thirdInstance.toString());
        }
    }//onClick

    // tap a piece and see its available moves
    public void checkMoves(Tile start) {
        // remove all present marks in a loop
        // flag if there is a legal capture elsewhere
        // if (capture available, mark capture and call checkMovesHelper on that square)
        // if (forward diagonals are empty, mark those diagonals)
        // if (king, check backward diagonals)
        // invalidate
    }

    private void checkMovesHelper(Tile curr) {
        // recursively check if available captures are available on curr
            // if (available capture, mark jump and recall helper method)
                // if king, also check backward
            // call checkMovesHelper
    }

}