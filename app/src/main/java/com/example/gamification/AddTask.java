package com.example.gamification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }
    public void goToHomeScreen(View v) {
        setContentView(R.layout.activity_home_screen);
    }
    public void goToLeaderboard(View v) {
        setContentView(R.layout.activity_leaderboard);
    }
}