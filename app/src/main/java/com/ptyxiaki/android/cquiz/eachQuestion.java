package com.ptyxiaki.android.cquiz;

/**
 * Created by billgemistos on 25/10/2016.
 */

public class eachQuestion {
    int id;
    String questionPron;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    int level;
    int correctIs;
    String ask;



    public eachQuestion(String questionPron, String answer1, String answer2, String answer3, String answer4, int level, int correctIs, String ask) {
        this.id = id;
        this.questionPron = questionPron;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.level = level;
        this.correctIs = correctIs;
        this.ask = ask;

    }

    public eachQuestion() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionPron() {
        return questionPron;
    }

    public void setQuestionPron(String questionPron) {
        this.questionPron = questionPron;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCorrectIs() {
        return correctIs;
    }

    public void setCorrectIs(int correctIs) {
        this.correctIs = correctIs;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String asking) {
        this.ask = asking;
    }

    public String show(int corrIndex){
        if(corrIndex == 1){
            return answer1;
        }else if(corrIndex == 2){
            return answer2;
        }else if(corrIndex == 3){
            return answer3;
        }else{
            return answer4;
        }
    }




}
