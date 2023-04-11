package com.example.gachagame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gachagame.Database.DatabaseSQLite;
import com.example.gachagame.Database.MonsterDatabaseHelper;
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
    private ImageView heroImage;
    private ImageView image_monstre;
    private TextView gold;
    private AnimationDrawable animation;

    private MonsterList monsterList;

    private Handler handler;
    private Runnable attaquerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DatabaseSQLite db = new DatabaseSQLite(this);

        List<Monster> ml = new ArrayList<>();

        MonsterDatabaseHelper monsterDatabaseHelper = new MonsterDatabaseHelper(this);

        monsterList = new MonsterList(monsterDatabaseHelper.getAllMonsters());
        Monster m = monsterList.getCurrentMonstre();

        gamePanel = findViewById(R.id.game_panel);

        nom_monstre = findViewById(R.id.nom_monstre);
        monstre_healthbar = findViewById(R.id.monstre_healthbar);
        image_monstre = findViewById(R.id.image_monstre);
        gold = findViewById(R.id.gold);
        hero_healthbar = findViewById(R.id.hero_healthbar);
        heroImage = findViewById(R.id.hero_image);
        heroImage.setBackgroundResource(R.drawable.knight_animation);
        animation = (AnimationDrawable) heroImage.getBackground();

        nom_monstre.setText(m.getName());
        monstre_healthbar.setMax(m.getHp());
        monstre_healthbar.setProgress(m.getHp());
        image_monstre.setImageResource(m.getImageResourceId());


        db.createDefaultJoueurIfNeed();
        Joueur j = db.getJoueur(1);

        hero_healthbar.setMax(j.getHp());
        hero_healthbar.setProgress(j.getHp());
        gold.setText(j.getGold()+"");

        gamePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                knightAttackAnimation();

                Monster monstreCourant = monsterList.getCurrentMonstre();
                Joueur j = db.getJoueur(1);
                int newPV = monstreCourant.getHp() - j.getAtk();
                monstreCourant.setHp(newPV);
                monstre_healthbar.setProgress(newPV);

                if(j.getGold()%500==0) {
                    sendNotification();
                }

                if (newPV <= 0) {
                    int updateGold = j.getGold()+monstreCourant.getGold();
                    db.updateJoueurGold(1,updateGold);
                    gold.setText(j.getGold()+"");
                    if (monsterList.getCurrentIndex() < monsterList.getNumberOfMonstres() - 1) {
                        monsterList.nextMonster();
                        Monster newCurrentMonster = monsterList.getCurrentMonstre();
                        nom_monstre.setText(newCurrentMonster.getName());
                        monstre_healthbar.setMax(newCurrentMonster.getHp());
                        monstre_healthbar.setProgress(newCurrentMonster.getHp());
                        image_monstre.setImageResource(newCurrentMonster.getImageResourceId());
                    } else {
                        handler.removeCallbacks(attaquerRunnable);
                        endGame();
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
                if (newPV <= 0) {
                    handler.removeCallbacks(attaquerRunnable);
                    gameOver();
                } else {
                    handler.postDelayed(this, 3000);
                }
            }
        };
        handler.postDelayed(attaquerRunnable, 5000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animation.start();
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setTitle("Game End");

        builder.setMessage("Congratulations, you have defeated all demon lord monsters!");

        builder.setCancelable(false);

        builder.setPositiveButton("Close", (DialogInterface.OnClickListener) (dialog, which) -> {
            Intent intent = new Intent(GameActivity.this,MainActivity.class);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void gameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setTitle("Game Over");

        builder.setMessage("Oh no, you have been defeated by the demon lord!");

        builder.setCancelable(false);

        builder.setPositiveButton("Close", (DialogInterface.OnClickListener) (dialog, which) -> {
            handler.removeCallbacks(attaquerRunnable);
            Intent intent = new Intent(GameActivity.this,MainActivity.class);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void  knightAttackAnimation() {
        animation = (AnimationDrawable) getResources().getDrawable(R.drawable.attack_animation, null);
        animation.setOneShot(true);
        heroImage.setBackground(animation);
        animation.start();
        animation.setOneShot(true);
    }

    public void sendNotification() {
        // Créer un Intent pour l'action lorsque l'utilisateur clique sur la notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Créer la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Money salutes you kid")
                .setContentText("Tuez plus de monstres et devenez plus riche comme un vrai guerrier")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Envoyer la notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build()); // utilisez un ID unique pour identifier la notification
    }

}