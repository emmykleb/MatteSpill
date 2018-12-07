package com.example.eivindm.mathgame;

import android.arch.persistence.room.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eivindm.mathgame.SqlDatabase.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "my_db";



   //boolean timeout = false;


    int number1;
    int number2;
    private int questionNum = 1 ;
    private int score;

    private UserContract user;

    enum Operation
    {
        PLUS('+'),
        MINUS('-'),
        MULT('x');

        private final int value;

        Operation(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }

    Operation op;

    private void GenerateRandomQuestion()
    {
        Random r = new Random();
        number1 = r.nextInt(10) + 3;
        number2 = r.nextInt(6) + 2;
        //op = Operation(r.nextInt(3 ));

        TextView txtNumber1 = (TextView)findViewById(R.id.txtNumber1);
        TextView txtNumber2 = (TextView)findViewById(R.id.txtNumber2);
        TextView txtOp = (TextView)findViewById(R.id.txtOp);

        txtNumber1.setText(Integer.toString(number1));
        txtNumber2.setText(Integer.toString(number2));

        char opc = (char)op.getValue();
        txtOp.setText(Character.toString(opc));

    }

    private void SetOpertion(){

        Random operatorChoice = new Random();

        int opvalue  = operatorChoice.nextInt(3);

        switch(opvalue){
            case 0: op = Operation.PLUS;
            break;
            case 1: op = Operation.MINUS;
                break;
            case 2: op = Operation.MULT;
                break;

        }
        /*
        if (questionNum <= 3){
            op = Operation.PLUS;

        }
        else if (questionNum <= 6){
            op = Operation.MINUS;

        }
        else if (questionNum <= 9){
            op = Operation.MULT;

        }
*/
    }





    private boolean isGameOver()
    {
        return questionNum >= 30;
    }
    private int GetCorrectAnswer(){
        TextView txtNumber1 = (TextView)findViewById(R.id.txtNumber1);
        TextView txtNumber2 = (TextView)findViewById(R.id.txtNumber2);

        int res = -1;
        int num1 = Integer.parseInt(txtNumber1.getText().toString());
        int num2 = Integer.parseInt(txtNumber2.getText().toString());

        char mathOp = (char)op.getValue();
        String operator = Character.toString(mathOp);

        if (operator.equals("+") )
        {
            res = num1 + num2;

        }else if(operator.equals("-") ){

            res = num1 - num2;

        }else if(operator.equals("x") ){

            res = num1 * num2;

        }

        return res;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        final UserDbHelper userDb = new UserDbHelper(getApplicationContext());
        user = (UserContract) getIntent().getSerializableExtra("UserContract");
        final TextView countDownTxt = (TextView) findViewById(R.id.countDownTxt);
        final Button contBtn = findViewById(R.id.continueBtn);
        final Button fab = (Button) findViewById(R.id.btnOK);
        contBtn.setEnabled(false);

        contBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String timeStamp = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime());

                if (!userDb.addNewScore(user, score,timeStamp))
                {

                    Toast.makeText(getApplicationContext(),"Failed to save the score in DB",20).show();

                }


                Intent launchGameOver = new Intent(getApplication(), GameOver.class);
               launchGameOver.putExtra("Score", score);
                launchGameOver.putExtra("UserContract", user);
                startActivity(launchGameOver);


            }
        });


        new CountDownTimer(30000, 1000) {


            public void onTick(long millisUntilFinished) {


                countDownTxt.setText("Seconds remaining: " + millisUntilFinished / 1000);


            }

            public void onFinish() {
                countDownTxt.setText("Time Out");
                fab.setEnabled(false);
                contBtn.setEnabled(true);

            }
        }.start();



       // TextView qNumText = (TextView) findViewById(R.id.qNumTxt);
        //qNumText.setText("Question " + questionNum + " of 15");
        TextView txtScore = (TextView)findViewById(R.id.txtScore);
        txtScore.setText(Integer.toString(score));

      /*  user = (UserContract) getIntent().getSerializableExtra("UserContract");
        if (savedInstanceState != null && user == null ){
            user =  (UserContract) savedInstanceState.getSerializable("UserContract");
        }*/



        SetOpertion();
        GenerateRandomQuestion();



        //database =  Room.databaseBuilder(this.getApplicationContext(), MyDatabase.class,DATABASE_NAME).build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                       txtStatus.setText("");

                        questionNum = questionNum+1;
                        score = score +5;
                        TextView txtScore = (TextView)findViewById(R.id.txtScore);
                        txtScore.setText(Integer.toString(score));
                        txtAnswer.setText("");
                        //TextView qNumText = (TextView) findViewById(R.id.qNumTxt);
                        //qNumText.setText("Question " + questionNum + " of 15");
                        Toast.makeText(getApplicationContext(),"CORRECT! +5 points!",10).show();

                    }
                    else {
                     txtStatus.setText("");
                        //questionNum = questionNum + 1;

                        score = score -2;
                        TextView txtScore = (TextView)findViewById(R.id.txtScore);
                        txtScore.setText(Integer.toString(score));
                        txtAnswer.setText("");
                        Toast.makeText(getApplicationContext(),"Wrong, -2 points!",10).show();
                    }
                   /* if(isGameOver()){

                        //List<ScoreContract> scores = userDb.getAllScores();
                        //userDb.addNewScore(user,score);
                        Intent launchGameOver = new Intent(getApplication(),GameOver.class);

                        launchGameOver.putExtra("Score", score);
                        String timeStamp = new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime());
                        userDb.addNewScore(user, score, timeStamp);
                        startActivity(launchGameOver);

                    }*/
                    SetOpertion();
                    GenerateRandomQuestion();
                }

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();


            }





        });




    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable("UserContract", user);
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("UserContract");


    }

}
