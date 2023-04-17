package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gachagame.Database.DatabaseSQLite;
import com.example.gachagame.Models.Joueur;

public class UpgradeActivity extends AppCompatActivity {

    private Button hp;
    private Button atk;

    private ProgressBar hp_bar;
    private ProgressBar atk_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        DatabaseSQLite db = new DatabaseSQLite(this);

        Joueur j = db.getJoueur(1);

        hp = findViewById(R.id.hpIncrease);
        atk = findViewById(R.id.atkIncrease);

        hp_bar = findViewById(R.id.player_hp_bar);
        atk_bar = findViewById(R.id.player_atk_bar);

        hp_bar.setProgress(j.getHp());
        atk_bar.setProgress(j.getAtk());

        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 50;
                int gold = j.getGold();

                if(gold>=cost) {
                    int newMoney = gold-cost;
                    db.updateJoueurGold(1,newMoney);
                    int newHP = j.getHp() + 10;
                    db.updateJoueurHp(1,newHP);
                    Toast.makeText(UpgradeActivity.this, "HP INCREASE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpgradeActivity.this, "Money lower than cost", Toast.LENGTH_SHORT).show();
                }
            }
        });

        atk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 50;
                int gold = j.getGold();

                if(gold>=cost) {
                    int newMoney = gold-cost;
                    db.updateJoueurGold(1,newMoney);
                    int newATK = j.getAtk() + 10;
                    db.updateJoueurAtk(1,newATK);
                    Toast.makeText(UpgradeActivity.this, "ATK INCREASE", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpgradeActivity.this, "Money lower than cost", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}