package com.example.quizapp;

//les paramètres de cette classe sont  : id de l'étudiant  _ nom _ prenom _ filière
public class Etudiant {
    private String username;
    String password;
    private String nom;
    private String prenom;
    private String filiere;

    //constructors
    public Etudiant(String username,String nom, String prenom, String filiere, String password){
        this.username=username;
        this.password=password;
        this.nom = nom;
        this.prenom=prenom;
        this.filiere=filiere;
    }
    public Etudiant (String username, String nom, String prenom, String filiere){
        this.username=username;
        this.nom = nom;
        this.prenom=prenom;
        this.filiere=filiere;

    }
     //getters

    public String getusername() {
        return username;
    }

    public String getFiliere() {
        return filiere;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword(){return password;}


    @Override
    public String toString() {
        return "Etudiant{" +
                "username" + username +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", filiere='" + filiere + '\'' +
                '}';
    }

}

