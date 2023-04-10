package com.example.gachagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gachagame.Database.DatabaseSQLite;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;

import java.util.ArrayList;
import java.util.List;


public class EncyclopediaActivity extends AppCompatActivity {
    private MonsterList monsterList;
    private ListView encyclopedia;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        encyclopedia = findViewById(R.id.listView);


        List<Monster> ml = new ArrayList<>();
        ml.add(new Monster(1,"Djin",100,5,10,"T",R.drawable.monstre3));
        monsterList = new MonsterList(ml);

        List<String> monsterNames = new ArrayList<>();
        for (Monster monster : monsterList.getMonsterList()) {
            monsterNames.add(monster.getName());
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monsterNames);
        encyclopedia.setAdapter(arrayAdapter);

        encyclopedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int monsterId = monsterList.getMonsterList().get(position).getId();


                Intent intent = new Intent(EncyclopediaActivity.this, MonsterDetailsActivity.class);
                intent.putExtra("monsterId", monsterId);
                startActivity(intent);
            }
        });
    }
}
