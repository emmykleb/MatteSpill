package com.example.eivindm.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import com.example.eivindm.mathgame.SqlDatabase.*;

public class LoginActivity extends AppCompatActivity {


    //UserDbHelper myDB;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtFirstName;
    private EditText txtLastName;

    private StringBuilder builder = new StringBuilder();
    private TextView userTxtView;

    UserContract user;


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("UserContract");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);



        final UserDbHelper userDb = new UserDbHelper(getApplicationContext());

        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                user = userDb.getUser(username,password);

                // List<ScoreContract> scores =  userDb.getTopScores();


                if (user != null)
                {
                    Intent index = new Intent(getApplication(), IndexScreen.class);
                    index.putExtra("UserContract",  user);
                    startActivity(index);
                }
                else{
                    Toast.makeText(getApplicationContext(),"User not recognised",10).show();
                }


            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();

                String firstrname = txtFirstName.getText().toString();
                String lastrname = txtLastName.getText().toString();

                //userTxtView.setText(builder.toString());
                if (userDb.addNewUser(username,password, firstrname, lastrname))
                {
                    Toast.makeText(getApplicationContext(),"User added successully",10).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"User probably already exists",10).show();
                }

            }
        });



    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable("UserContract", user);
        super.onSaveInstanceState(outState);

    }





}





