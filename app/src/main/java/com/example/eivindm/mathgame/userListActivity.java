package com.example.eivindm.mathgame;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eivindm.mathgame.SqlDatabase.*;

import java.util.*;

public class userListActivity extends AppCompatActivity {

    UserDbHelper userDb ;
   private UserContract user;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("UserContract");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);


       userDb = new UserDbHelper(getApplicationContext());
       //user = userDb.getUserById(1);
        user = (UserContract) getIntent().getSerializableExtra("UserContract");


        //List<UserContract> users =  userDb.getUserList();
        List<ScoreContract> scores =  userDb.getTopScores();

        ArrayList<String> names = new ArrayList<String>();

        for (ScoreContract sc : scores){

            String fmt = String.format("%10s %10s %30s" , sc.user.firstName, sc.score, sc.timestamp);
            names.add(fmt);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        ListView listView = findViewById(R.id.userListView);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        Button home = (Button) findViewById(R.id.homeBtnGameOver);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable("UserContract", user);
        super.onSaveInstanceState(outState);

    }



}



