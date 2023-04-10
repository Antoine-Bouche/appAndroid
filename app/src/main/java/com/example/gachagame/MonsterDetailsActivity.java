package com.example.gachagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MonsterDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_details);

        Intent intent = getIntent();

        // Vérifier si l'intention contient l'extra "monsterId"
        if (intent.hasExtra("monsterId")) {
            // Récupérer l'ID du monstre à partir de l'extra "monsterId"
            int monsterId = intent.getIntExtra("monsterId", -1);

            // Faire quelque chose avec l'ID du monstre, par exemple l'afficher dans un TextView
            TextView textView = findViewById(R.id.textViewMonsterId);
            textView.setText("Monster ID: " + monsterId);
        }
    }
}