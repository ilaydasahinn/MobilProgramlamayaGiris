package com.ilayda.mobilprogramming;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity{

    private EditText etUser;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etUser = (EditText)findViewById(R.id.etUser);
        etPassword = (EditText)findViewById(R.id.etPassword);
        btnReg = (Button)findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                String newUser = etUser.getText().toString();
                String newPassword = etPassword.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(newUser + ",-,-," + newPassword + ",-,-," + "data" ,  newUser );
                editor.commit();

                Toast.makeText(getApplicationContext(),"Registered Successfully! \n You Can Login Now! ", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }


}
