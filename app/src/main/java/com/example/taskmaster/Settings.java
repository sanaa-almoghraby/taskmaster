package com.example.taskmaster;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener((View -> {
            TextView text = findViewById(R.id.Name);
            String name =text.getText().toString();


            RadioButton b1=findViewById(R.id.radioButton1);
            RadioButton b2=findViewById(R.id.radioButton2);
            RadioButton b3=findViewById(R.id.radioButton3);

            String id = null;
            if(b1.isChecked()){
                id="1";
            }
            else if(b2.isChecked()){
                id="2";
            }
            else if(b3.isChecked()){
                id="3";
            }
            editor.putString("EnteredText",name);
            editor.putString("Team",id);
            editor.apply();
            System.out.println(id+"id *********************");
        }));
    }
}