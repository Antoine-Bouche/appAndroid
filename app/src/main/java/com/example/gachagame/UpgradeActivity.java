package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gachagame.Database.PlayerDatabase;
import com.example.gachagame.Models.Joueur;

public class UpgradeActivity extends AppCompatActivity {

    private Button hp;
    private Button atk;

    private ProgressBar hp_bar;
    private ProgressBar atk_bar;

    private TextView hp_cost_text;
    private TextView atk_cost_text;
    private TextView goldTextView;

    private int hp_cost = 100;
    private int atk_cost = 100;

    private int gold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        PlayerDatabase db = new PlayerDatabase(this);

        Joueur player = db.getJoueur(1);

        gold = player.getGold();

        hp = findViewById(R.id.hpIncrease);
        atk = findViewById(R.id.atkIncrease);

        goldTextView = findViewById(R.id.number_gold);

        goldTextView.setText("Gold : "+gold);

        hp_bar = findViewById(R.id.player_hp_bar);
        atk_bar = findViewById(R.id.player_atk_bar);

        hp_cost_text = findViewById(R.id.hp_cost_text);
        atk_cost_text = findViewById(R.id.atk_cost_text);

        updateCostText();

        hp_bar.setProgress(player.getHp());
        atk_bar.setProgress(player.getAtk());

        hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gold = player.getGold();

                if(player.getHp()>=999) {
                    Toast.makeText(UpgradeActivity.this, "MAX HP", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(gold>=hp_cost) {
                    int newMoney = gold-hp_cost;
                    player.setGold(newMoney);
                    goldTextView.setText("Gold : "+player.getGold());
                    db.updateJoueurGold(1,newMoney);
                    int newHP = player.getHp() + 10;
                    db.updateJoueurHp(1,newHP);
                    player.setHp(newHP);
                    Toast.makeText(UpgradeActivity.this, "HP INCREASE", Toast.LENGTH_SHORT).show();
                    updateCostText();
                    hp_bar.setProgress(newHP);
                } else {
                    Toast.makeText(UpgradeActivity.this, "Money lower than cost", Toast.LENGTH_SHORT).show();
                }
            }
        });

        atk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gold = player.getGold();

                if(player.getAtk()>=999) {
                    Toast.makeText(UpgradeActivity.this, "MAX ATK", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(gold>=atk_cost) {
                    int newMoney = gold-atk_cost;
                    player.setGold(newMoney);
                    goldTextView.setText("Gold : "+player.getGold());
                    db.updateJoueurGold(1,newMoney);
                    int newATK = player.getAtk() + 20;
                    db.updateJoueurAtk(1,newATK);
                    player.setAtk(newATK);
                    Toast.makeText(UpgradeActivity.this, "ATK INCREASE", Toast.LENGTH_SHORT).show();
                    updateCostText();
                    atk_bar.setProgress(newATK);
                } else {
                    Toast.makeText(UpgradeActivity.this, "Money lower than cost", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateCostText() {
        hp_cost_text.setText("Cost: " + hp_cost);
        atk_cost_text.setText("Cost: " + atk_cost);
    }
}
