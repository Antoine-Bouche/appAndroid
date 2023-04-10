package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gachagame.Models.Joueur;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private LinearLayout gamePanel;
    private TextView nom_monstre;
    private ProgressBar monstre_healthbar;
    private ProgressBar hero_healthbar;
    private MonsterList monsterList;
    private int indexMonstreCourant;

    private Handler handler;
    private Runnable attaquerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        monsterList = new MonsterList(initMonster());
        Monster m = monsterList.getCurrentMonstre();

        gamePanel = findViewById(R.id.game_panel);

        //Initialisation vue coté monstre
        nom_monstre = findViewById(R.id.nom_monstre);
        monstre_healthbar = findViewById(R.id.monstre_healthbar);

        hero_healthbar = findViewById(R.id.hero_healthbar);

        nom_monstre.setText(m.getName());
        monstre_healthbar.setMax(m.getHp());
        monstre_healthbar.setProgress(m.getHp());

        Joueur j = new Joueur("Ma bite",10000,10,1);

        hero_healthbar.setMax(j.getHp());
        hero_healthbar.setProgress(j.getHp());

        gamePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Monster monstreCourant = monsterList.getCurrentMonstre();
                int newPV = monstreCourant.getHp() - 10;
                monstreCourant.setHp(newPV);
                monstre_healthbar.setProgress(newPV);

                if (newPV <= 0) {
                    // Si le monstre courant est mort, passer au monstre suivant ou terminer le jeu
                    if (monsterList.getCurrentIndex() < monsterList.getMonsterList().size() - 1) {
                        monsterList.setCurrentIndex(monsterList.getCurrentIndex()+1);
                        Monster nouveauMonstreCourant = monsterList.getCurrentMonstre();
                        nom_monstre.setText(nouveauMonstreCourant.getName());
                        monstre_healthbar.setMax(nouveauMonstreCourant.getHp());
                        monstre_healthbar.setProgress(nouveauMonstreCourant.getHp());
                    } else {
                        Toast.makeText(GameActivity.this, "Fin du jeu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        handler = new Handler();
        attaquerRunnable = new Runnable() {
            @Override
            public void run() {
                Monster monstreCourant = monsterList.getCurrentMonstre();

                int newPV = j.getHp() - monstreCourant.getAtk();
                j.setHp(newPV);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hero_healthbar.setProgress(newPV);
                    }
                });
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(attaquerRunnable, 5000);

    }

    private List<Monster> initMonster() {

        List<Monster> list = new ArrayList<>();
        list.add(new Monster(100,10,1,"Gobelin"));
        list.add(new Monster(1,1,1,"Axel"));
        list.add(new Monster(10,2,1,"Mananta"));
        list.add(new Monster(1000,100,1,"je suis raciste"));

        return list;
    }
}