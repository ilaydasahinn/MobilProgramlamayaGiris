package com.ilayda.mobilprogramming;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button Mail;
    private Button UsersList;
    private Button Sensors;
    private Button Notes;
    private Button UserSettings;
    private Button Exit;
    //private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);




        Mail = (Button)findViewById(R.id.btnMail);
        UsersList = (Button)findViewById(R.id.btnUsers);
        Sensors = (Button)findViewById(R.id.btnSensor);
        Notes = (Button)findViewById(R.id.btnNote);
        UserSettings = (Button)findViewById(R.id.btnSettings);
        Exit = (Button)findViewById(R.id.btnExit);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String display = preferences.getString("display", "");
        TextView displayInfo = (TextView)findViewById(R.id.tvWelcome);
        displayInfo.setText("Welcome " + display + "!");


        Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MailActivity.class);
                startActivity(intent);
            }
        });

        UserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UserPreferencesActivity.class);
                startActivity(intent);
            }
        });

        UsersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Log out Successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });


        }

    }

