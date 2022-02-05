package com.example.quizapp;

/*les paramètres de cette classe sont  : id du question  _ level du question _ choix 1 _ choix 2 _ choix 3 _
                                          contenu de question (subject) _ la réponse correcte (right choice)*/
public class Question {
    private int question_ID;
    private String level;
    private String Choice1;
    private String Choice2;
    private String Choice3;
    private String RightChoice;
    private String Subject;

    //constructor
    public Question(int question_ID,String level,String Subject,String choice1,String choice2,String choice3,String RightChoice) {
        this.question_ID = question_ID;
        this.level=level;
        this.Choice1=choice1;
        this.Choice2=choice2;
        this.Choice3=choice3;
        this.RightChoice=RightChoice;
        this.Subject=Subject;
    }

    //getters
    public String getChoice1() {
        return Choice1;
    }

    public String getChoice2() {
        return Choice2;
    }

    public String getChoice3() {
        return Choice3;
    }

    public String getRightChoice() {
        return RightChoice;
    }

    public String getSubject() {
        return Subject;
    }

    public String getLevel() {
        return level;
    }

    public long getID() {
        return question_ID;
    }

    //cette méthode compare la réponse de l'étudiant avec la réponse correcte et retourne true s'ils sont identiques
    public boolean Response(String ChoiceEtudiant) {
        if (ChoiceEtudiant.compareTo(this.RightChoice)==0)
            return true;
        else
            return false;
    }
}
