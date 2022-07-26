package com.aldimas.stemgitar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tutorial);
    }
}