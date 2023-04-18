package com.example.gachagame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gachagame.Database.PlayerDatabase;
import com.example.gachagame.Models.Joueur;

public class AmeliorationActivity extends AppCompatActivity {
    private Button uppgradeButtonHp;
    private TextView costTextHp;
    private Button uppgradeButtonAtk;
    private TextView costTextAtk;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amelioration);
        uppgradeButtonHp= findViewById(R.id.upgrade_button_hp);
        costTextAtk = findViewById(R.id.cost_hp);
        uppgradeButtonAtk = findViewById(R.id.upgrade_button_atk);
        costTextAtk = findViewById(R.id.cost_atk);


        PlayerDatabase db = new PlayerDatabase(this);
        db.createDefaultJoueurIfNeed();
        Joueur j = db.getJoueur(1);


        uppgradeButtonHp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (j.getGold() > j.getHp()-j.getHpDuDepart()) {
                    j.setHp(j.getHp() + 10);
                    j.setGold(j.getGold() - j.getHp() - j.getHpDuDepart());
                    db.updateJoueurStats(j.getId(), j.getHp(), j.getAtk(), j.getGold());
                    Toast.makeText(AmeliorationActivity.this, "Hp bien augmenté à : "+ j.getHp(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AmeliorationActivity.this, "Vous n'avez pas assez d'argent", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uppgradeButtonAtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j.getGold() > (j.getAtk() - j.getAtkDuDepart())*10) {
                    j.setHp(j.getAtk() + 1);
                    j.setGold(j.getGold() - (j.getAtk() - j.getAtkDuDepart())*10);
                    db.updateJoueurStats(j.getId(), j.getHp(), j.getAtk(), j.getGold());
                    Toast.makeText(AmeliorationActivity.this, "Atk bien augmenté à : "+ j.getAtk(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AmeliorationActivity.this, "Vous n'avez pas assez d'argent", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
