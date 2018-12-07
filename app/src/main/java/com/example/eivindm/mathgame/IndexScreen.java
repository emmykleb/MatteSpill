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

import java.util.ArrayList;
import java.util.List;

import com.example.eivindm.mathgame.SqlDatabase.*;

public class IndexScreen extends AppCompatActivity {


    UserContract user;
    // final UserDbHelper userDb = new UserDbHelper(getApplicationContext());
    //UserDbHelper myDB;
    private EditText userNameInput;
    private EditText passwordInput;
    private ArrayList<String> userList = new ArrayList<>();
    private StringBuilder builder = new StringBuilder();
    private TextView userTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_screen);


        final UserDbHelper userDb = new UserDbHelper(getApplicationContext());

        user = (UserContract) getIntent().getSerializableExtra("UserContract");
        // String loggedInUser = userDb.getUserById(0).firstName.toString();
        if (savedInstanceState != null && user == null) {
            user = (UserContract) savedInstanceState.getSerializable("UserContract");
        }


        Button startBtn = (Button) findViewById(R.id.startGameBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchGame = new Intent(getApplication(), MainActivity.class);
                launchGame.putExtra("UserContract", user);
                startActivity(launchGame);


            }
        });


        Button userLiBtn = (Button) findViewById(R.id.userListBtn);

        userLiBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                Intent viewUsers = new Intent(getApplication(), userListActivity.class);
                viewUsers.putExtra("UserContract", user);
                startActivity(viewUsers);

            }

        });






        }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("UserContract", user);
        super.onSaveInstanceState(outState);


    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("UserContract");


    }

}



