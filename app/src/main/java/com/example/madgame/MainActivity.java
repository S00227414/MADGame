package com.example.madgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private HighScoresDatabaseHelper dbHelper;
    private EditText editTextName, editTextScore;
    private TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HighScoresDatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextScore = findViewById(R.id.editTextScore);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToDatabase();
                displayDataFromDatabase();
            }
        });
        Button btnDeleteAll = findViewById(R.id.btnDeleteAll);
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllEntries();
            }
        });

        Button btnPlayGame = findViewById(R.id.btnPlayGame);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Play Game activity here
                Intent intent = new Intent(MainActivity.this, PlayGameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void deleteLowScores(SQLiteDatabase db) {
        String selectQuery = "SELECT * FROM HighScores ORDER BY Score DESC LIMIT 5";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            int scoreColumnIndex = cursor.getColumnIndex("Score");
            int count = cursor.getCount();
            Log.d("TotalCount", "Total Count: " + count);

            // Keep only the top five scores
            if (count > 5) {
                cursor.moveToPosition(4); // Move to the 5th record

                // Check if the column index for "Score" is valid
                if (scoreColumnIndex != -1) {
                    @SuppressLint("Range") int thresholdScore = cursor.getInt(scoreColumnIndex);

                    // Log the threshold score
                    Log.d("ThresholdScore", "Threshold Score: " + thresholdScore);

                    // Delete scores lower than the threshold
                    int deletedRows = db.delete("HighScores", "Score < ?", new String[]{String.valueOf(thresholdScore)});
                    Log.d("DeletedRows", "Deleted Rows: " + deletedRows);
                } else {
                    Log.d("ScoreColumnError", "Column index for 'Score' not found.");
                }
            }
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void addDataToDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PlayerName", editTextName.getText().toString());
        values.put("Score", Integer.parseInt(editTextScore.getText().toString()));
        db.insert("HighScores", null, values);

        // Check and delete lower scores
        deleteLowScores(db);

        // Close the database connection after displaying the data
        displayDataFromDatabase();
        db.close();
    }





    private void displayDataFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HighScores ORDER BY Score DESC LIMIT 5", null);
        StringBuilder displayText = new StringBuilder("Top Five Scores:\n");


        if (cursor.moveToFirst()) {
            do {
                String playerName = cursor.getString(cursor.getColumnIndex("PlayerName"));
                int score = cursor.getInt(cursor.getColumnIndex("Score"));
                displayText.append("Name: ").append(playerName).append(", Score: ").append(score).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        textViewDisplay.setText(displayText.toString());
    }
    private void deleteAllEntries() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("HighScores", null, null); // Deletes all entries from the table
        db.close();

        // Refresh displayed data after deletion
        displayDataFromDatabase();
    }


    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}
