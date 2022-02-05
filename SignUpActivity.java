package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    //Les paramètres de Main Activity
    //L'étudiant est invité dans un premier temps à entrer son nom, son id et sa filière
    private EditText editTextFirstName;
    private EditText editTextSecondName;
    private EditText editusername;
    private EditText editTextFiliere;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editusername = (EditText) findViewById(R.id.editusername1);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextSecondName = (EditText) findViewById(R.id.editTextSecondName);
        editTextFiliere = (EditText) findViewById(R.id.editTextFiliere);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword1);
    }

    public void StartQuiz(View view){
        //récupération des données entrées
        String firstName = editTextFirstName.getText().toString();
        String secondName = editTextSecondName.getText().toString();
        String field = editTextFiliere.getText().toString();
        String username = editusername.getText().toString();
        String password = editTextPassword.getText().toString();

        //initialisation de database
        QuizService quizService= new QuizService(getApplicationContext());
        //quizService.fillQuestionTable();//remplissage de la table questions
        //Ajout d'un nouveau étudiant dans la base de données
        Etudiant etudiant= new Etudiant(username, firstName, secondName, field, password);
        long i=quizService.sauvegarderEtudiant(etudiant); // s'il y avait des problèmes dans l'ajout i prendra la valeur -1
        if(i==-1)
            //afficher un message de failure
            Toast.makeText(this, "Fail To Sign Up", Toast.LENGTH_SHORT).show();
        else{
            //afficher un message de succès et ouvrir l'activité de quiz
            Toast.makeText(this, "Signed Up successfully", Toast.LENGTH_SHORT).show();

            Intent level= new Intent(this,LevelActivity.class);
            //transporter l'username de l'étudiant vers l'activité suivante , on en aura besoin pour remplir les autres tables
            level.putExtra("username", username);
            startActivity(level);}
    }

}
