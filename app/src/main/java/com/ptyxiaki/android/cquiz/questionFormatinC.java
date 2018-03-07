package com.ptyxiaki.android.cquiz;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by billgemistos on 16/10/2016.

 This class created to format the question to the right way. To show the question the right way to the screen
 */

public class questionFormatinC {
    private String gived = "";
    private String finalString;
    private char ClosingBr = '}';
    private char OpeningBr = '{';
    private char semicolon =';';
    private char closingLib ='>';
    private int counter;
    private String result = "";

    ArrayList <Character> finaleStr = new ArrayList<Character>();

    public questionFormatinC(){
       // finaleStr.clear();
    }


//examins where to leave a tab and where to add a line based on C programming language
    public String format(String givenQuestion){
        finaleStr.clear();
        this.gived = givenQuestion;
        counter = 0;
        int first_time = 1;
        for(int i=0; i<gived.length(); i++){
            if(gived.charAt(i) == OpeningBr){
                finaleStr.add('\n');
                if(counter != 0){
                    for(int j=0; j < counter; j++){
                        finaleStr.add('\b');
                        finaleStr.add('\b');
                        Log.i("oct", "read open bracket");
                    }
                    finaleStr.add(gived.charAt(i));

                    finaleStr.add('\n');
                    finaleStr.add('\b');
                    finaleStr.add('\b');
                    Log.i("oct", "open br counter = " + counter);
                    for(int j=0; j < counter; j++){
                        finaleStr.add('\b');
                        finaleStr.add('\b');
                        Log.i("oct", "read open bracket");
                    }
                    counter++;
                }
                else{
                    finaleStr.add(gived.charAt(i));
                    finaleStr.add('\n');
                    finaleStr.add('\b');
                    finaleStr.add('\b');
                    Log.i("oct", "open br counter = " + counter);
                    for(int j=0; j < counter; j++){
                        finaleStr.add('\b');
                        finaleStr.add('\b');
                        Log.i("oct", "read open bracket");
                    }
                    counter++;
                }

            }
            else if (gived.charAt(i) == '$'){ //to adding correctly the for loop
                finaleStr.add(';');
            }
            else if (gived.charAt(i) == '@'){
                finaleStr.add('{');
            }
            else if (gived.charAt(i) == '#'){
                finaleStr.add('}');
            }
            else if (gived.charAt(i) == semicolon){
                    finaleStr.add(gived.charAt(i));
                Log.i("oct", "given string" + gived);
                    if(gived.charAt(i+1) != ClosingBr) {
                       finaleStr.add('\n');
                    }

                    Log.i("oct", "Semi counter = " + counter);

                    for(int j=0; j < counter; j++){
                        finaleStr.add('\b');
                        finaleStr.add('\b');
                        Log.i("oct", "semicolon");
                    }

            }
            else if ( gived.charAt(i) == ClosingBr ){

                if(counter!=0){
                    counter--;
                }
                finaleStr.add('\n');
                Log.i("oct", "clos br Counter = " + counter);
                for(int j=0; j < counter; j++){
                    finaleStr.add('\b');
                    finaleStr.add('\b');
                    Log.i("oct", "read close bracket");
                }
                finaleStr.add(gived.charAt(i));
                finaleStr.add('\n');


                for(int j=0; j < counter; j++){
                    finaleStr.add('\b');
                    finaleStr.add('\b');
                    Log.i("oct", "read close bracket");
                }
            }
           // else if(gived.charAt(i) == closingLib ){
            //    finaleStr.add(gived.charAt(i));
             //   finaleStr.add('\n');
            //}
            else {
                finaleStr.add(gived.charAt(i));
            }
        }
        //result = arrToString();
        result = myThing();
        //finaleStr.clear();
        return result;
    }


//Returns the final String with the right form of the question.
    public String arrToString(){

        StringBuilder sb = new StringBuilder();

        for(Character s : finaleStr){
            sb.append(s);
        }
        finalString = sb.toString();

        return finalString;
    }


    public String myThing(){
        finalString = "";

        for(int i = 0; i<finaleStr.size(); i++){
            finalString = finalString + finaleStr.get(i);
        }

        return finalString;
    }

}

