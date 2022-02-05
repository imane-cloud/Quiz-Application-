package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    //Les paramètres de ResultActivity
    private TextView ScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ScoreText=(TextView)findViewById(R.id.textViewScore);

        //récupérer le score de l'activité précedente
        Bundle extras=getIntent().getExtras();
        String score=extras.get("score").toString();

        //Affichage du score
        ScoreText.setText("Your score is : " + score);

    }
}
