package com.example.gachagame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gachagame.Models.Joueur;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.R;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSQLite extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ClickerGame";

    private static final String TABLE_JOUEUR = "Joueur";
    private static final String COLUMN_JOUEUR_ID = "id";
    private static final String COLUMN_HP = "hp";
    private static final String COLUMN_ATK = "atk";
    private static final String COLUMN_GOLD = "gold";

    private static final String TABLE_MONSTER = "Monstre";
    private static final String COLUMN_MONSTRE_ID = "id";
    private static final String COLUMN_NAME_MONSTER = "nom";
    private static final String COLUMN_HP_MONSTER = "hp";
    private static final String COLUMN_ATK_MONSTER = "atk";
    private static final String COLUMN_GOLD_MONSTER = "gold";
    private static final String COLUMN_DESCRIPTION_MONSTER = "description";
    private static final String COLUMN_IMAGE_MONSTER = "img";

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
                + COLUMN_GOLD + " INTEGER"+")";
        db.execSQL(script1);

        String script2 = "CREATE TABLE " + TABLE_MONSTER + "("
                + COLUMN_MONSTRE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_MONSTER + " TEXT,"
                + COLUMN_HP_MONSTER + " INTEGER,"
                + COLUMN_ATK_MONSTER + " INTEGER,"
                + COLUMN_GOLD_MONSTER + " INTEGER,"
                + COLUMN_DESCRIPTION_MONSTER + "TEXT,"
                + COLUMN_IMAGE_MONSTER + " INTEGER"+")";
        db.execSQL(script2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"DatabaseHelper.onUpgrade ...");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_JOUEUR);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MONSTER);
        onCreate(db);
    }

    public void createDefaultJoueurIfNeed() {
        int count = this.getJoueurCount();
        if(count==0) {
            Joueur joueur = new Joueur(1,50,5,0);
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

    public void createDefaultMonsterIfNeed() {
        int count = this.getMonsterCount();
        if(count==0) {
            Monster monster1 = new Monster(1,
                    "Demon",
                    50, 5, 3,
                    "Demon",
                    R.drawable.monstre1);
            this.addMonster(monster1);
        }
    }

    public void addMonster(Monster monster) {
        Log.i(TAG,"DatabaseHelper.addMonster ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_MONSTER,monster.getName());
        values.put(COLUMN_HP_MONSTER,monster.getHp());
        values.put(COLUMN_ATK_MONSTER,monster.getAtk());
        values.put(COLUMN_GOLD_MONSTER,monster.getGold());
        values.put(COLUMN_DESCRIPTION_MONSTER,monster.getDescription());
        values.put(COLUMN_IMAGE_MONSTER,monster.getImageResourceId());

        db.insert(TABLE_MONSTER,null,values);

        db.close();
    }

    public Monster getMonster(int id) {
        Log.i(TAG,"DatabaseHelper.getMonster ..."+id);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOUEUR,new String[] {COLUMN_MONSTRE_ID
                        ,COLUMN_NAME_MONSTER,COLUMN_HP_MONSTER,COLUMN_ATK_MONSTER,COLUMN_GOLD_MONSTER,COLUMN_DESCRIPTION_MONSTER,COLUMN_IMAGE_MONSTER},COLUMN_MONSTRE_ID+"=?",
                new String[] { String.valueOf(id)},null,null,null,null);

        if(cursor!=null)
            cursor.moveToFirst();

        Monster monster = new Monster(Integer.parseInt(cursor.getString(0))
                ,cursor.getString(1)
                ,Integer.parseInt(cursor.getString(2))
                ,Integer.parseInt(cursor.getString(3))
                ,Integer.parseInt(cursor.getString(4))
                ,cursor.getString(5)
                ,Integer.parseInt(cursor.getString(6)));

        return monster;
    }

    public List<Monster> getAllMonster() {
        Log.i(TAG,"DatabaseHelper.getAllMonster ...");

        List<Monster> monsterList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_MONSTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {

            do {
                Monster monster = new Monster();
                monster.setId(Integer.parseInt(cursor.getString(0)));
                monster.setName(cursor.getString(1));
                monster.setHp(Integer.parseInt(cursor.getString(2)));
                monster.setAtk(Integer.parseInt(cursor.getString(3)));
                monster.setGold(Integer.parseInt(cursor.getString(4)));
                monster.setDescription(cursor.getString(5));
                monster.setImageResourceId(Integer.parseInt(cursor.getString(6)));
                monsterList.add(monster);
            }while (cursor.moveToNext());
        }
        return monsterList;
    }

    public int getMonsterCount() {
        Log.i(TAG, "DatabaseHelper.getMonsterCount ...");

        String countQuery = "SELECT * FROM " + TABLE_MONSTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }
}
