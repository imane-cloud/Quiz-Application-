package com.example.quizapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*les paramètres de cette classe sont  : etudiant  _ liste des questions de test
                                        liste des choix de l'étudiant _ score de l'étudiant _ date de test */

public class test {

    String username;
    List<Question> Questions;
    List<String> EtudiantChoices;
    int Score;
    //affecter automatiquement la date
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String date= formatter.format(new Date());

    //constructor
    public test(String username,List<Question> Questions,List<String> EtudiantChoices,int Score) {
        this.username=username;
        this.EtudiantChoices=EtudiantChoices;
        this.Questions=Questions;
        this.Score=Score;
    }

    //getters
    public String getDate() {
        return date;
    }

    //cette méthode retourne l'id du question i
    public long getTestquestion_ID( int i){
        return this.Questions.get(i).getID();
    }

    //cette méthode retourne la réponse de l'étudiant sur la question i
    public String getQuestionEtudiantAnswer(int i){
        return this.EtudiantChoices.get(i);
    }

    //cette méthode retourne la réponse correcte sur la question i
    public String getQuestionRightAnswer(int i){
        return this.Questions.get(i).getRightChoice();
    }

    public int getScore() {
        return Score;
    }
}
