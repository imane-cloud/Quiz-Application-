package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProfActivity extends AppCompatActivity {

    private EditText EditUsername;
    private TextView ViewDate,ViewScore;
    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        EditUsername = (EditText) findViewById(R.id.editName);
        ViewDate = (TextView) findViewById(R.id.textViewDate);
        ViewScore = (TextView) findViewById(R.id.textViewScore1);
        QuizService quizservice=new QuizService(getApplicationContext());
        btnEnter=(Button)findViewById(R.id.buttonEnter);


        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Récupérer l'username insérée
                String username = EditUsername.getText().toString();
                //Verifier si l'username inséré existe dans database
                if (quizservice.checkusername(username)==false){
                    Toast.makeText(ProfActivity.this,"This username does not exist",Toast.LENGTH_SHORT).show();}
                //Récupérer la date du test effectué par l'username
                else {
                    String Date="date :"+quizservice.getDate(username);
                    //Afficher la date dans l'écran
                    ViewDate.setText(Date);
                    //Récupérer le score du test effectué par l'username
                    String Score="score :"+quizservice.getscore(username);
                    //Aficher le score du test
                    ViewScore.setText(Score);

                    //Créer les variables TextView de la table
                    //Chaque test contient 5 questions, donc chaque colonnes contient 5 lignes (un ligne pour chaque question)
                    //Colonne question_ID
                    TextView ViewID1=(TextView) findViewById(R.id.ID1);
                    TextView ViewID2=(TextView) findViewById(R.id.ID2);
                    TextView ViewID3=(TextView) findViewById(R.id.ID3);
                    TextView ViewID4=(TextView) findViewById(R.id.ID4);
                    TextView ViewID5=(TextView) findViewById(R.id.ID5);
                    //colonnes Réponses corrects
                    TextView ViewRightAnswer1=(TextView) findViewById(R.id.RP1);
                    TextView ViewRightAnswer2=(TextView) findViewById(R.id.RP2);
                    TextView ViewRightAnswer3=(TextView) findViewById(R.id.RP3);
                    TextView ViewRightAnswer4=(TextView) findViewById(R.id.RP4);
                    TextView ViewRightAnswer5=(TextView) findViewById(R.id.RP5);
                    //colonnes Réponses Etudiants
                    TextView ViewStudentAnswer1=(TextView) findViewById(R.id.RE1);
                    TextView ViewStudentAnswer2=(TextView) findViewById(R.id.RE2);
                    TextView ViewStudentAnswer3=(TextView) findViewById(R.id.RE3);
                    TextView ViewStudentAnswer4=(TextView) findViewById(R.id.RE4);
                    TextView ViewStudentAnswer5=(TextView) findViewById(R.id.RE5);
                    //Récupérer de notre base de données les question_id du test effectué par l'username insérée et les afficher dans la table
                    ViewID1.setText(quizservice.getquestionsID(username).get(0));
                    ViewID2.setText(quizservice.getquestionsID(username).get(1));
                    ViewID3.setText(quizservice.getquestionsID(username).get(2));
                    ViewID4.setText(quizservice.getquestionsID(username).get(3));
                    ViewID5.setText(quizservice.getquestionsID(username).get(4));
                    //Récupérer de notre base de données les réponses correctes des questions et les afficher dans la table
                    ViewRightAnswer1.setText(quizservice.getRightAnswers(username).get(0));
                    ViewRightAnswer2.setText(quizservice.getRightAnswers(username).get(1));
                    ViewRightAnswer3.setText(quizservice.getRightAnswers(username).get(2));
                    ViewRightAnswer4.setText(quizservice.getRightAnswers(username).get(3));
                    ViewRightAnswer5.setText(quizservice.getRightAnswers(username).get(4));
                    //Récupérer de notre base de données les réponses de l'étudiant sur chaque question et les afficher dans la table
                    ViewStudentAnswer1.setText(quizservice.getStudentAnswers(username).get(4));
                    ViewStudentAnswer2.setText(quizservice.getStudentAnswers(username).get(3));
                    ViewStudentAnswer3.setText(quizservice.getStudentAnswers(username).get(2));
                    ViewStudentAnswer4.setText(quizservice.getStudentAnswers(username).get(1));
                    ViewStudentAnswer5.setText(quizservice.getStudentAnswers(username).get(0));}

                }
            }

        );
    }


}
