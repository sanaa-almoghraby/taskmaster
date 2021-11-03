package com.example.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Task> allTask = AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();


        ArrayList<Task> tasksList = new ArrayList<Task>();
        tasksList.add(new Task("task1" , "This the Lab" , "assigned"));
        tasksList.add(new Task("task2" , "This the Challenge" , "complete"));
        tasksList.add(new Task("task3" , "This the Reading" , "new"));

        for (Task task:allTask) {
            tasksList.add(task);
            
        }
        
        RecyclerView tasksListRecyclerView = findViewById(R.id.recyclerView);
        tasksListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksListRecyclerView.setAdapter(new TaskAdapter(allTask));


        Button allTaskButton = findViewById(R.id.button2);

        allTaskButton.setOnClickListener(V -> {
            Intent activity2Intent = new Intent(MainActivity.this, AllTasks.class);
            startActivity(activity2Intent);
        });
        Button addTaskButton = findViewById(R.id.button);
        addTaskButton.setOnClickListener(V -> {
            Intent activity2Intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(activity2Intent);
        });

        Button goSettingButton = findViewById(R.id.setting);
        goSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent goSetting = new Intent(MainActivity.this, Settings.class);
                startActivity(goSetting);
            }
        });
        Button task1 = findViewById(R.id.Task1);
        task1.setOnClickListener((view -> {
            String taskTitle = task1.getText().toString();
            Intent goToTaskDetail = new Intent(MainActivity.this , Task_Detail.class);
            goToTaskDetail.putExtra("taskName", taskTitle);
            startActivity(goToTaskDetail);
        }));
        Button task2 = findViewById(R.id.Task2);
        task2.setOnClickListener((view -> {
            String taskTitle = task2.getText().toString();
            Intent goToTaskDetail = new Intent(MainActivity.this , Task_Detail.class);
            goToTaskDetail.putExtra("taskName", taskTitle);
            startActivity(goToTaskDetail);
        }));
        Button task3 = findViewById(R.id.Task3);
        task3.setOnClickListener((view -> {
            String taskTitle = task3.getText().toString();
            Intent taskDetail = new Intent(MainActivity.this , Task_Detail.class);
            taskDetail.putExtra("taskName", taskTitle);
            startActivity(taskDetail);
        }));
    }
    @Override
    protected void onResume() {
        super.onResume();
        String usernameTasks = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPreferences.getString("username", "Your");
        TextView userTasks = findViewById(R.id.textView10);
        userTasks.setText(username+usernameTasks);
    }
}

