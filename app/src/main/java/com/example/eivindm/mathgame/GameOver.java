package com.example.eivindm.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eivindm.mathgame.SqlDatabase.ScoreContract;
import com.example.eivindm.mathgame.SqlDatabase.UserContract;
import com.example.eivindm.mathgame.SqlDatabase.UserDbHelper;

import java.util.ArrayList;
import java.util.List;

public class GameOver extends AppCompatActivity {



    UserDbHelper userDb;
    private UserContract user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        TextView scoreview = findViewById(R.id.scoreView);

        userDb = new UserDbHelper(getApplicationContext());
        user = (UserContract) getIntent().getSerializableExtra("UserContract");
        Intent intent = getIntent();

        int score = intent.getIntExtra("Score",0);


        scoreview.setText("Score: " + score);


        Button home = (Button) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent launchIndex = new Intent(getApplication(),IndexScreen.class);
                launchIndex.getSerializableExtra("UserContract");
                launchIndex.putExtra("UserContract", user);
                startActivity(launchIndex);

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