package com.ptyxiaki.android.cquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class gameActivity extends Activity {

    Random rand = new Random(); //for questions roll
    int n = 0; //integer used for random generation
    int qidTester = 0; //used in the updateQuestion function for keeping track of qid of selected question and if the question has been asked again

    int quizLengthCounter = 0; //up to 10 questions of each quiz, if is level 1(), level 2() and level 3()


    private TextView mLevelInfoTextView;
    private int whatLevel; //keeps it from the launch view what is the selected level
    private String questionSet = "public void function(){ int c; return c;}"; //this is what looks like if question uses string to inflate the question to textview

    //4 possible answers button
    private Button answer1_button;
    private Button answer2_button;
    private Button answer3_button;
    private Button answer4_button;

    //textView
    private TextView questionTextView;

    //Finished game layout widgets
    private Button next_button;
    private Button skip_button;
    private TextView correct_or_false;
    private TextView correct_text_view;
    private TextView false_text_view;
    private TextView fin_question_text_view;
    private TextView pre_c_answer_tv;
    private TextView pre_f_answer_tv;
    private TextView score_tv;
    private TextView question_no;

    private TextView asking_text_view;

    private int scoredCorrect = 0; // how many questions answered correctly by the player



    private questionFormatinC formatting = new questionFormatinC(); //class questionFormatinC format the question to show in text view

    private int arrayCounter = 0; //increases by 1 each time a answer button is pressed
    private  int c = 0;//counter used within updateQuestion
    private int qCounter = 1; //counter within score update for tracking the number of question when pressed the next button

    //finale screen buttons
    private Button ngame_button;
    private Button qgame_button;



    private String fStr;


    private static final String KEY_LEVEL = "pass to gameActivity the level";
    private static final String KEY_LEVEL_INT = "pass to gameActivity the level in int form";

    ArrayList<eachQuestion> qArray = new ArrayList<eachQuestion>(); //array with all question of selected level
    ArrayList<eachQuestion> qArray2 = new ArrayList<eachQuestion>(); //array with all questions of other level
    ArrayList<eachQuestion> qArray3 = new ArrayList<eachQuestion>(); //array with all questions of other level
    ArrayList<eachQuestion> mainBoard = new ArrayList<eachQuestion>();

    ArrayList<Integer> p_qids = new ArrayList<Integer>(); //An arraylist of previous id's of questions

    private int scoreBoard[];

    //SETTING NEXT AND NEXT QUESTION EACH TIME
    private void updateQuestion() {
//10 august: prepei na valw na ananewnei kai to asking text
        
        //To show all the questions of each level
        if(qArray.size() > arrayCounter ){ //because
            fStr = formatting.format(qArray.get(arrayCounter).getQuestionPron());

            questionTextView.scrollTo(0,0); //set scrollbar at the top again
            questionTextView.setText(fStr); //set the question text to the text view

            answer1_button.setText(qArray.get(arrayCounter).getAnswer1());//setting the buttons
            answer2_button.setText(qArray.get(arrayCounter).getAnswer2());
            answer3_button.setText(qArray.get(arrayCounter).getAnswer3());
            answer4_button.setText(qArray.get(arrayCounter).getAnswer4());

            asking_text_view.setText(qArray.get(arrayCounter).getAsk());//setting the question's asking text


            Log.i("ARRAY DISPLAY", fStr);
        }
        else{

            scoreLayout();
            Log.i("TO", "" + fStr);
            for(int i = 0; i < qArray.size(); i++ ){
                Log.i("oct", "" + scoreBoard[i]);
            }
            Log.i("oct", "" + scoreBoard.length);
        }

    }


    //Method for Closing the app using the quit button
    public void AppExit()
    {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    /*int pid = android.os.Process.myPid();=====> use this if you want to kill your activity. But its not a good one to do.
    android.os.Process.killProcess(pid);*/

    }




        //Checking if a question has been asked in the quiz before

    private boolean checkinId(int qid){ //qid is the passing qidTester
            for(int k=0; k < p_qids.size(); k++){
                if(p_qids.get(k) == qid){
                    return false;
                }
            }
            return true;
    }

    //Results LAYOUT FINISHED GAME
    private void scoreLayout(){
        setContentView(R.layout.finished_game);
        //Finding Finished layout widgets id
        next_button = (Button) findViewById(R.id.next_button);
        skip_button = (Button) findViewById(R.id.skip_button);
        correct_or_false = (TextView) findViewById(R.id.corf_text_view);
        correct_text_view = (TextView) findViewById(R.id.correct_answer_text_view);
        false_text_view = (TextView) findViewById(R.id.false_answer_text_view);
        fin_question_text_view = (TextView) findViewById(R.id.q_text_view);
        pre_c_answer_tv = (TextView) findViewById(R.id.pre_c_answer_tv);
        pre_f_answer_tv = (TextView) findViewById(R.id.pre_f_answer_tv);
        score_tv = (TextView) findViewById(R.id.score_info_text);
        question_no = (TextView) findViewById(R.id.questions_no);
       // ngame_button = (Button) findViewById(R.id.new_game_button);
        //qgame_button = (Button) findViewById(R.id.quit_game_button);


        fin_question_text_view.setMovementMethod(new ScrollingMovementMethod()); //making text view scrollable

        //Load 1st question and results
        fStr = formatting.format(qArray.get(c).getQuestionPron());
        fin_question_text_view.setText(fStr);


        for(int k = 0; k<scoreBoard.length; k++){
            if(scoreBoard[k] == qArray.get(k).getCorrectIs()){
                scoredCorrect++;
            }
        }

        score_tv.setText(scoredCorrect + "/" + scoreBoard.length + " answered correctly");

        question_no.setText("Question Number: " + qCounter);


        if(scoreBoard[c] == qArray.get(c).getCorrectIs()){

            pre_f_answer_tv.setText("");
            false_text_view.setText("");

            correct_or_false.setText("Correct!");
            pre_c_answer_tv.setText("Correct Answer: ");
            correct_text_view.setText(qArray.get(c).show(qArray.get(c).getCorrectIs()));
            correct_or_false.setTextColor(getResources().getColor(R.color.green));
        }
        else{
            correct_or_false.setText("False!");
            correct_or_false.setTextColor(getResources().getColor(R.color.red));
            pre_c_answer_tv.setText("Correct Answer: ");
            correct_text_view.setText(qArray.get(c).show(qArray.get(c).getCorrectIs()));

            pre_f_answer_tv.setText("Your Answer: ");
            false_text_view.setText(qArray.get(c).show(scoreBoard[c]));
        }

        //Loop for score
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                qCounter++;
                pre_f_answer_tv.setText("");
                false_text_view.setText("");
                if (qArray.size() > c) {
                    fStr = formatting.format(qArray.get(c).getQuestionPron());
                    fin_question_text_view.scrollTo(0,0);
                    fin_question_text_view.setText(fStr);
                    question_no.setText("Question Number: " + qCounter);

                    if (scoreBoard[c] == qArray.get(c).getCorrectIs()) {
                        correct_or_false.setText("Correct!");
                        correct_or_false.setTextColor(getResources().getColor(R.color.green));
                        correct_text_view.setText(qArray.get(c).show(qArray.get(c).getCorrectIs()));
                        false_text_view.setText("");
                    } else {
                        correct_or_false.setText("False!");
                        correct_or_false.setTextColor(getResources().getColor(R.color.red));
                        pre_c_answer_tv.setText("Correct Answer: ");
                        correct_text_view.setText(qArray.get(c).show(qArray.get(c).getCorrectIs()));
                        pre_f_answer_tv.setText("Your Answer: ");
                        false_text_view.setText(qArray.get(c).show(scoreBoard[c]));
                    }
                }else{//c = 0;
                    setContentView(R.layout.finale_screen);

                    ngame_button = (Button) findViewById(R.id.new_game_button);
                    qgame_button = (Button) findViewById(R.id.quit_game_button);

                    ngame_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });

                    qgame_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AppExit();
                        }
                    });





                     }
            }
        });

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.finale_screen);

                ngame_button = (Button) findViewById(R.id.new_game_button);
                qgame_button = (Button) findViewById(R.id.quit_game_button);

                ngame_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(gameActivity.this, MainActivity.class);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                qgame_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppExit();
                    }
                });

            }
        });
    }



// ON CREATE STANDAR METHOD HERE IS THE START //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mLevelInfoTextView = (TextView) findViewById(R.id.level_info);
        mLevelInfoTextView.setText(getIntent().getStringExtra(KEY_LEVEL));

        //whatLevel maps the level user selected
        whatLevel=getIntent().getIntExtra(KEY_LEVEL_INT, 0); //the second value is what will be returned if something happens

        MyDBHandler db = new MyDBHandler(this);

        qArray = db.getAllQuestions(whatLevel, 6); // Gets all questions with the given level


        if(whatLevel == 1){
            qArray2 = db.getAllQuestions(2, 2);
            qArray.addAll(qArray2);
            qArray3 = db.getAllQuestions(3, 2);
            qArray.addAll(qArray3);

        }
        else if(whatLevel == 2){
            qArray2 = db.getAllQuestions(1, 2);
            qArray.addAll(qArray2);
            qArray3 = db.getAllQuestions(3, 2);
            qArray.addAll(qArray3);
        }
        else{
            qArray2 = db.getAllQuestions(1, 2);
            qArray.addAll(qArray2);
            qArray3 = db.getAllQuestions(2, 2);
            qArray.addAll(qArray3);
        }



        scoreBoard = new int[qArray.size()]; //Exei to idio megethos me ton pinaka twn erwtisewn


        questionTextView = (TextView) findViewById(R.id.C_text_view);

   //     questionTextView.setText(qArray.get(0).getQuestionPron());
       // questionTextView.setText(fStr); //if I select not to use the format class that I created this command will be : mCtextView.setText(questionSetOtherWay);

        asking_text_view = (TextView) findViewById(R.id.asking_text);
        questionTextView.setMovementMethod(new ScrollingMovementMethod()); //making text view scrollable



        answer1_button = (Button) findViewById(R.id.answer_1_button);
        answer2_button = (Button) findViewById(R.id.answer_2_button);
        answer3_button = (Button) findViewById(R.id.answer_3_button);
        answer4_button = (Button) findViewById(R.id.answer_4_button);

        //Setting the 1st Question View
        fStr = formatting.format(qArray.get(0).getQuestionPron());

        questionTextView.setText(fStr); //text of the question

        answer1_button.setText(qArray.get(arrayCounter).getAnswer1()); //buttons
        answer2_button.setText(qArray.get(arrayCounter).getAnswer2());
        answer3_button.setText(qArray.get(arrayCounter).getAnswer3());
        answer4_button.setText(qArray.get(arrayCounter).getAnswer4());

        asking_text_view.setText(qArray.get(arrayCounter).getAsk()); //above the question text of what the question asking

       // gameBoard();








        Log.i("ARRAY DISPLAY", "" + qArray.size() + "elements inside");
        for(int i = 0; i < qArray.size(); i++){
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getQuestionPron());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getAnswer1());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getAnswer2());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getAnswer3());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getAnswer4());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getLevel());
            Log.i("ARRAY DISPLAY", "" + qArray.get(i).getCorrectIs());
        }



        answer1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreBoard[arrayCounter] = 1;
                arrayCounter++;
                quizLengthCounter++;
                updateQuestion();
            }
        });

        answer2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreBoard[arrayCounter] = 2;
                arrayCounter++;
                quizLengthCounter++;
                updateQuestion();
            }
        });

        answer3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreBoard[arrayCounter] = 3;
                arrayCounter++;
                quizLengthCounter++;
                updateQuestion();
            }
        });

        answer4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreBoard[arrayCounter] = 4;
                arrayCounter++;
                quizLengthCounter++;
                updateQuestion();
            }
        });


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


}


//=======================//
/*  without the update
    private void updateQuestion() {

        //To show all the questions of each level
        if(qArray.size() > arrayCounter ){ //because
            fStr = formatting.format(qArray.get(arrayCounter).getQuestionPron());

            questionTextView.scrollTo(0,0); //set scrollbar at the top again
            questionTextView.setText(fStr); //set the question text to the text view

            answer1_button.setText(qArray.get(arrayCounter).getAnswer1());
            answer2_button.setText(qArray.get(arrayCounter).getAnswer2());
            answer3_button.setText(qArray.get(arrayCounter).getAnswer3());
            answer4_button.setText(qArray.get(arrayCounter).getAnswer4());

            //asking_text_view.setText(qArray.get(arrayCounter).getAsk());

            //fStr = "";
            //int  n = rand.nextInt(qArray.size()) + 1;

            Log.i("ARRAY DISPLAY", fStr);
        }
        else{

            scoreLayout();
            //fStr = formatting.format(qArray.get(c).getQuestionPron());
            Log.i("TO", "" + fStr);
            for(int i = 0; i < qArray.size(); i++ ){
                Log.i("GEMISTOS", "" + scoreBoard[i]);
            }
            Log.i("GEMISTOS", "" + scoreBoard.length);
        }

        }
 */

/* with the update

    private void updateQuestion() {

        if(whatLevel == 1){//LEVEL 1
            if(quizLengthCounter < 7){ //tha vazw erwtiseis level 1 mexri 7
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 1 && checkinId(qidTester) == true);

                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else if(quizLengthCounter < 10){ //tha vazw erwtiseis level 2 mexri 10
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 2 && checkinId(qidTester) == true);
                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else{
                scoreLayout();
            }

        }


        else if(whatLevel == 2){ //LEVEL 2
            if(quizLengthCounter < 6){ //tha vazw erwtiseis level 1 mexri 7
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 2 && checkinId(qidTester) == true);

                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else if(quizLengthCounter < 8){
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 1 && checkinId(qidTester) == true);

                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else if(quizLengthCounter < 10){ //tha vazw erwtiseis level 2 mexri 10
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 3 && checkinId(qidTester) == true);
                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else{
                scoreLayout();
            }
        }


        else{//LEVEL 3
            if(quizLengthCounter < 7){ //tha vazw erwtiseis level 3 mexri 7
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 3 && checkinId(qidTester) == true);

                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else if(quizLengthCounter < 10){ //tha vazw erwtiseis level 2 mexri 10
                do{
                    n = rand.nextInt(qArray.size());
                    qidTester = qArray.get(n).getId();
                }while(qArray.get(n).getLevel() == 2 && checkinId(qidTester) == true);
                fStr = formatting.format(qArray.get(n).getQuestionPron());

                questionTextView.scrollTo(0,0); //set scrollbar at the top again
                questionTextView.setText(fStr); //set the question text to the text view

                answer1_button.setText(qArray.get(n).getAnswer1());
                answer2_button.setText(qArray.get(n).getAnswer2());
                answer3_button.setText(qArray.get(n).getAnswer3());
                answer4_button.setText(qArray.get(n).getAnswer4());
            }
            else{
                scoreLayout();
            }
        }





        }

 */