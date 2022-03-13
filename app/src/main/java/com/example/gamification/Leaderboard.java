package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    ArrayList<Profile> leaderboardObject = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
    }

    private static ArrayList mergeSort(ArrayList<Profile> left, ArrayList<Profile> right, ArrayList<Profile> whole) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ( (left.get(leftIndex).getPoints() > right.get(rightIndex).getPoints() )) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex) );
                rightIndex++;
            }
            wholeIndex++;
        }

        ArrayList<Profile> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            rest = right;
            restIndex = rightIndex;
        } else {
            rest = left;
            restIndex = leftIndex;
        }

        for (int i=restIndex; i<rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i) );
            wholeIndex++;
        }

        return whole;
    }

    public static ArrayList<Profile> merge(ArrayList<Profile> a)
    {
        int total = a.size();
        if(total < 2)
        {
            return a;
        }
        else
        {
            int mid = a.size() / 2;
            ArrayList<Profile> left = new ArrayList<>();
            ArrayList<Profile> right = new ArrayList<>();

            for(int i = 0; i < mid; i++)
            {
                left.add(a.get(i));
            }
            for(int i = mid; i < total; i ++)
            {
                right.add(a.get(i));
            }

            left = merge(left);
            right = merge(right);

            return mergeSort(left, right, a);
        }
    }
}