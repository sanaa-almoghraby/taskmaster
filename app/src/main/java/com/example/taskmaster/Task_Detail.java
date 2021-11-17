package com.example.taskmaster;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.squareup.picasso.Picasso;

public class Task_Detail extends AppCompatActivity {
    private String fileURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
//        String taskName = intent.getExtras().getString("taskName");
//        TextView taskTitle = findViewById(R.id.textView7);
//        taskTitle.setText(taskName);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String title = sharedPreferences.getString("title", "title");
        String body = sharedPreferences.getString("body", "body");
        String state = sharedPreferences.getString("state", "state");
        String filename = sharedPreferences.getString("Filename", "");

        TextView textView =findViewById(R.id.title);
        TextView bodyView =findViewById(R.id.body);
        TextView stateView =findViewById(R.id.state);

        textView.setText("Task Title:  "+title);
        bodyView.setText("Task Body:  "+body);
        stateView.setText("Task State:  "+state);

        TextView fileLinkDetail = findViewById(R.id.fileLinkDetail);
        fileLinkDetail.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fileURL));
            startActivity(i);
        });
        if (filename != null) {

            Amplify.Storage.getUrl(
                    filename,
                    result -> {
                        Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
                        runOnUiThread(() -> {
                            if (filename.endsWith("png")
                                    || filename.endsWith("jpg")
                                    || filename.endsWith("jpeg")
                                    || filename.endsWith("gif")) {
                                ImageView taskImageDetail = findViewById(R.id.taskImageDetail);
                                System.out.println(result.getUrl());
                                Picasso.get().load(String.valueOf(result.getUrl())).into(taskImageDetail);

                                taskImageDetail.setVisibility(View.VISIBLE);
                            } else {
                                fileURL = String.valueOf(result.getUrl());
//                                String link = "<a href=\""+ result.getUrl() + "\">Download the linked file</a>";
                                fileLinkDetail.setVisibility(View.VISIBLE);
                            }
                        });
                    },
                    error -> Log.e("MyAmplifyApp", "URL generation failure", error)
            );
        }
    }



}
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = getIntent();
//        String taskName = intent.getExtras().getString("taskName");
//        TextView taskTitle = findViewById(R.id.textView7);
//        taskTitle.setText(taskName);
//    }
