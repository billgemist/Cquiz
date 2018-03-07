package com.ptyxiaki.android.cquiz;

/**
 * Created by billgemistos on 25/10/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "cq";

    // Contacts table name
    private static final String TABLE_QUESTIONS = "questions";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question_pron";
    private static final String KEY_ANSWER1 = "answer_1";
    private static final String KEY_ANSWER2 = "answer_2";
    private static final String KEY_ANSWER3 = "answer_3";
    private static final String KEY_ANSWER4 = "answer_4";
    private static final String KEY_LEVEL = "lvl";
    private static final String KEY_CORRECT = "correct_is";
    private static final String KEY_ASKING = "asking";


    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER1 + " TEXT," + KEY_ANSWER2 + " TEXT," + KEY_ANSWER3 + " TEXT," + KEY_ANSWER4 + " TEXT," + KEY_LEVEL + " INTEGER," + KEY_CORRECT + " INTEGER," + KEY_ASKING + " TEXT" + ")";


        db.execSQL(CREATE_QUESTIONS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);

        // Create tables again
        onCreate(db);
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
        //db.execSQL("delete from "+ TABLE_QUESTIONS);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new question
    void addQuestion(eachQuestion question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(KEY_QUESTION, question.getQuestionPron());
        values.put(KEY_ANSWER1, question.getAnswer1());
        values.put(KEY_ANSWER2, question.getAnswer2());
        values.put(KEY_ANSWER3, question.getAnswer3());
        values.put(KEY_ANSWER4, question.getAnswer4());
        values.put(KEY_LEVEL, question.getLevel());
        values.put(KEY_CORRECT, question.getCorrectIs());
        values.put(KEY_ASKING, question.getAsk());



        // Inserting Row
        db.insert(TABLE_QUESTIONS, null, values);
        db.close(); // Closing database connection


    }

    public ArrayList<eachQuestion> getAllQuestions(int givenlvl, int lmt) {
        int glvl = givenlvl;
        int limitVar = lmt;


        ArrayList<eachQuestion> questionsArray = new ArrayList<eachQuestion>();
        // Select All Query
       String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS + " WHERE lvl = " + glvl + " ORDER BY RANDOM()" + "LIMIT " + limitVar;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //get the index from the column name provided
        int idColumn = cursor.getColumnIndex(KEY_ID);
        int questionColumn = cursor.getColumnIndex(KEY_QUESTION);
        int answer1Column = cursor.getColumnIndex(KEY_ANSWER1);
        int answer2Column = cursor.getColumnIndex(KEY_ANSWER2);
        int answer3Column = cursor.getColumnIndex(KEY_ANSWER3);
        int answer4Column = cursor.getColumnIndex(KEY_ANSWER4);
        int levelColumn = cursor.getColumnIndex(KEY_LEVEL);
        int correctAnswerColumn = cursor.getColumnIndex(KEY_CORRECT);
        int askingColumn = cursor.getColumnIndex(KEY_ASKING);


        cursor.moveToFirst(); // move to the first row of results
        // Verify that we have results
        if(cursor != null && (cursor.getCount() > 0)){

            do{
                // Get the results and store them in a String
                int id = cursor.getInt(idColumn);
                String question = cursor.getString(questionColumn);
                String answer_1 = cursor.getString(answer1Column);
                String answer_2 = cursor.getString(answer2Column);
                String answer_3 = cursor.getString(answer3Column);
                String answer_4 = cursor.getString(answer4Column);
                int level = cursor.getInt(levelColumn);
                int correct_answer = cursor.getInt(correctAnswerColumn);
                String myAsk = cursor.getString(askingColumn);

                eachQuestion ea = new eachQuestion();

                ea.setId(id);
                ea.setQuestionPron(question);
                ea.setAnswer1(answer_1);
                ea.setAnswer2(answer_2);
                ea.setAnswer3(answer_3);
                ea.setAnswer4(answer_4);
                ea.setLevel(level);
                ea.setCorrectIs(correct_answer);
                ea.setAsk(myAsk);

                questionsArray.add(ea);

                // Keep getting results as long as they exist
            }while(cursor.moveToNext());



        }
        return questionsArray;
    }


    public void deleteDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
    }

}
