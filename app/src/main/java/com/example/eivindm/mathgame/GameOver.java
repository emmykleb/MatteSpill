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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
       UserDbHelper userDb ;








        Button home = (Button) findViewById(R.id.homeBtn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIndex = new Intent(getApplication(),IndexScreen.class);

                startActivity(launchIndex);

            }
    });



}}