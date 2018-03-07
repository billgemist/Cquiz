package com.ptyxiaki.android.cquiz;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*==================== START OF DATABASE VALIDATES========================== */
    private ProgressDialog pDialog; // Progress Dialog


    JSONParser jParser = new JSONParser(); // Creating JSON Parser object

    //ArrayList<HashMap<String, String>> productsList;
    ArrayList<eachQuestion> qArray = new ArrayList<eachQuestion>();


    MyDBHandler db = new MyDBHandler(this); //my database handler

    //private static String url_all_questions = "https://cquizdata.000webhostapp.com/getallquestions_v2.php"; //url to get all questions from mysql with php

    private static String url_all_questions = "http://www.billgemist.com/getallquestions_v2.php"; //url to get all questions from mysql with php

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_QUESTIONS = "questions";
    private static final String TAG_QID = "qid";
    private static final String TAG_QUESTION = "question_pron";
    private static final String TAG_ANSWER_1 = "answer_1";
    private static final String TAG_ANSWER_2 = "answer_2";
    private static final String TAG_ANSWER_3 = "answer_3";
    private static final String TAG_ANSWER_4 = "answer_4";
    private static final String TAG_LEVEL = "lvl";
    private static final String TAG_CORRECT_IS = "correct_is";
    private static final String TAG_ASKING = "asking";

    // products JSONArray
    JSONArray questions = null;

    /*==================== END OF DATABASE VALIDATES========================== */

    //Level selection buttons
    private Button mEasyButton;
    private Button mMediumButton;
    private Button mHardButton;
    private Button mAboutButton;

    private int levelValue;

    private static final String KEY_LEVEL = "pass to gameActivity the level";
    private static final String KEY_LEVEL_INT = "pass to gameActivity the level in int form";
    private static final String KEY_ABOUT = "pass to aboutAcivity";


    private boolean internetCon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isNetworkAvailable(this)) {
            //Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();

            showDialogNoInternet(this);
           //finish(); //Calling this method to close this activity when internet is not available.
        }else {
            new LoadAllQuestions().execute();
        }
        setContentView(R.layout.activity_main);


        mEasyButton = (Button) findViewById(R.id.easy_button);
        mEasyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelValue = 1;
                Intent i = new Intent(MainActivity.this, gameActivity.class);
                i.putExtra(KEY_LEVEL, "Your level is Easy");
                i.putExtra(KEY_LEVEL_INT, levelValue);
                startActivity(i);
            }
        });

        mMediumButton = (Button) findViewById(R.id.medium_button);
        mMediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelValue = 2;
                Intent i = new Intent(MainActivity.this, gameActivity.class);
                i.putExtra(KEY_LEVEL, "Your level is Medium");
                //i.putExtra(KEY_LEVEL_INT, 3);
                i.putExtra(KEY_LEVEL_INT, levelValue);
                startActivity(i);
            }
        });

        mHardButton = (Button) findViewById(R.id.hard_button);
        mHardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelValue = 3;
                Intent i = new Intent(MainActivity.this, gameActivity.class);
                i.putExtra(KEY_LEVEL, "Your level is Hard");
                i.putExtra(KEY_LEVEL_INT, levelValue);
                startActivity(i);
            }
        });


        mAboutButton = (Button) findViewById(R.id.about_button);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

    }

    //checking internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

    //Dialog for no internet connection
    public void showDialogNoInternet(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        //if (title != null) builder.setTitle(title);
        builder.setTitle("No Internet Connection");

        builder.setMessage("Please connect to the internet and try again");
       // builder.setPositiveButton("OK", null);
        //builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();//Calling this method to close this activity when internet is not available.
            }
        });


        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.exit_game) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





    /*------------- AsyncTask class for fetch mysql table from the server --------------*/
    class LoadAllQuestions extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading questions. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }


        /**
         * getting All questions from url
         */
        protected String doInBackground(String... args) {

            //deleting previous database
            db.deleteAll();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_questions, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Questions: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // questions found
                    // Getting Array of Questions
                    questions = json.getJSONArray(TAG_QUESTIONS);

                    // looping through All Questions
                    for (int i = 0; i < questions.length(); i++) {
                        JSONObject c = questions.getJSONObject(i);

                        // Storing each json item in variable
                        int id = c.getInt(TAG_QID);
                        String questionPron = c.getString(TAG_QUESTION);
                        String answer1 = c.getString(TAG_ANSWER_1);
                        String answer2 = c.getString(TAG_ANSWER_2);
                        String answer3 = c.getString(TAG_ANSWER_3);
                        String answer4 = c.getString(TAG_ANSWER_4);
                        int level = c.getInt(TAG_LEVEL);
                        int correctIs = c.getInt(TAG_CORRECT_IS);
                        String asking = c.getString(TAG_ASKING);


                        //Creating an each question object for every entry of the mysql database
                        eachQuestion ea = new eachQuestion();
                        ea.setId(id);
                        ea.setQuestionPron(questionPron);
                        ea.setAnswer1(answer1);
                        ea.setAnswer2(answer2);
                        ea.setAnswer3(answer3);
                        ea.setAnswer4(answer4);
                        ea.setLevel(level);
                        ea.setCorrectIs(correctIs);
                        ea.setAsk(asking);

                        Log.i("oct", "before db insertion");
                        db.addQuestion(ea); //adding question to database
                        Log.i("oct", "after db insertion");



                    }
                } else {
                    Log.i("oct", "nothing in the database");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all questions
            pDialog.dismiss();

            //Launching gameActivity
            //Intent i = new Intent(MainActivity.this, gameActivity.class);
            //startActivity(i);
        }
    }
}

