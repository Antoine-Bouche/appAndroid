package com.example.gachagame.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.gachagame.Models.Monster;
import com.example.gachagame.R;

import java.util.ArrayList;
import java.util.List;

public class MonsterDatabase extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ClickerGameMonster";
    private static final String TABLE_MONSTER = "Monster";
    private static final String COLUMN_MONSTER_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_HP = "hp";
    private static final String COLUMN_ATK = "atk";
    private static final String COLUMN_GOLD = "gold";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_ID = "imageId";

    public MonsterDatabase(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "DatabaseHelper.onCreate ...");

        String script = "CREATE TABLE " + TABLE_MONSTER + "("
                + COLUMN_MONSTER_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_HP + " INTEGER,"
                + COLUMN_ATK + " INTEGER,"
                + COLUMN_GOLD + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE_ID + " INTEGER"+")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"DatabaseHelper.onUpgrade ...");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MONSTER);
        onCreate(db);
    }

    public void createDefaultMonsterIfNeed() {
        int count = this.getMonsterCount();
        if(count==0) {

            Monster m1 = new Monster(1,"Slime",5,1,1,"A slime is a type of amorphous creature or substance that is characterized by its gelatinous and often translucent appearance. Slimes can come in various shapes and sizes, but they typically lack any discernible body structure or organs. Instead, they are composed of a thick, viscous fluid that enables them to move and even shape-shift to some extent.",R.drawable.monster1);
            addMonster(m1);
            Monster m2  = new Monster(2,"Lizard",20,5,5,"A giant lizard is a type of reptile that is much larger than most other species of lizards. They can vary in size, with some species growing to be several meters long and weighing hundreds of kilograms. Giant lizards are often found in tropical or subtropical regions, where they inhabit a variety of habitats such as forests, grasslands, and deserts.",R.drawable.monster2);
            addMonster(m2);
            Monster m3 = new Monster(3,"Caterpillar",15,8,8,"A caterpillar is the larval stage of a butterfly or moth, which is characterized by its long, cylindrical body and numerous legs. Caterpillars are often brightly colored or patterned, with markings that help to camouflage them from predators or signal their toxicity.",R.drawable.monster3);
            addMonster(m3);
            Monster m4 = new Monster(4,"Kobolt",30,10,12,"A kobold is a type of mythical creature or spirit that is typically depicted as a small, impish being that dwells in mines or other underground places. In Germanic folklore, kobolds were believed to be mischievous household spirits that would help with chores and tasks around the home, but could also be unpredictable and capricious.",R.drawable.monster4);
            addMonster(m4);
            Monster m5 = new Monster(5,"Green Dragon",100,15,25,"A green dragon is a mythical creature typically depicted as a large, scaly reptilian with leathery wings, sharp claws, and a long tail. As the name suggests, their scales are usually green in color, and they are often associated with forests, meadows, and other natural places.",R.drawable.monster5);
            addMonster(m5);
            Monster m6 = new Monster(6,"Golem",550,10,50," A golem is a creature from Jewish folklore that is said to be created from inanimate matter, such as clay or mud. In the stories, golems are brought to life through the use of mystical incantations or other magical means, and are often used as protectors or servants by their creators.",R.drawable.monster6);
            addMonster(m6);
            Monster m7 = new Monster(7,"Devil Plant",100,25,30," There are various fictional plants in mythology, literature, and popular culture that are referred to as \"devil plants.\" These plants are often depicted as dangerous or malevolent, with properties that can harm or even kill those who come into contact with them.",R.drawable.monster7);
            addMonster(m7);
            Monster m8 = new Monster(8,"Jiangshi",140,30,40,"Jiangshi (also known as \"hopping vampires\") is a type of undead creature from Chinese folklore. They are typically depicted as corpses that have been reanimated through supernatural means, such as being possessed by a vengeful spirit or a Taoist priest's curse.",R.drawable.monster8);
            addMonster(m8);
            Monster m9 = new Monster(9,"Assault Werewolf",180,45,50,"\"Assault Werewolf\" is not a specific creature from mythology or folklore, but rather a term that has been used in popular culture, particularly in fantasy and horror fiction, to describe a werewolf that is particularly aggressive or violent in nature.",R.drawable.monster9);
            addMonster(m9);
            Monster m10 = new Monster(10,"Skull knight",200,40,60,"Skull Knight is a fictional character from the Japanese manga and anime series \"Berserk,\" created by Kentaro Miura. He is a mysterious and powerful figure who wears a suit of armor that resembles a giant, skeletal knight.",R.drawable.monster10);
            addMonster(m10);
            Monster m11 = new Monster(11,"Badass Orc",250,60,70,"Orcs are typically portrayed as muscular and aggressive humanoids with a fierce and intimidating appearance. A badass orc is a character that embodies these traits and more, often being portrayed as a skilled warrior or hunter, with a fearless and brutal demeanor.",R.drawable.monster11);
            addMonster(m11);
            Monster m12 = new Monster(12,"Demon",270,40,65,"A demon is a supernatural being often depicted in mythology, folklore, and religious texts. In many cultures, demons are believed to be malevolent spirits or entities that can possess, harm or even kill humans.",R.drawable.monster12);
            addMonster(m12);
            Monster m13 = new Monster(13,"Salamander",300,70,100,"A salamander is a type of amphibian that is found in many parts of the world, including North America, Europe, and Asia. Salamanders are characterized by their slender bodies, long tails, and four short legs. They are typically small, with most species ranging in size from 10 to 20 centimeters in length, although some can grow up to a meter long.",R.drawable.monster13);
            addMonster(m13);
            Monster m14 = new Monster(14,"Ice Gorilla",320,75,120,"As the name suggests, an ice gorilla is a type of gorilla or ape-like creature that has ice or frost-based abilities. It is often depicted as having white or light blue fur, and is covered in ice crystals or frost. It may also have sharp claws, large fangs, and an imposing physical stature.",R.drawable.monster14);
            addMonster(m14);
            Monster m15 = new Monster(15,"Electrik Octopus",290,80,135,"As the name suggests, an electric octopus is an octopus-like creature that has the ability to generate and control electricity. It is often depicted as having a blue or silver coloration and crackling with electrical energy. It may also have long, tentacle-like arms and a large, bulbous head.",R.drawable.monster15);
            addMonster(m15);
            Monster m16 = new Monster(16,"Giant Mummy",350,40,135,"There is no scientific evidence to support the existence of giant mummies. However, in some myths and legends, there are stories of mummies that are larger than average human size. These legends often describe them as being the remains of ancient giants or other legendary creatures.",R.drawable.monster16);
            addMonster(m16);
            Monster m17 = new Monster(17,"Undead Ultimade Knight",500,100,200,"As the name suggests, an Undead Ultimate Knight is a powerful undead warrior, typically depicted as a skeletal knight or a heavily armored undead warrior. It is often portrayed as a formidable enemy, possessing great strength, skill, and supernatural abilities.",R.drawable.monster17);
            addMonster(m17);
            Monster m18 = new Monster(18,"Undead King Dwarf",700,120,350,"As the name suggests, an Undead King Dwarf is a dwarf character who has been transformed into an undead creature, typically through magical means. It is often portrayed as a powerful and imposing figure, possessing great strength, dark magic, and supernatural abilities.",R.drawable.monster18);
            addMonster(m18);
            Monster m19 = new Monster(19,"ZT X-49 Dark Knight",750,220,500,"As the name suggests, the ZT X-49 Dark Knight would likely be a dark, menacing and powerful mechanical monster, perhaps designed for war or destruction. It could be depicted as a humanoid machine, towering over other creatures with its size and strength, or perhaps as a four-legged beast or a massive, armored tank-like machine.",R.drawable.monster19);
            addMonster(m19);
            Monster m20 = new Monster(20,"Origin Of Chaos",900,600,900,"Origin of Chaos would likely be a creature that embodies chaos and destruction. It could be depicted as a massive, multi-headed beast, perhaps with writhing tentacles, and its body could be composed of writhing, wriggling tendrils or other unstable, constantly changing materials.",R.drawable.monster20);
            addMonster(m20);
        }
    }

    public void addMonster(Monster monster) {
        Log.i(TAG,"DatabaseHelper.addMonster ...");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME,monster.getName());
        values.put(COLUMN_HP,monster.getHp());
        values.put(COLUMN_ATK,monster.getAtk());
        values.put(COLUMN_GOLD,monster.getGold());
        values.put(COLUMN_DESCRIPTION,monster.getDescription());
        values.put(COLUMN_IMAGE_ID,monster.getImageResourceId());

        db.insert(TABLE_MONSTER,null,values);

        db.close();
    }

    public Monster getMonster(int id) {
        Log.i(TAG, "MonsterDatabaseHelper.getMonster ...");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MONSTER, new String[]{COLUMN_MONSTER_ID, COLUMN_NAME, COLUMN_HP, COLUMN_ATK, COLUMN_GOLD, COLUMN_DESCRIPTION, COLUMN_IMAGE_ID}, COLUMN_MONSTER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor!=null)
            cursor.moveToFirst();

        Monster monster = new Monster(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                cursor.getString(5),
                Integer.parseInt(cursor.getString(6)));

        return monster;
    }

    public List<Monster> getAllMonsters() {
        Log.i(TAG,"DatabaseHelper.getAllMonsters ...");

        List<Monster> monsterList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+ TABLE_MONSTER;

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