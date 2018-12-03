package com.example.eivindm.mathgame.SqlDatabase;

import android.database.sqlite.*;
import android.util.*;
import android.widget.*;
import android.content.*;
import android.database.*;

import java.util.*;

import static com.example.eivindm.mathgame.SqlDatabase.UserContract.UserEntry.COLUMN_NAME;


public class UserDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "User.db";
    //String YOUR_QUERY = "SELECT * FROM TABLE_NAME";


    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE IF NOT EXISTS " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_PASSWORD + " TEXT," +
                    UserContract.UserEntry.COLUMN_FIRSTNAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_LASTNAME + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_SCORE =
            "CREATE TABLE IF NOT EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME + " (" +
                    ScoreContract.ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreContract.ScoreEntry.COLUMN_USERID + " TEXT," +
                    ScoreContract.ScoreEntry.COLUMN_SCORE + " TEXT," +
                    //ScoreContract.ScoreEntry.COLUMN_DURATION + " TEXT," +
                    "FOREIGN KEY (" + ScoreContract.ScoreEntry.COLUMN_USERID + ") REFERENCES " + UserContract.UserEntry.TABLE_NAME + " (" + UserContract.UserEntry._ID + "))";



    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME + ";";

    private static final String SQL_DELETE_ENTRIES_SCORE =
            "DROP TABLE IF EXISTS " + ScoreContract.ScoreEntry.TABLE_NAME + ";";


    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_SCORE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_SCORE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addNewUser(String name, String password, String firstName, String lastName) {// Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME, name.toString());
        values.put(UserContract.UserEntry.COLUMN_PASSWORD, password.toString());

        values.put(UserContract.UserEntry.COLUMN_FIRSTNAME, firstName.toString());
        values.put(UserContract.UserEntry.COLUMN_LASTNAME, lastName.toString());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);
        if (newRowId > 0)
            return true;
        else
            return false;
    }
    public boolean addNewScore(UserContract user, int score) {// Gets the data repository in write mode

        try {
            SQLiteDatabase db = getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(ScoreContract.ScoreEntry.COLUMN_USERID, user.id);
            values.put(ScoreContract.ScoreEntry.COLUMN_SCORE, score);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
            if (newRowId > 0)
                return true;
            else
                return false;
        }
        catch(Exception ex){
            return false;
        }
    }

    public List<ScoreContract> getAllScores() {

        try {
            SQLiteDatabase db = getReadableDatabase();
            String q = "SELECT * FROM score";
            Cursor cursor = db.rawQuery(q, null);

            ArrayList<ScoreContract> scores = new ArrayList<ScoreContract>();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry._ID));
                int user = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_USERID));
                int scoreresult = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_SCORE));

                ScoreContract score = new ScoreContract(id, user, scoreresult);
                scores.add(score);
            }

            return scores;
        }
        catch(Exception ex){
            return null;
        }
    }

    public UserContract getUserById(int userId)
    {
        try{
            SQLiteDatabase db = getReadableDatabase();

            String[] projection = {
                    UserContract.UserEntry._ID,
                    COLUMN_NAME,
                    UserContract.UserEntry.COLUMN_FIRSTNAME,
                    UserContract.UserEntry.COLUMN_LASTNAME,
            };

            String selection = UserContract.UserEntry._ID + " = ?";
            String[] selectionArgs = {Integer.toString(userId)};

            Cursor cursor = db.query(
                    UserContract.UserEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID));
                String un = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME));
                //String pswd = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD));
                String fn = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_FIRSTNAME));
                String ln = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_LASTNAME));

                return new UserContract(id, un, "",fn,ln);

            }
            return null;
        }
        catch(Exception e) {
            return null;
        }
    }
    public List<ScoreContract> getTopScores() {

        try {
            SQLiteDatabase db = getReadableDatabase();
            String q = "SELECT score.* FROM score  ORDER BY score.score DESC";
            Cursor cursor = db.rawQuery(q, null);

            ArrayList<ScoreContract> scores = new ArrayList<ScoreContract>();

            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry._ID));
                int userid = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_USERID));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(ScoreContract.ScoreEntry.COLUMN_SCORE));

                ScoreContract sc = new ScoreContract (id, userid, score);

                UserContract user = getUserById(userid);
                sc.user = user;

                scores.add(sc);
            }

            return scores;
        }
        catch(Exception ex){
            return null;
        }
    }

    public ArrayList<UserContract> getUserList(){
        SQLiteDatabase thisdb = getReadableDatabase();

        String q = "SELECT * FROM user";

        Cursor cursor = thisdb.rawQuery(q,null);

        ArrayList<UserContract> users = new ArrayList<UserContract>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID));
            String pswd = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME));

            UserContract new_user = new UserContract(id, username,pswd,"","");
            users.add(new_user);

        }
        cursor.close();
        return users;
    }

    public UserContract getUser(String username, String password) {// Gets the data repository in write mode
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry._ID,
                COLUMN_NAME,
                UserContract.UserEntry.COLUMN_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {username};

// How you want the results sorted in the resulting Cursor
        //String sortOrder =  FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserContract.UserEntry._ID));
            String pswd = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_PASSWORD));

            if (pswd.equals(password))
            {
                return new UserContract(id, username, password,"","");
            }
            return null;
        }
        return null;
    }



}