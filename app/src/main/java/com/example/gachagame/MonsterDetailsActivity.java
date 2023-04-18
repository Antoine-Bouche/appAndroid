package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Button;
import android.widget.Toast;

import com.example.gachagame.Database.MonsterDatabase;
import com.example.gachagame.Models.Monster;

import java.util.Locale;


public class MonsterDetailsActivity extends AppCompatActivity {

    private TextView name;
    private TextView hp;
    private TextView atk;
    private TextView gold;
    private Button read;
    private TextView description;
    TextToSpeech textToSpeech;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_details);

        MonsterDatabase db = new MonsterDatabase(this);

        name = findViewById(R.id.nomD);
        hp = findViewById(R.id.hpD);
        atk = findViewById(R.id.atkD);
        gold = findViewById(R.id.goldD);
        description = findViewById(R.id.tts_lire);
        read = findViewById(R.id.lire);
        image = findViewById(R.id.imageD);

        Intent intent = getIntent();

        if(intent.hasExtra("monsterId")) {
            int id = intent.getIntExtra("monsterId", -1);
            Monster m = db.getMonster(id);
            name.setText(m.getName());
            hp.setText(m.getHp()+"");
            atk.setText(m.getAtk()+"");
            gold.setText(m.getGold()+"");
            description.setText(m.getDescription());
            image.setBackgroundResource(m.getImageResourceId());

        }else {
            Toast.makeText(this, "Id does not exist : ERROR", Toast.LENGTH_SHORT).show();
            finish();
        }

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });



        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texte = description.getText().toString();
                textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
}
