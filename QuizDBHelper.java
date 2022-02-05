package com.example.quizapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuizDBHelper extends SQLiteOpenHelper {
    // creation de database
    public QuizDBHelper(@Nullable Context context){
        super(context,"quiz.db",null,1);

    }
    //creation des tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table etudiants"+"(username TEXT ,nom TEXT, prenom TEXT,filiere TEXT,password TEXT)");
        sqLiteDatabase.execSQL("create table questions"+"(question_ID INTEGER PRIMARY KEY AUTOINCREMENT,level TEXT,Subject TEXT,choice1 TEXT,choice2 TEXT,choice3 TEXT,RightAnswer TEXT)");
        sqLiteDatabase.execSQL("create table tests"+"(username TEXT,score INTEGER, Date TEXT)");
        sqLiteDatabase.execSQL("create table testDetails"+"(username TEXT, question_ID INTEGER,RightAnswer TEXT,EtudiantAnswer TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists  etudiants");
        sqLiteDatabase.execSQL("drop table if exists  questions");
        sqLiteDatabase.execSQL("drop table if exists  tests");
        sqLiteDatabase.execSQL("drop table if exists  testDetails");
        onCreate(sqLiteDatabase);
    }


}
