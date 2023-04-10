package com.example.gachagame;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;


public class EncyclopediaActivity extends AppCompatActivity {
    private MonsterList monsterList;
    private LinearLayout encyclopedia;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        encyclopedia = findViewById(R.id.encyclopediaLayout);

        monsterList= new MonsterList();
        monsterList.getMonsterList().add(new Monster(1,"gobelin",50,5,2,R.drawable.test));

        for (int i =0; i<monsterList.getMonsterList().size();i++){
            ImageView imageView = new ImageView(EncyclopediaActivity.this);
            imageView.setImageResource(monsterList.getMonsterList().get(i).getImageResourceId());
            imageView.setId(monsterList.getMonsterList().get(i).getId());
            imageView.setLayoutParams(encyclopedia.getLayoutParams());
            imageView.setScaleY(1);
            imageView.setScaleX(1);
            encyclopedia.addView(imageView);
        }

    }
}
