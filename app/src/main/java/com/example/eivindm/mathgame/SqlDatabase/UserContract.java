package com.example.eivindm.mathgame.SqlDatabase;

import android.database.Cursor;
import android.database.sqlite.*;
import android.provider.*;
import android.database.*;

import java.io.Serializable;


public class UserContract  implements Serializable {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserContract() {}

    /* Inner class that defines the table contents */
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FIRSTNAME = "first_name";
        public static final String COLUMN_LASTNAME = "last_name";
    }

    public long id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;

    public UserContract(long id, String username, String password, String firstName, String lastName){
        this.id = id;
        this.username = username;
        this.password = password;

        this.firstName = firstName;
        this.lastName = lastName;
    }


}