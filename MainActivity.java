package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Les paramètres de Main Activity
    //L'étudiant est invité dans un premier temps à entrer son nom, son id et sa filière
    private EditText editTextusername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword1);
        editTextusername=(EditText) findViewById(R.id.editusername1);

    }

    public void StartQuiz1(View view){
        //récupération des données entrées
        String username = editTextusername.getText().toString();
        String password = editTextPassword.getText().toString();

        //initialisation de database
        QuizService quizService= new QuizService(getApplicationContext());
        quizService.CompteProf();//créer compte professeur
        quizService.fillQuestionTable();//créer compte professeur

        if(quizService.checkusername(username)){
            if(quizService.checkusernamepassword(username, password)){
                //si l'username et le mot de passe entrés sont correctes, on affiche un message de succès et on démarre l'activité suivante
                Toast.makeText(this, "Signed In successfully", Toast.LENGTH_SHORT).show();
                if(username.equals("LAMIRI")||password.equals("LAMIRI")){
                    Intent i=new Intent(this,ProfActivity.class);
                    startActivity(i);
                }
                else{
                    Intent level= new Intent(this,LevelActivity.class);
                    //on passe la valuer de l'username de l'étudiant vers l'activité suivante , on en aura besoin pour remplir les autres tables
                    level.putExtra("username", username);
                    startActivity(level);
                }
            }
            else
                //message d'erreur affiché si le mot de passe est incorrecte
                Toast.makeText(this, "Wrong password!!", Toast.LENGTH_SHORT).show();
        }
        else
            //message d'erreur affiché si l'username n'existe pas dans la base de données'
            Toast.makeText(this, "Wrong Username!!", Toast.LENGTH_SHORT).show();
        }


    //Cette méthode Démarre l'activité  SignUpActivity
    public void SignUp(View v){
        //Si l'utilisateur clique sur le boutton Sign Up , il sera redirigé vers l'activité Sign Up
        Intent i=new Intent(this,SignUpActivity.class);
        startActivity(i);
    }



}
