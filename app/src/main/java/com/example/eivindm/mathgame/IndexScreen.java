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
import com.example.eivindm.mathgame.SqlDatabase.*;

public class IndexScreen extends AppCompatActivity {

    private UserContract user;

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

        user = (UserContract) getIntent().getSerializableExtra("UserContract");
        final UserDbHelper userDb = new UserDbHelper(getApplicationContext());
        //userDb.write("fsd", "fdsfs");
        //UserContract user = userDb.getUser("eivind", "1234");


        //userTxtView = findViewById(R.id.userTxtView);

        Button startBtn = (Button) findViewById(R.id.startGameBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchGame = new Intent(getApplication(), MainActivity.class);
                launchGame.putExtra("UserContract",  user);
                startActivity(launchGame);


            }
        });



        Button userLiBtn = (Button) findViewById(R.id.userListBtn);

        userLiBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {



                    Intent viewUsers = new Intent(getApplication(), userListActivity.class);
                    startActivity(viewUsers);

                }

        });


    }


}





