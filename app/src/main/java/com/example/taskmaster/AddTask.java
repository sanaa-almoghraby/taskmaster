package com.example.taskmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button addTaskButton = findViewById(R.id.button3);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View V) {
                Toast.makeText(getApplicationContext(),
                        "submitted!", Toast.LENGTH_SHORT).show(); } });
    }
}