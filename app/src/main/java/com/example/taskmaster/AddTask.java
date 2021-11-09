package com.example.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.amplifyframework.datastore.generated.model.Task;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

public class AddTask extends AppCompatActivity {
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button homeButton = findViewById(R.id.back);
        homeButton.setOnClickListener(V -> {
            Intent goToHome = new Intent(AddTask.this, MainActivity.class);
            startActivity(goToHome);
        });

        Button addTaskButton = findViewById(R.id.button3);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                EditText title=findViewById(R.id.titleplain);
                String addTitle=title.getText().toString();

                EditText body=findViewById(R.id.descripton);
                String addBody=body.getText().toString();

                EditText state=findViewById(R.id.Status);
                String addState=state.getText().toString();


                Task todo = Task.builder()
                        .title(addTitle)
                        .body(addBody)
                        .state(addState)
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );


//                Task task =new Task(addTitle,addBody,addState);
//                Long addedTaskID = AppDatabase.getInstance(getApplicationContext()).taskDao().insertAll(task);
//
//                Toast.makeText(getApplicationContext(),"submitted!", Toast.LENGTH_SHORT).show();
//
//                System.out.println(
//                        "++++++++++++++++++++++++++++++++++++++++++++++++++" +
//                                " Student ID : " + addedTaskID
//                                +
//                                "++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        });
    }
}