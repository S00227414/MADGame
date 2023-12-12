package com.example.madgame;
// HighScoresDatabaseHelper.java

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoresDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "high_scores.db";
    private static final int DATABASE_VERSION = 1;

    public HighScoresDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create the HighScores table
        String CREATE_HIGH_SCORES_TABLE = "CREATE TABLE HighScores (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PlayerName TEXT," +
                "Score INTEGER)";

        // Execute the SQL statement
        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // This method is called when DATABASE_VERSION is incremented
    }
}
