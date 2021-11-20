package com.example.taskmaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.amplifyframework.datastore.generated.model.Task;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    AppDatabase appDatabase;
    String img = "";
    private String uploadedFileNames;
    ActivityResultLauncher<Intent> someActivityResultLauncher;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            onChooseFile(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        findViewById(R.id.btnUploadFile).setOnClickListener(view -> {
            Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.setType("*/*");
            chooseFile = Intent.createChooser(chooseFile, "Choose a file");
            someActivityResultLauncher.launch(chooseFile);
        });

        Button homeButton = findViewById(R.id.back);
        homeButton.setOnClickListener(V -> {
            Intent goToHome = new Intent(AddTask.this, MainActivity.class);
            startActivity(goToHome);
        });

        Button addTaskButton = findViewById(R.id.button3);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                EditText title=findViewById(R.id.titleplain);
                String addTitle=title.getText().toString();

                EditText body=findViewById(R.id.descripton);
                String addBody=body.getText().toString();

                EditText state=findViewById(R.id.Status);
                String addState=state.getText().toString();


                RadioButton b1 = findViewById(R.id.team1);
                RadioButton b2 = findViewById(R.id.team2);
                RadioButton b3 = findViewById(R.id.team3);

                String id = null;
                if (b1.isChecked()) {
                    id = "1";
                } else if (b2.isChecked()) {
                    id = "2";
                } else if (b3.isChecked()) {
                    id = "3";
                }
                String url = sharedPreferences.getString("fileUrl","null");
                Log.i("onChooseFile", "onClick: ========>" + url);
                dataStore(addTitle, addBody, addState, id,url);


                Intent intent = new Intent(AddTask.this, MainActivity.class);
                startActivity(intent);


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
    private void dataStore(String title, String body, String state,String id,String url) {

        Task task = Task.builder().teamId(id).title(title).body(body).state(state).fileName(url).build();
        Amplify.API.mutate(
                ModelMutation.create(task),
                response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );


    }

    private void onChooseFile(ActivityResult activityResult) throws IOException {

        Uri uri = null;
        if (activityResult.getData() != null) {
            uri = activityResult.getData().getData();
        }
        assert uri != null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        Date date = new Date();
        String uploadedFileName = formatter.format(date) + "." + getMimeType(getApplicationContext(), uri);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        File uploadFile = new File(getApplicationContext().getFilesDir(), "uploadFile");
        Log.i("URI", "onChooseFile: URI =>>>>" + uri);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileUtils.copyToFile(inputStream, uploadFile);
        } catch (Exception exception) {
            Log.e("onChooseFile", "onActivityResult: file upload failed" + exception.toString());
        }

        Amplify.Storage.uploadFile(
                uploadedFileName,
                uploadFile,
                success -> {
                    Log.i("onChooseFile", "uploadFileToS3: succeeded " + success.getKey());
                    Amplify.Storage.getUrl(success.getKey(),
                            urlSuccess->{
                                Log.i("onChooseFile", "onChooseFile: " + urlSuccess.getUrl().toString());
                                sharedPreferences.edit().putString("fileUrl",urlSuccess.getUrl().toString()).apply();
                            },
                            urlError->{});
                    },
                error -> Log.e("onChooseFile", "uploadFileToS3: failed " + error.toString())
        );
        uploadedFileNames = uploadedFileName;
    }


    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }
}