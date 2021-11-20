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

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.core.Amplify;
import com.squareup.picasso.Picasso;

public class Task_Detail extends AppCompatActivity {
    private String fileURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recordEvents();

        Intent intent = getIntent();
//        String taskName = intent.getExtras().getString("taskName");
//        TextView taskTitle = findViewById(R.id.textView7);
//        taskTitle.setText(taskName);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String title = intent.getExtras().getString("title", "title");
        String body = intent.getExtras().getString("body", "body");
        String state = intent.getExtras().getString("state", "state");
        String filename = intent.getExtras().getString("Filename", "Filename");

        TextView textView =findViewById(R.id.title);
        TextView bodyView =findViewById(R.id.body);
        TextView stateView =findViewById(R.id.state);

        textView.setText("Task Title:  "+title);
        bodyView.setText("Task Body:  "+body);
        stateView.setText("Task State:  "+state);

        ImageView taskImageDetail = findViewById(R.id.taskImageDetail);
        Picasso.get().load(filename).into(taskImageDetail);

//        TextView fileLinkDetail = findViewById(R.id.fileLinkDetail);
//        fileLinkDetail.setOnClickListener(view -> {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(fileURL));
//            startActivity(i);
//        });
//        if (filename != null) {
//
//            Amplify.Storage.getUrl(
//                    filename,
//                    result -> {
//                        Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
//                        runOnUiThread(() -> {
//                            if (filename.endsWith("png")
//                                    || filename.endsWith("jpg")
//                                    || filename.endsWith("jpeg")
//                                    || filename.endsWith("gif")) {
//
//
//                                taskImageDetail.setVisibility(View.VISIBLE);
//                            } else {
//                                System.out.println("test img");
//                                fileURL = String.valueOf(result.getUrl());
////                                String link = "<a href=\""+ result.getUrl() + "\">Download the linked file</a>";
//                                fileLinkDetail.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    },
//                    error -> Log.e("MyAmplifyApp", "URL generation failure", error)
//            );
//        }
    }

    public void recordEvents() {
        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("PasswordReset")
                .addProperty("Channel", "SMS")
                .addProperty("Successful", true)
                .addProperty("ProcessDuration", 792)
                .addProperty("UserAge", 120.3).build();
        Amplify.Analytics.recordEvent(event);
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
