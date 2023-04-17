package com.example.gachagame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gachagame.Database.DatabaseSQLite;
import com.example.gachagame.Database.MonsterDatabaseHelper;
import com.example.gachagame.Models.Joueur;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout gamePanel;
    private TextView nom_monstre;
    private ProgressBar monstre_healthbar;
    private ProgressBar hero_healthbar;
    private ImageView heroImage;
    private ImageView image_monstre;
    private TextView gold;
    private TextView kill;
    private AnimationDrawable animation;

    private MonsterList monsterList;

    private Button changeBackground;
    private Button stat;

    private Handler handler;
    private Runnable attaquerRunnable;
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManagerCompat;
    private int monsterKill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.createNotificationChannels();
        this.notificationManagerCompat = NotificationManagerCompat.from(this);

        sendOnChannel1();

        monsterKill = 0;

        DatabaseSQLite db = new DatabaseSQLite(this);

        MonsterDatabaseHelper monsterDatabaseHelper = new MonsterDatabaseHelper(this);

        monsterDatabaseHelper.createDefaultMonsterIfNeed();

        monsterList = new MonsterList(monsterDatabaseHelper.getAllMonsters());
        Monster m = monsterList.getCurrentMonstre();

        initView();

        stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,UpgradeActivity.class);
                startActivity(intent);
            }
        });

        changeBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

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

                if (newPV <= 0) {
                    int updateGold = j.getGold() + monstreCourant.getGold();
                    db.updateJoueurGold(1, updateGold);
                    j.setGold(updateGold);
                    gold.setText(j.getGold() + "");

                    monsterKill++;
                    kill.setText(""+monsterKill);

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

    private void initView() {
        gamePanel = findViewById(R.id.game_panel);
        nom_monstre = findViewById(R.id.nom_monstre);
        monstre_healthbar = findViewById(R.id.monstre_healthbar);
        image_monstre = findViewById(R.id.image_monstre);
        gold = findViewById(R.id.gold);
        hero_healthbar = findViewById(R.id.hero_healthbar);
        heroImage = findViewById(R.id.hero_image);
        heroImage.setBackgroundResource(R.drawable.knight_animation);
        animation = (AnimationDrawable) heroImage.getBackground();
        changeBackground = findViewById(R.id.changeBackground);
        stat = findViewById(R.id.stat);
        kill = findViewById(R.id.kill);
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
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(attaquerRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(attaquerRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Drawable drawable = Drawable.createFromStream(getContentResolver().openInputStream(uri), uri.toString());
                gamePanel.setBackground(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void gameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setTitle("Game Over");

        builder.setMessage("Oh no, you have been defeated by the demon lord!");

        builder.setCancelable(false);

        builder.setPositiveButton("Close", (DialogInterface.OnClickListener) (dialog, which) -> {
            handler.removeCallbacks(attaquerRunnable);
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void knightAttackAnimation() {
        animation = (AnimationDrawable) getResources().getDrawable(R.drawable.attack_animation, null);
        animation.setOneShot(true);
        heroImage.setBackground(animation);
        animation.start();
        animation.setOneShot(true);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID, "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    private void sendOnChannel1() {

        String title = "Welcome";
        String message = "Just tap the monster and kill them";

        Notification notification = new NotificationCompat.Builder(this,
                GameActivity.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        int notificationId = 1;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.notificationManagerCompat.notify(notificationId, notification);

    }

}