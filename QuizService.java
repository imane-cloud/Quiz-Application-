package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quizapp.Question;
import com.example.quizapp.QuizDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*cette classe permet la manipulation de database créée dans QuizDBHelper
a travers les paramètres DBWritable (écrice)et DBReadable (lire)*/

public class QuizService {

    SQLiteDatabase dBWritable;
    SQLiteDatabase dBReadable;

    //consturctors
    public QuizService (Context context){
        QuizDBHelper dbHelper=new QuizDBHelper(context);
        dBWritable=dbHelper.getWritableDatabase();
        dBReadable=dbHelper.getReadableDatabase();

    }

    //cette méthode retourne la liste des questions de notre base de données
    public List<Question> listeQuetion(String level){
        List<Question> ListQuestion = new ArrayList<>();
        String colonnes[]={"question_ID","level","Subject","choice1","choice2","choice3","RightAnswer"};
        Cursor c=dBReadable.query("questions",colonnes, "level"+"=?",new String[]{level},null,null,null);
        if (c.moveToFirst()){
            do{
            //Ajout des questions dans la liste
            ListQuestion.add(new Question(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
            }while(c.moveToNext());
        }
        c.close();
        return ListQuestion;
    }

    //Cette méthode génère une liste de 'i' question aléatoirement choisi d'un niveau précis
    public List<Question> RandomChoice(int i,String level){
        List<Question> RandomQuestion= new ArrayList<>();
        List<Question> list = this.listeQuetion(level); //On utilise la méthode précedente pour récupérer la liste des questions
        ArrayList<Integer> ListOfNumbers = new ArrayList<Integer>(i+1);
        for (int j=0; j<i; j++) {
            ListOfNumbers.add(j); //une liste des nombre de 0 jusqu'à i
        }
        for(int compteur=1; compteur<=i;compteur++){
            Random rand = new Random();
            int randomIndex = rand.nextInt(ListOfNumbers.size());
            int randomElement = ListOfNumbers.get(randomIndex);
            ListOfNumbers.remove(randomIndex);
            RandomQuestion.add(list.get(randomElement));
        }
        return RandomQuestion;
    }

    //cette méthode insère un nouveau étudiant dans la table étudiants
    public long sauvegarderEtudiant(Etudiant etudiant){
        ContentValues values = new ContentValues();
        values.put("password",etudiant.getPassword());
        values.put("username",etudiant.getusername());
        values.put("nom",etudiant.getNom());
        values.put("prenom",etudiant.getPrenom());
        values.put("filiere",etudiant.getFiliere());
        return dBWritable.insert("etudiants",null,values);
    }

    //cette méthode insère une nouvelle question dans la table questions
    public long sauvegarderQuestion(Question question){
        ContentValues values = new ContentValues();
        values.put("question_ID",question.getID());
        values.put("level",question.getLevel());
        values.put("Subject",question.getSubject());
        values.put("choice1",question.getChoice1());
        values.put("choice2",question.getChoice2());
        values.put("choice3",question.getChoice3());
        values.put("RightAnswer",question.getRightChoice());

        return dBWritable.insert("questions",null,values);
    }

    //cette méthode insère les détails d'un nouveau test dans la table testDetails
    public void sauvegarderTestDetails(test test,String username){
        int j=test.Questions.size();
        for(int i=0; i<j;i++) {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("question_ID", test.getTestquestion_ID(i));
            values.put("EtudiantAnswer", test.getQuestionEtudiantAnswer(i));
            values.put("RightAnswer", test.getQuestionRightAnswer(i));
            dBWritable.insert("testDetails",null,values);
        }
    }

    //cette méthode insère un nouveau test dans la table tests
    public long sauvegardertest(test test,String username){
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("score", test.getScore());
        values.put("Date", test.getDate());

        return dBWritable.insert("tests",null,values);
    }

    //Cette méthode vérifie si un username entré existe dans la base de données
    public Boolean checkusername(String username) {
        Cursor cursor = dBReadable.rawQuery("Select * from etudiants where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //Cette méthode vérifie la compatibilité d'un username avec son mot de passe;
    public Boolean checkusernamepassword(String username, String password){
        Cursor cursor = dBReadable.rawQuery("Select * from etudiants where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    //Cette méthode retourne le score du dernier test d'un étudiant à partir de son username

    @SuppressLint("Range")
    public String getscore(String username) {
        List<Integer> msgList = new ArrayList<Integer>();
        Cursor cursor = dBReadable.rawQuery("Select * from tests where username = ?", new String[]{username});
        while (cursor.moveToNext()) {
            msgList.add(cursor.getInt(cursor.getColumnIndex("score")));
        }
        return msgList.get(0).toString();
    }

    //Cette méthode retourne la date du dernier test à partir de l'username
    @SuppressLint("Range")
    public String getDate(String username) {
        List<String> msgList = new ArrayList<String>();
        Cursor cursor = dBReadable.rawQuery("Select * from tests where username = ?", new String[]{username});
        while (cursor.moveToNext()) {
            msgList.add(cursor.getString(cursor.getColumnIndex("Date")));
        }
        return msgList.get(0);
    }

    //Cette méthode créée un compte pour le professeur
    public void CompteProf(){
        Etudiant etudiant= new Etudiant("LAMIRI","LAMIRI","ABDENABY","Prof","LAMIRI");
        this.sauvegarderEtudiant(etudiant);
    }

    //Cette méthode retourne les questions ID d'un test qu'a effectué un username
    @SuppressLint("Range")
    public List<String> getquestionsID(String username) {
        List<String> msgList = new ArrayList<String>();
        Cursor cursor = dBReadable.rawQuery("Select * from testDetails where username = ?", new String[]{username});
        while (cursor.moveToNext()) {
            msgList.add(cursor.getString(cursor.getColumnIndex("question_ID")));//récupérer les valeur du curseur
        }
        return msgList;
    }
    //Cette méthode retourne les réponses correctes des questions d'un test qu'a effectué un username
    @SuppressLint("Range")
    public List<String> getRightAnswers(String username) {
        List<String> msgList = new ArrayList<String>();
        Cursor cursor = dBReadable.rawQuery("Select * from testDetails where username = ?", new String[]{username});
        while (cursor.moveToNext()) {
            msgList.add(cursor.getString(cursor.getColumnIndex("RightAnswer")));
        }
        return msgList;
    }
    //Cette méthode retourne les réponses d'un étudiants sur un test a partir de son username
    @SuppressLint("Range")
    public List<String> getStudentAnswers(String username) {
        List<String> msgList = new ArrayList<String>();
        Cursor cursor = dBReadable.rawQuery("Select * from testDetails where username = ?", new String[]{username});
        while (cursor.moveToNext()) {
            msgList.add(cursor.getString(cursor.getColumnIndex("EtudiantAnswer")));
        }
        return msgList;
    }


    //cette méthode est pour remplir la table questions
    public void fillQuestionTable() {
        Question question1 = new Question(1, "easy", "Dans la sécurité informatique, _______ signifie que les systèmes actifs informatique ne peuvent être modifiés que par les personnes autorisées.", "La confidentialité", "L'intégrité", "La disponibilité", "L'intégrité");
        Question question2 = new Question(2, "easy", "Dans la sécurité informatique, _______ signifie que les informations contenues dans un système informatique ne sont accessibles en lecture que par les personnes autorisées.", "La confidentialité", "L’intégrité", "L’authenticité", "La confidentialité");
        Question question3 = new Question(3, "easy", "Les types de menaces pour la sécurité d’un système informatique ou d’un réseau sont _______ ?", "Interruption", "Intégration", "Création", "Interruption");
        Question question4 = new Question(4, "easy", "Lequel des programmes suivants est un programme malveillant indépendant qui ne nécessite aucun d’autre programme?", "Porte à piège", "Cheval de Troie", "Ver", "Ver");
        Question question5 = new Question(5, "easy", "Le _______ est un code qui reconnaît une séquence d’entrée spéciale ou qui est déclenché par une séquence d’événements improbable.", "Porte à piège", "Cheval de Troie", "Bombe logique", "Porte à piège");
        Question question6 = new Question(8, "easy", "________ est une forme de virus explicitement conçue pour éviter la détection par des logiciels antivirus.", "Virus furtif", "Virus polymorphe", "Virus parasite", "Virus furtif");
        Question question7 = new Question(9, "easy", "Un ________ exécute une copie de lui-même sur un autre système.", "Ver", "Bombe logique", "Cheval de Troie", "Ver");
        Question question8 = new Question(10, "easy", "______ est un programme qui peut infecter d’autres programmes en les modifiant. Cette modification inclut une copie du programme viral, qui peut ensuite infecter d’autres programmes.", "Ver", "Virus", "Trap doors", "Virus");



        Question question11 = new Question(11, "medium", "______ sont utilisés dans les attaques par déni de service, généralement contre des sites Web ciblés.", "Cheval de Troie", "Zombie", "Ver", "Zombie");
        Question question12 = new Question(12, "medium", "Le type de codage dans lequel la manipulation de flux de bits sans tenir compte de la signification des bits est appelé_________?", "Codage de destination", "Codage entropique", "Codage de source", "Codage entropique");
        Question question13 = new Question(13, "medium", "Le protocole utilisé pour sécuriser les e-mails? ", "SNMP", "HTTP", "PGP", "PGP");
        Question question14 = new Question(14, "medium", "L’art de casser des chiffres est connu comme ____?", "Cryptanalyse", "Cryptographie", "Cryptage", "Cryptanalyse");
        Question question15 = new Question(15, "medium", "Lequel des énoncés suivants est correct?", "Caractère – représenté par un complément", "Caractère – représenté par Unicode", "Integer – représenté par ASCII", "Caractère – représenté par Unicode");
        Question question16 = new Question(16, "medium", "Le nombre de sous-clés générées dans l’algorithme IDEA est _______?", "52", "48", "54", "52");
        Question question17 = new Question(17, "medium", "Le nombre de « S-boxes » utilisées dans l’algorithme DES est _______?", "8", "16", "32", "8");
        Question question18 = new Question(18, "medium", "______ est un exemple d’algorithme de clé publique.", "IREA", "RSA", "DES", "RSA");
        Question question19 = new Question(19, "medium", "Le chiffre de César est représenté par _______?", "C = (p + 3) mod3", "C = (p + 3) mod26", "C = (p – 3) mod26", "C = (p + 3) mod3");
        Question question20 = new Question(20, "medium", "Le ______ s’attache aux fichiers exécutables et se réplique, lorsque le programme infecté est exécuté, en recherchant d’autres fichiers exécutables à infecter.", "Virus furtif", "Virus parasite", "Virus polymorphe", "Virus parasite");

        Question question21 = new Question(21, "hard", "Nombre de tours dans l’algorithme DES(Data Encryption Standard) est ______?", "13 tours", "12 tours", "16 tours", "16 tours");
        Question question22 = new Question(22, "hard", "________ transforme le message en format qui ne peut pas être lu par les pirates.", "Cryptage", "Transformation", "Décryptage", "Cryptage");
        Question question23 = new Question(23, "hard", "Quel est le numéro de port pour HTTPS (HTTP Secure)?", "443", "445", "444", "443");
        Question question24 = new Question(24, "hard", "Le chiffrement et le déchiffrement des données est la responsabilité de quelle couche?", "Couche réseau", "Couche de présentation", "Couche de transport", "Couche de présentation");
        Question question25 = new Question(25, "hard", "Quel est l’algorithme d’échange de clé utilisé dans le paramètre d’une suite cryptographique(Cipher Suite)?", "RSA", "Diffie-Hellman", "Les deux réponses sont vrais", "Les deux réponses sont vrais");
        Question question26 = new Question(26, "hard", "Le message de certificat est requis pour toute méthode d’échange de clé convenue, sauf __________.", "Anonymous Diffie-Hellman", "RSA", "Fixed Diffie-Hellman", "Anonymous Diffie-Hellman");
        Question question27 = new Question(27, "hard", "Les types de menaces pour la sécurité d’un système informatique ou d’un réseau sont _______ ?", "Interruption", "Les deux réponses sont vraies", "Modification", "Les deux réponses sont vraies");
        Question question28 = new Question(28, "hard", "Les pare-feu sont utilisés pour __________", "Le routage", "Le tunneling", "La sécurité", "La sécurité");
        Question question29 = new Question(29, "hard", "_________ est utilisé pour valider l’identité de l’expéditeur du message auprès du destinataire.", "Certificat numérique", "Cryptage", "Décryptage", "Certificat numérique");
        Question question30 = new Question(30, "hard", "Lorsque vous vous connectez à un service en ligne, vous êtes invité à fournir une sorte d’identification, telle que votre nom, numéro de compte et le mot de passe. Quel est le nom donné à ce bref dialogue?", "Procédure de connexion", "Procédure d’identification", "Procédure de sauvegarde", "Procédure de connexion");


        sauvegarderQuestion(question1);
        sauvegarderQuestion(question2);
        sauvegarderQuestion(question3);
        sauvegarderQuestion(question4);
        sauvegarderQuestion(question5);
        sauvegarderQuestion(question6);
        sauvegarderQuestion(question7);
        sauvegarderQuestion(question8);
        sauvegarderQuestion(question11);
        sauvegarderQuestion(question12);
        sauvegarderQuestion(question13);
        sauvegarderQuestion(question14);
        sauvegarderQuestion(question15);
        sauvegarderQuestion(question16);
        sauvegarderQuestion(question17);
        sauvegarderQuestion(question18);
        sauvegarderQuestion(question19);
        sauvegarderQuestion(question20);
        sauvegarderQuestion(question21);
        sauvegarderQuestion(question22);
        sauvegarderQuestion(question23);
        sauvegarderQuestion(question24);
        sauvegarderQuestion(question25);
        sauvegarderQuestion(question26);
        sauvegarderQuestion(question27);
        sauvegarderQuestion(question28);
        sauvegarderQuestion(question29);
        sauvegarderQuestion(question30);

    }
}




