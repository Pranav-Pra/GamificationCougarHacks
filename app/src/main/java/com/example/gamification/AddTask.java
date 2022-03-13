package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
    public void goToHomeScreen(View v) {
        Intent hIntent = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(hIntent);

    }
    public void goToLeaderboard(View v) {
        Intent lIntent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(lIntent);

    }
}