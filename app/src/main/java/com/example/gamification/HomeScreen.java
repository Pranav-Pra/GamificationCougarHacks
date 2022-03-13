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
    public void signOut(View v) {
        mAuth.getInstance().signOut();

        Intent welcomeIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(welcomeIntent);

    }
}