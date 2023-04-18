package com.example.gachagame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
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

import com.example.gachagame.Database.PlayerDatabase;
import com.example.gachagame.Database.MonsterDatabase;
import com.example.gachagame.Models.Joueur;
import com.example.gachagame.Models.Monster;
import com.example.gachagame.Models.MonsterList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_PERMISSION = 100;
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
    private ImageView parameter;
    private Dialog dialog;
    private Handler handler;
    private Runnable attaquerRunnable;
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManagerCompat;
    private int monsterKill = 0;
    private boolean isDialogOpen = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mediaPlayer = MediaPlayer.create(this, R.raw.battle_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        this.createNotificationChannels();
        this.notificationManagerCompat = NotificationManagerCompat.from(this);

        sendOnChannel1();

        PlayerDatabase db = new PlayerDatabase(this);

        MonsterDatabase monsterDatabase = new MonsterDatabase(this);

        monsterDatabase.createDefaultMonsterIfNeed();

        List<Monster> monstreDb = monsterDatabase.getAllMonsters();

        List<Monster> randomMonsters = new ArrayList<>();

        Random random = new Random();

        while (randomMonsters.size() < 1000) {
            int randomIndex = random.nextInt(monstreDb.size());
            Monster randomMonster = monstreDb.get(randomIndex);
            randomMonsters.add(randomMonster);
        }

        monsterList = new MonsterList(randomMonsters);
        Monster m = monsterList.getCurrentMonstre();

        initView();

        parameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                dialog.setCancelable(false);
                dialog.show();
                isDialogOpen = true;
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                isDialogOpen = false;
                handler.postDelayed(attaquerRunnable, 3000);
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
                if (!isDialogOpen) { // Condition si le dialog est fermer
                    Monster monstreCourant = monsterList.getCurrentMonstre();

                    int newPV = j.getHp() - monstreCourant.getAtk();
                    j.setHp(newPV);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hero_healthbar.setProgress(newPV);
                        }
                    });

                    if (newPV <= 0) { //si le personnage meurs alors le runnable s'exécute plus
                        handler.removeCallbacks(attaquerRunnable);
                        gameOver();
                    } else {
                        handler.postDelayed(this, 3000);
                    }
                } else {
                    handler.removeCallbacks(attaquerRunnable); // Arrêtez l'exécution de Runnable
                }
            }
        };
        handler.postDelayed(attaquerRunnable, 5000);

    }

    //initialisation des différents vue
    private void initView() {

        //initialisation des vues avec le xml
        gamePanel = findViewById(R.id.game_panel);
        nom_monstre = findViewById(R.id.nom_monstre);
        monstre_healthbar = findViewById(R.id.monstre_healthbar);
        image_monstre = findViewById(R.id.image_monstre);
        gold = findViewById(R.id.gold);
        hero_healthbar = findViewById(R.id.hero_healthbar);
        heroImage = findViewById(R.id.hero_image);
        heroImage.setBackgroundResource(R.drawable.knight_animation);
        parameter = findViewById(R.id.parameter);
        kill = findViewById(R.id.kill);

        //ajout de l'animation pour mon chevalier
        animation = (AnimationDrawable) heroImage.getBackground();

        //initialisation du dialog
        initDialog();
    }


    private void initDialog() {

        //création du dialog pour avoir le menu paramètre
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.parameter_pop_up);

        Button resume = dialog.findViewById(R.id.resume);

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(GameActivity.this, R.raw.battle_music);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                dialog.dismiss();
            }
        });

        Button upgrade = dialog.findViewById(R.id.upgrade);

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this,UpgradeActivity.class);
                startActivity(intent);
            }
        });

        Button backgroud = dialog.findViewById(R.id.background);

        backgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermissionAndOpenGallery();
            }
        });

        Button home = dialog.findViewById(R.id.home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }

    //animation du personnage
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animation.start();
    }

    //fin du jeu quand le héro game
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
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private void askPermissionAndOpenGallery() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int readPermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_PERMISSION);
                return;
            }
        }
        this.openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.openGallery();
            } else {
                Toast.makeText(this, "You can't access the gallery", Toast.LENGTH_SHORT).show();
            }
        }
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

    //fin du jeu quand le héro perd
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

    //animation d'attaque du chevalier
    private void knightAttackAnimation() {
        animation = (AnimationDrawable) getResources().getDrawable(R.drawable.attack_animation, null);
        animation.setOneShot(true);
        heroImage.setBackground(animation);
        animation.start();
        animation.setOneShot(true);
    }

    //notificaiton
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

    //envoie de la notification
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