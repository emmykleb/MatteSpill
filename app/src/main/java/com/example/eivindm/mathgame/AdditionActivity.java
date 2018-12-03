package com.example.eivindm.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eivindm.mathgame.SqlDatabase.UserContract;
import com.example.eivindm.mathgame.SqlDatabase.UserDbHelper;

import java.util.List;
import java.util.Random;

public class AdditionActivity extends AppCompatActivity {

    //final UserDbHelper userDb = new UserDbHelper(getApplicationContext());

    int number1;
    int number2;
    private int score;
    private int questionNum;
   /* enum Operation
    {
        PLUS(0),
        MINUS(1),
        MULT(2),
        DIV(3);
        private final int value;

        Operation(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }

    Operation op;

*/

    private void GenerateRandomQuestion()
    {
        Random r = new Random();
        number1 = r.nextInt(10) + 3;
        number2 = r.nextInt(10) + 3;
        //op = Operation(r.nextInt(3 ));

        TextView txtNumber1 = (TextView)findViewById(R.id.txtNumber1);
        TextView txtNumber2 = (TextView)findViewById(R.id.txtNumber2);
        TextView txtOp = (TextView)findViewById(R.id.txtOp);

        txtNumber1.setText(Integer.toString(number1));
        txtNumber2.setText(Integer.toString(number2));
        txtOp.setText("+");



    }


    private int GetCorrectAnswer(){
        TextView txtNumber1 = (TextView)findViewById(R.id.txtNumber1);
        TextView txtNumber2 = (TextView)findViewById(R.id.txtNumber2);

        int res = -1;
        int num1 = Integer.parseInt(txtNumber1.getText().toString());
        int num2 = Integer.parseInt(txtNumber2.getText().toString());

        res = num1 + num2;
        return res;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addition_layout);
        GenerateRandomQuestion();

        Button fab = (Button) findViewById(R.id.btnOK);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent launchChatScreen = new Intent(getApplication(),MainActivity.class);
                //startActivity(launchChatScreen);
                Log.d("Chat Messages ","CHeck 2");

                EditText txtAnswer = (EditText)findViewById(R.id.txtAnswer);
                TextView txtStatus = (TextView) findViewById(R.id.txtStatus);

                txtStatus.setText("");

                String answer = txtAnswer.getText().toString();
                if (answer.equals("")){
                    txtStatus.setText("Answer is empty");
                }
                else{
                    int res = GetCorrectAnswer();
                    int iAnswer = 0;
                    try {
                        iAnswer = Integer.parseInt(txtAnswer.getText().toString());
                    }
                    catch(Exception e)
                    {
                        txtStatus.setText("Stupid!");
                    }

                    if (res == iAnswer){

                        GenerateRandomQuestion();
                        questionNum = questionNum+1;
                        score = score +5;
                        TextView txtScore = (TextView)findViewById(R.id.txtScore);
                        txtScore.setText(Integer.toString(questionNum) + " of 5");
                        txtAnswer.setText("");
                        if(questionNum == 5){


                            //String scoreresult = String.valueOf(score);

                            //userDb.addNewScore(scoreresult);

                            Intent launchGameOver = new Intent(getApplication(),GameOver.class);

                            startActivity(launchGameOver);

                        }


                    }
                    else {

                        txtStatus.setText("");
                        questionNum = questionNum + 1;

                        score = score -2;

                    }
                }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();


            }
        });
    }
}
