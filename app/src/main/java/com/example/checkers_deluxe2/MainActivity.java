package com.example.checkers_deluxe2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.checkers_deluxe2.InfoMessage.CheckersState;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button run_test = (Button) findViewById(R.id.run_test);
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
            CheckersState secondInstance = new CheckersState(firstInstance);

            //The simulated turn will now begin here moving one black piece
            //Tile[][] tempBoard = firstInstance.getBoard();


            //firstInstance.swapPieces(tempBoard[0][3], tempBoard[1][4]);
            editText.append("Player 1 (black) has moved their first piece");

            //Instances after any changes/moves are made
            CheckersState thirdInstance = firstInstance;
            CheckersState fourthInstance = new CheckersState(thirdInstance);

            // Prints out the text for both the before and after game
            // states and puts it into the multi-line Edit Text
            //editText.append(secondInstance.toString());
            //editText.append(fourthInstance.toString());
        }
    }//onClick
}