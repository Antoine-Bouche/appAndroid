package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gachagame.Models.Monster;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private LinearLayout gamePanel;
    private TextView nom_monstre;
    private ProgressBar monstre_healthbar;
    private ProgressBar hero_healthbar;
    private List<Monster> listeMonstres;
    private int indexMonstreCourant;

    private Handler handler;
    private Runnable attaquerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}