package com.example.madgame;
// HighScoresDatabaseHelper.java

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HighScoresDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "high_scores.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "HighScores";
    private static final String COL_ID = "ID";
    private static final String COL_PLAYER_NAME = "PlayerName";
    private static final String COL_SCORE = "Score";

    public HighScoresDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIGH_SCORES_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_PLAYER_NAME + " TEXT," +
                COL_SCORE + " INTEGER)";
        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // This method is called when DATABASE_VERSION is incremented
    }

    public void addData(String playerName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PLAYER_NAME, playerName);
        values.put(COL_SCORE, score);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}

