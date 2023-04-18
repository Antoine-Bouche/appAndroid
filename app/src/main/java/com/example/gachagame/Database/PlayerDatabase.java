package com.example.gachagame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gachagame.Models.Joueur;

public class PlayerDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ClickerGame";

    private static final String TABLE_JOUEUR = "Joueur";
    private static final String COLUMN_JOUEUR_ID = "id";
    private static final String COLUMN_HP = "hp";
    private static final String COLUMN_ATK = "atk";
    private static final String COLUMN_GOLD = "gold";

    public PlayerDatabase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "DatabaseHelper.onCreate ...");

        String script1 = "CREATE TABLE " + TABLE_JOUEUR + "("
                + COLUMN_JOUEUR_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_HP + " INTEGER,"
                + COLUMN_ATK + " INTEGER,"
                + COLUMN_GOLD + " INTEGER"+")";
        db.execSQL(script1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"DatabaseHelper.onUpgrade ...");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOUEUR);
        onCreate(db);
    }

    public void createDefaultJoueurIfNeed() {
        int count = this.getJoueurCount();
        if(count==0) {
            Joueur joueur = new Joueur(1,999,999,60000);
            this.addJoueur(joueur);
        }
    }

    public void addJoueur(Joueur joueur) {
        Log.i(TAG,"DatabaseHelper.addJoueur ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_HP,joueur.getHp());
        values.put(COLUMN_ATK,joueur.getAtk());
        values.put(COLUMN_GOLD,joueur.getGold());

        db.insert(TABLE_JOUEUR,null,values);

        db.close();
    }

    public Joueur getJoueur(int id) {
        Log.i(TAG,"DatabaseHelper.getJoueur ..."+id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOUEUR,new String[] {COLUMN_JOUEUR_ID
                        ,COLUMN_HP,COLUMN_ATK,COLUMN_GOLD},COLUMN_JOUEUR_ID+"=?",
                new String[] { String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        Joueur joueur = new Joueur(Integer.parseInt(cursor.getString(0))
                ,Integer.parseInt(cursor.getString(1))
                ,Integer.parseInt(cursor.getString(2))
                ,Integer.parseInt(cursor.getString(3)));

        return joueur;
    }

    public void updateJoueurGold(int id,int gold) {
        Log.i(TAG, "DatabaseHelper.updateJoueurStats ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_GOLD, gold);

        db.update(TABLE_JOUEUR, values, COLUMN_JOUEUR_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public void updateJoueurHp(int id,int hp) {
        Log.i(TAG, "DatabaseHelper.updateJoueurStats ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_HP, hp);

        db.update(TABLE_JOUEUR, values, COLUMN_JOUEUR_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }


    public void updateJoueurAtk(int id,int atk) {
        Log.i(TAG, "DatabaseHelper.updateJoueurStats ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ATK, atk);

        db.update(TABLE_JOUEUR, values, COLUMN_JOUEUR_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }


    public void updateJoueurStats(int id, int hp, int atk, int gold) {
        Log.i(TAG, "DatabaseHelper.updateJoueurStats ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HP, hp);
        values.put(COLUMN_ATK, atk);
        values.put(COLUMN_GOLD, gold);

        db.update(TABLE_JOUEUR, values, COLUMN_JOUEUR_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
    }

    public int getJoueurCount() {
        Log.i(TAG, "DatabaseHelper.getJoueurCount ...");

        String countQuery = "SELECT * FROM " + TABLE_JOUEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
