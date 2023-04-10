package com.example.gachagame.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseSQLite extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ClickerGame";

    private static final String TABLE_JOUEUR = "Joueur";
    private static final String COLUMN_JOUEUR_ID = "id";
    private static final String COLUMN_HP = "hp";
    private static final String COLUMN_ATK = "atk";
    private static final String COLUMN_GOLD = "gold";

    public DatabaseSQLite(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "DatabaseHelper.onCreate ...");

        String script1 = "CREATE TABLE " + TABLE_JOUEUR + "("
                + COLUMN_JOUEUR_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_HP + " INTEGER,"
                + COLUMN_ATK + " INTEGER,"
                + COLUMN_GOLD + ")";
        db.execSQL(script1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"DatabaseHelper.onUpgrade ...");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOUEUR);
        onCreate(db);
    }
}
