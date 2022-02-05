package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity {
    Button btnEasy;
    Button btnMedium;
    Button btnHard;
    String level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnHard = (Button) findViewById(R.id.btnHard);

        Intent quiz = new Intent(this, QuizActivity.class);
        Bundle extras1 = getIntent().getExtras();
        //récupérer la valeur de l'username de l'activité précédent et le faire passer vers l'activité de quiz
        String username = extras1.get("username").toString();
        quiz.putExtra("username", username);

        //La valeur de 'level' change selon le boutton choisi
        btnEasy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                level="easy";
                //passer la valeur de level vers l'activité de quiz
                quiz.putExtra("level",level);
                //démarrer l'activité de quiz
                startActivity(quiz);

            }
        }
    );
        btnMedium.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                level="medium";
                //passer la valeur de level vers l'activité de quiz
                quiz.putExtra("level",level);
                //démarrer l'activité de quiz
                startActivity(quiz);
            }
        }
        );

        btnHard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                level="hard";
                //passer la valeur de level vers l'activité de quiz
                quiz.putExtra("level",level);
                //démarrer l'activité de quiz
                startActivity(quiz);
            }
        }
        );
    }
}
