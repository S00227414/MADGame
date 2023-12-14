package com.example.madgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    private int score = 0;

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

    private int calculateScore() {
        int correctGuesses = currentIndex; // Assuming currentIndex represents the count of correct guesses
        return correctGuesses * 10; // Assign 10 points for each correct guess
    }
    // Method called when any input button is clicked

    private void onInputButtonClick(Button clickedButton) {
        int clickedButtonIndex = inputButtons.indexOf(clickedButton);
        int expectedButtonIndex = displayedSequence.get(currentIndex);

        if (clickedButtonIndex == expectedButtonIndex) {
            currentIndex++;
            score += 10; // Increment the score by 10 for each correct button pressed

            if (currentIndex == displayedSequence.size()) {
                // User successfully replicated the sequence
                Toast.makeText(this, "Sequence matched! Score: " + score, Toast.LENGTH_SHORT).show();
                // Communicate success back to PlayGameActivity or perform the necessary action
                saveScoreToDatabase(score); // Save the score to the database
                Intent intent = new Intent(PlayerInputActivity.this, GameOverActivity.class);
                intent.putExtra("score", score); // Pass the score to the Game Over activity
                startActivity(intent);
                finish(); // Finish this activity
            }
        } else {
            // Incorrect sequence input
            Toast.makeText(this, "Incorrect sequence! Score: " + score, Toast.LENGTH_SHORT).show();

            saveScoreToDatabase(score); // Save the score to the database
            Intent intent = new Intent(PlayerInputActivity.this, GameOverActivity.class);
            intent.putExtra("score", score); // Pass the score to the Game Over activity
            startActivity(intent);
            finish(); // Finish this activity
        }
    }

    private void saveScoreToDatabase(int score) {
        HighScoresDatabaseHelper dbHelper = new HighScoresDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Get the player name (you may have a method to retrieve the name)
        String playerName = "Player"; // Replace this with the actual player name

        ContentValues values = new ContentValues();
        values.put("PlayerName", playerName);
        values.put("Score", score);

        db.insert("HighScores", null, values);
        db.close();
    }

}

