package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    private TaskAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String enteredName = sharedPreferences.getString("EnteredText","Write the name");

        String Team = sharedPreferences.getString("Team","noTeam");

        TextView personTasks = findViewById(R.id.textView10);
        personTasks.setText(enteredName + "'s Tasks");

        configureAmplify();
        creatTeams();


        List <Task> tasks=new ArrayList<Task>();

// farh       SharedPreferences sharedPreferences2 = getSharedPreferences("MyPref", 0);
//    farh    String settingsTeamID = sharedPreferences2.getString("Team",null);

        RecyclerView tasksListRecyclerView = findViewById(R.id.recyclerView);


//        if (settingsTeamID == null){
//            tasks=GetData(tasksListRecyclerView);
//
//        }else if(settingsTeamID=="1") {
//
//            tasks=GetData2(tasksListRecyclerView,settingsTeamID);
//
//        }
        if(Team.equals("noTeam")){
            tasks = GetData(tasksListRecyclerView);
            System.out.println("*****************************"+tasks);
        }
        else{
            tasks = GetData2(tasksListRecyclerView);
            System.out.println("*****************************"+tasks);
        }

        Log.i("BLAAAAAAAA",tasks.toString());
        tasksListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksListRecyclerView.setAdapter(new TaskAdapter(tasks));
//        Log.i("TeamID", "Settings Team ID ===> " + settingsTeamID);







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
        SharedPreferences.Editor editor = sharedPreferences.edit();

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
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "Override onStart()", Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String enteredName = sharedPreferences.getString("EnteredText","Write the name");

        String Team = sharedPreferences.getString("Team","noTeam");

        SharedPreferences.Editor editor = sharedPreferences.edit();

        TextView personTasks = findViewById(R.id.textView10);
        personTasks.setText(enteredName + "'s Tasks");
    }
    private void configureAmplify() {
        try {

            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Main", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Main", "Could not initialize Amplify", error);
        }}

    private  List<Task> GetData( RecyclerView allTasks ) {
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTasks.getAdapter().notifyDataSetChanged();

                return false;
            }
        });
        List<Task> foundTask=new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Task.class),
                response -> {
                    for (Task todo : response.getData()) {
                        foundTask.add(todo);
                        foundTask.toString();
                        Log.i("MyAmplifyApp", foundTask.toString());
                        Log.i("MyAmplifyApp", "Successful query, found posts.");
                    }
                    System.out.println("/////////////////////////////////"+foundTask);
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        return  foundTask;
    }

    private List<Task> GetData2(RecyclerView allTaskDataRecyclerView){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String Team = sharedPreferences.getString("Team","noTeam");
        System.out.println("-------------------------------------------------------------------");
        System.out.println(Team);
        Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTaskDataRecyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        List<Task> foundTask=new ArrayList<>();
        Amplify.API.query(
                ModelQuery.list(Task.class,Task.TEAM_ID.contains(Team)),

                response -> {
                    for (Task todo : response.getData()) {
                        foundTask.add(todo);
                        foundTask.toString();
                        Log.i("MyAmplifyApp", foundTask.toString());
                        Log.i("MyAmplifyApp", "Successful query, found posts.");
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        System.out.println(foundTask+"//////////////////////////***");

        return  foundTask;
//        Amplify.API.query(
//                ModelQuery.list(Task.class, Task.TEAM_ID.contains(Team)),
//                response -> {
//                    for (Task task : response.getData()) {
//                        listOfTasks.add(task);
//                    }
//                    Collections.sort(listOfTasks, new Comparator<Task>() {
//                        @Override
//                        public int compare(Task task, Task t1) {
//                            return Long.compare(task.getCreatedAt().toDate().getTime(), t1.getCreatedAt().toDate().getTime());
//                        }
//                    });
//
//
//                },
//                error -> Log.e("MyAmplifyApp", "Query failure", error)
//        );
//        return listOfTasks;
    }

    private void creatTeams(){
        AtomicBoolean x= new AtomicBoolean(false);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    if(response.getData().getRequestForNextResult()==null){
                        System.out.println("alooooolllllllllllllllllllllll");
                        System.out.println(response.getData().getRequestForNextResult());
                        x.set(true);
                        Log.i("Teams", "Successful query, found teams.");
                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        if(x.equals(false)){
            Team todo1 = Team.builder()
                    .name("Team 1").id("1")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo1),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            Team todo2 = Team.builder()
                    .name("Team 2").id("2")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo2),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
            Team todo3 = Team.builder()
                    .name("Team 3").id("3")
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(todo3),
                    response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        } }
}

