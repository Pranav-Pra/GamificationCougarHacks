package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {
    ArrayList<Profile> leaderboardObject = new ArrayList<>();
    FirebaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        dbHelper = new FirebaseHelper();
       // leaderboardObject = dbHelper.getGroupProfiles();
        leaderboardObject = merge(leaderboardObject);

        TextView text1 = findViewById(R.id.first);
        text1.setText("1\t" + leaderboardObject.get(0).toString());
        TextView text2 = findViewById(R.id.textView2);
        text2.setText("2\t" + leaderboardObject.get(1).toString());
        TextView text3 = findViewById(R.id.textView3);
        text3.setText("3\t" + leaderboardObject.get(2).toString());
        TextView text4 = findViewById(R.id.textView4);
        text4.setText("4\t" + leaderboardObject.get(3).toString());
        TextView text5 = findViewById(R.id.textView5);
        text5.setText("5\t" + leaderboardObject.get(4).toString());
        TextView text6 = findViewById(R.id.textView6);
        text6.setText("6\t" + leaderboardObject.get(5).toString());
        TextView text7 = findViewById(R.id.textView7);
        text7.setText("7\t" + leaderboardObject.get(6).toString());
        TextView text8 = findViewById(R.id.textView8);
        text8.setText("8\t" + leaderboardObject.get(7).toString());
        TextView text9 = findViewById(R.id.textView9);
        text9.setText("9\t" + leaderboardObject.get(8).toString());
        TextView text10 = findViewById(R.id.textView10);
        text10.setText("10\t" + leaderboardObject.get(9).toString());

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
    public void goToHomeScreen(View v) {
        Intent hIntent = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(hIntent);
    }
    public void goToAddTask(View v) {
        Intent atIntent = new Intent(getApplicationContext(), AddTask.class);
        startActivity(atIntent);

    }
}