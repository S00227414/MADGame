package com.example.madgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // Retrieve the score passed from PlayGameActivity
        int score = getIntent().getIntExtra("score", 0);

        // Set up references to UI elements
        EditText editTextName = findViewById(R.id.editTextName);
        TextView textViewScore = findViewById(R.id.textViewScore);
        Button addButton = findViewById(R.id.buttonAddToScores);
        Button playAgainButton = findViewById(R.id.buttonPlayAgain);
        Button viewScoresButton = findViewById(R.id.buttonViewScores);

        // Set the received score to the score TextView
        textViewScore.setText(String.valueOf(score));

        // Implement onClickListener for "Add to High Scores" button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = editTextName.getText().toString().trim();

                if (!playerName.isEmpty()) {
                    // Add the name and score to the database using the HighScoresDatabaseHelper
                    HighScoresDatabaseHelper dbHelper = new HighScoresDatabaseHelper(GameOverActivity.this);
                    dbHelper.addData(playerName, score);
                    dbHelper.close();

                    // Optionally, display a success message (e.g., Toast)
                    Toast.makeText(GameOverActivity.this, "Score added to High Scores!", Toast.LENGTH_SHORT).show();
                } else {
                    // Display a message indicating the name is required
                    Toast.makeText(GameOverActivity.this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Implement onClickListener for "Play Again" button
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the PlayGameActivity for a new game
                Intent intent = new Intent(GameOverActivity.this, PlayGameActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish this activity if you don't want to go back to it from PlayGameActivity
            }
        });

        // Implement onClickListener for "View High Scores" button
        viewScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a message indicating the functionality is coming soon
                Toast.makeText(GameOverActivity.this, "View High Scores functionality coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
