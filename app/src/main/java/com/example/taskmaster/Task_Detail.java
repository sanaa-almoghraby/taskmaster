package com.example.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Task_Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
//        String taskName = intent.getExtras().getString("taskName");
//        TextView taskTitle = findViewById(R.id.textView7);
//        taskTitle.setText(taskName);
        String title=intent.getExtras().getString("taskName");
        String body=intent.getExtras().getString("taskBody");
        String state=intent.getExtras().getString("taskState");

        TextView textView =findViewById(R.id.title);
        TextView bodyView =findViewById(R.id.body);
        TextView stateView =findViewById(R.id.state);

        textView.setText("Task Title:  "+title);
        bodyView.setText("Task Body:  "+body);
        stateView.setText("Task State:  "+state);

    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = getIntent();
//        String taskName = intent.getExtras().getString("taskName");
//        TextView taskTitle = findViewById(R.id.textView7);
//        taskTitle.setText(taskName);
//    }
}