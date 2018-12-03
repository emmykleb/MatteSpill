package com.example.eivindm.mathgame.SqlDatabase;

import android.provider.BaseColumns;
import java.io.Serializable;

public class ScoreContract implements Serializable{
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ScoreContract() {}

    /* Inner class that defines the table contents */
    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "score";

        public static final String COLUMN_USERID = "user_id";
        public static final String COLUMN_SCORE = "score";
        //public static final String COLUMN_TIME = "attempt_time";
       // public static final String COLUMN_DURATION = "duration";
    }

    public long id;
    public int userid;
    public int score;
    public UserContract user;

    public ScoreContract(long id, int userid, int score){
        this.id = id;
        this.userid = userid;
        this.score = score;
    }
}
