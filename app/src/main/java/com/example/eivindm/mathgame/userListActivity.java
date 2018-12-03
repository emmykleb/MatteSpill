package com.example.eivindm.mathgame;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eivindm.mathgame.SqlDatabase.*;

import java.util.*;

public class userListActivity extends AppCompatActivity {

    UserDbHelper userDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

       userDb = new UserDbHelper(getApplicationContext());
      // Bundle userBundle = getIntent().getExtras().getBundle("userBundle");

      // ArrayList<String> userList = userBundle.getStringArrayList("userList");
      //  ArrayList<String> userList = new ArrayList<>();

        List<UserContract> users =  userDb.getUserList();
        List<ScoreContract> scores =  userDb.getTopScores();

        ArrayList<String> names = new ArrayList<String>();

        for (ScoreContract sc : scores){

            String fmt = String.format("%10s %10s", sc.user.firstName, sc.score);
            names.add(fmt);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        ListView listView = findViewById(R.id.userListView);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
