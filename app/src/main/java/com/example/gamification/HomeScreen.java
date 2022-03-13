package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    private TextView pointsorJoinCodeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String name, level, code, points = "";

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        level = intent.getStringExtra("level");
        code = intent.getStringExtra("code");
        if(level.equals("Employee")) {
            points = intent.getStringExtra("points");
        }

        pointsorJoinCodeTV = findViewById(R.id.pointsOrJoinCode);
        if(level.equals("Boss")) {
            pointsorJoinCodeTV.setText("Join Code: " + code);
        } else if(level.equals("Employee")) {
            pointsorJoinCodeTV.setText("Points: " + points);
        }
    }
}