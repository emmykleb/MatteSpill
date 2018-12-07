package com.example.eivindm.mathgame.SqlDatabase;


import java.util.Comparator;

public class ScoreComparer implements Comparator<ScoreContract>
{
    public int compare (ScoreContract left, ScoreContract right){
        if (left.score < right.score) return 1;
        if (left.score > right.score) return -1;
        return 0;
        //return Integer.compare(left.score,right.score);
    }
}