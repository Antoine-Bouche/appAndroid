package com.example.gachagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gachagame.Adapter.MonsterAdapter;
import com.example.gachagame.Database.DatabaseSQLite;
import com.example.gachagame.Database.MonsterDatabaseHelper;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

        MonsterDatabaseHelper monsterDatabaseHelper = new MonsterDatabaseHelper(this);

        monsterList = new MonsterList(monsterDatabaseHelper.getAllMonsters());

        encyclopedia.setAdapter(new MonsterAdapter(this,monsterList.getMonsterList()));
        encyclopedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int monsterId = monsterList.getMonsterList().get(position).getId();


                Intent intent = new Intent(EncyclopediaActivity.this, MonsterDetailsActivity.class);
                intent.putExtra("monsterId", monsterId);
                Toast.makeText(EncyclopediaActivity.this, "ID : " + monsterId , Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
