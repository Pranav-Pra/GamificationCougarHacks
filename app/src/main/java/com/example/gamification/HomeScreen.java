package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {
    private TextView pointsorJoinCodeTV;
    private FirebaseAuth mAuth;
    String name;
    String level;
    String code;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);



        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        level = intent.getStringExtra("level");
        code = intent.getStringExtra("code");
        if(level.equals("Employee")) {
            points = intent.getIntExtra("points", 0);
        }

        pointsorJoinCodeTV = findViewById(R.id.pointsOrJoinCode);
        if(level.equals("Boss")) {
            pointsorJoinCodeTV.setText("Join Code: " + code);
        } else if(level.equals("Employee")) {
            pointsorJoinCodeTV.setText("Points: " + points);
        }
    }
    public void signOut(View v) {
        mAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }
    public void goToTaskScreen(View v) {
        if(level.equals("Boss")) {
            Intent atIntent = new Intent(getApplicationContext(), AddTask.class);
            startActivity(atIntent);

        }
        else if(level.equals("Employee")) {
            Intent vtIntent = new Intent(getApplicationContext(), ViewTask.class);
            startActivity(vtIntent);

        }
    }
    public void goToLeaderboard(View v) {
        Intent lIntent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(lIntent);

    }
}