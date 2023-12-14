package com.example.madgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class PlayerInputActivity extends AppCompatActivity {

    private List<Button> inputButtons;
    private List<Integer> displayedSequence;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_input);

        // Initialize buttons for user input
        Button colorTop = findViewById(R.id.colorTop);
        Button colorLeft = findViewById(R.id.colorLeft);
        Button colorRight = findViewById(R.id.colorRight);
        Button colorBottom = findViewById(R.id.colorBottom);

        // Store buttons in a list for easier access
        inputButtons = new ArrayList<>();
        inputButtons.add(colorTop);
        inputButtons.add(colorLeft);
        inputButtons.add(colorRight);
        inputButtons.add(colorBottom);

        // Retrieve the displayed sequence passed from PlayGameActivity
        displayedSequence = getIntent().getIntegerArrayListExtra("displayedSequence");

        // Start with the first button in the sequence
        currentIndex = 0;

        // Set click listeners for the input buttons
        for (Button button : inputButtons) {
            button.setOnClickListener(v -> onInputButtonClick(button));
        }
    }

    // Method called when any input button is clicked
    private void onInputButtonClick(Button clickedButton) {
        int clickedButtonIndex = inputButtons.indexOf(clickedButton);
        int expectedButtonIndex = displayedSequence.get(currentIndex);

        if (clickedButtonIndex == expectedButtonIndex) {
            currentIndex++;
            if (currentIndex == displayedSequence.size()) {
                // User successfully replicated the sequence
                Toast.makeText(this, "Sequence matched!", Toast.LENGTH_SHORT).show();
                // Communicate success back to PlayGameActivity or perform the necessary action
                // For example, finish() this activity to go back to the PlayGameActivity
                finish();
            }
        } else {
            // Incorrect sequence input
            Toast.makeText(this, "Incorrect sequence!", Toast.LENGTH_SHORT).show();
            // Handle incorrect input, e.g., go to the Game Over screen or reset the round
            // You can implement additional logic based on your game requirements
        }
    }
}
