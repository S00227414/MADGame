package com.example.madgame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
    }

    private void addDataToDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PlayerName", editTextName.getText().toString());
        values.put("Score", Integer.parseInt(editTextScore.getText().toString()));
        db.insert("HighScores", null, values);
        db.close();
    }

    private void displayDataFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM HighScores ORDER BY Score DESC", null);
        StringBuilder displayText = new StringBuilder("Player Data:\n");

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String playerName = cursor.getString(cursor.getColumnIndex("PlayerName"));
                @SuppressLint("Range") int score = cursor.getInt(cursor.getColumnIndex("Score"));
                displayText.append("Name: ").append(playerName).append(", Score: ").append(score).append("\n");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        textViewDisplay.setText(displayText.toString());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
