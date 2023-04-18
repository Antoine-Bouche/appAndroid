package com.example.gachagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gachagame.Adapter.MonsterAdapter;
import com.example.gachagame.Database.MonsterDatabase;
import com.example.gachagame.Models.MonsterList;


public class EncyclopediaActivity extends AppCompatActivity {
    private MonsterList monsterList;
    private ListView encyclopedia;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        encyclopedia = findViewById(R.id.listView);

        MonsterDatabase monsterDatabase = new MonsterDatabase(this);

        monsterList = new MonsterList(monsterDatabase.getAllMonsters());

        encyclopedia.setAdapter(new MonsterAdapter(this,monsterList.getMonsterList()));
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
