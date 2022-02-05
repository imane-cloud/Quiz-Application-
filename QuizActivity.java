package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    //Les paramètres de l'activité quiz
    private TextView QuestionNumber, Question;
    private RadioGroup RG;
    private RadioButton choice1,choice2,choice3;
    private Button BtnNext;
    String level;
    int count=5;//le nombre des questions du test
    Question Currentquestion;//ce variable change chaque on appuie sur 'Next'
    int score=0;
    int i=1;//numéro de la question
    List<Question> listeQuestion=new ArrayList<>();//on va remplir cette avec les randoms questions du test
    String username;
    test test;
    ArrayList<String> etudiantChoices=new ArrayList<>();//on va remplir cette liste avec les réponses de l'étudiant
    private ColorStateList textColorDefaultRb;
    boolean checkAnswer;//true si la correction de la question est affichée, false sinon


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //récupération des données entrées
        Question=(TextView) findViewById(R.id.textViewQuestion);
        QuestionNumber=(TextView) findViewById(R.id.textViewQuestionNumber);
        choice1=findViewById(R.id.radioButtonChoice1);
        choice2=findViewById(R.id.radioButtonChoice2);
        choice3=findViewById(R.id.radioButtonChoice3);
        RG=findViewById(R.id.radio_group);


        BtnNext=(Button)findViewById(R.id.buttonNext);

        textColorDefaultRb=choice1.getTextColors();

        //récupérer les données étudiants enregistrées dans les activités précédente
        Bundle extras=getIntent().getExtras();
        username=extras.get("username").toString();
        level=extras.get("level").toString();
        QuizService quizService=new QuizService(getApplicationContext());

        //récupérer de la base de données i question aléatoire ayant le level précis
        listeQuestion=quizService.RandomChoice(5,level);

        // exécuter la méthode showQuestion qui affiche chaque fois une nouvelle question avec les choix appropriés
        ShowQuestion();

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkAnswer) { //si on a pas encore visualiser les réponses
                    if (choice1.isChecked() || choice2.isChecked() || choice3.isChecked()) {//cette condition assure que l'étudiant a coché une réponse
                        ViewAnswer(); //redirection vers la méthode qui affiche la correction de la question
                        //récupérer le choix de l'étudiant et l'ajouter à la liste 'etudiantChoice', on sauvegardera ensuite ces données dans notre database.
                        RadioButton rb = findViewById(RG.getCheckedRadioButtonId());
                        etudiantChoices.add(rb.getText().toString());
                        if (Currentquestion.Response(rb.getText().toString()))
                            score++;//Si la réponse est correcte, le score augmente
                    } else
                        // si l'étudiant n'a rien coché , on affiche ce message
                        Toast.makeText(QuizActivity.this, "Select an answer!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (count == 0) {//Après la dernière question, on fait les opérations suivantes:
                        //Création d'un nouveau test
                        test = new test(username, listeQuestion, etudiantChoices, score);
                        //Remplissage des table test et testDetails
                        quizService.sauvegardertest(test,username);
                        quizService.sauvegarderTestDetails(test,username);
                        //Affichage de l'activité ResultActivity qui affichera le score
                        Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                        i.putExtra("score", score);//on fait passe le score à l'activité suivante
                        startActivity(i);
                    } else {
                        ShowQuestion();
                    }
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void ShowQuestion(){
        choice1.setTextColor(textColorDefaultRb);
        choice2.setTextColor(textColorDefaultRb);
        choice3.setTextColor(textColorDefaultRb);
        RG.clearCheck();//supprimer le cochage

        checkAnswer=false;

        //récupérer le question aléatoire ayant l'index (count-1)
        Currentquestion=listeQuestion.get(count-1);

        //Changer les textes affichés à l'écran
        QuestionNumber.setText("Question"+i);
        Question.setText(Currentquestion.getSubject());
        choice1.setText(Currentquestion.getChoice1());
        choice2.setText(Currentquestion.getChoice2());
        choice3.setText(Currentquestion.getChoice3());

        count-=1;//decrementation du nombre de question
        i+=1;//incrémentation de numéro de la question
        BtnNext.setText("Confirm");  //Changer le texte de boutton en 'Confirm'
    }

    @SuppressLint("SetTextI18n")
    public void ViewAnswer(){
        checkAnswer=true;
        //changee les couleur de tout les choix en rouge
        choice1.setTextColor(Color.RED);
        choice2.setTextColor(Color.RED);
        choice3.setTextColor(Color.RED);

        //récupérer le choix correcte et changer sa couleur en vert
        if(Currentquestion.Response(choice1.getText().toString()))
            choice1.setTextColor(Color.GREEN);
        if(Currentquestion.Response(choice2.getText().toString()))
            choice2.setTextColor(Color.GREEN);
        if(Currentquestion.Response(choice3.getText().toString()))
            choice3.setTextColor(Color.GREEN);

        if(count!=0)
            /*Après la visualization des réponse, on Change le texte
            de boutton en 'Next' pour afficher la question suivante */
            BtnNext.setText("Next");
        if (count==0)
            //Pour la dernière question, au lieu de Next, le text sur le boutton devient Finish
            BtnNext.setText("Finish");
    }
    }


