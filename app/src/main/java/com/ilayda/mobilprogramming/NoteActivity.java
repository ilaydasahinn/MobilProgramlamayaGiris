package com.ilayda.mobilprogramming;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.text.BoringLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 5;
    EditText etNote;
    EditText editText;
    Button buttonSave;
    static String display;
    private boolean darkmode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        File folder = new File(NoteActivity.this.getDataDir() +
                File.separator + display);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        display = preferences.getString("display", "");

        darkmode = preferences.getBoolean("darkmod", false);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.noteLayout);


        if(darkmode){
            ll.setBackground(getResources().getDrawable(R.drawable.gradient));

        }
        else
        {
            ll.setBackgroundColor(getResources().getColor(R.color.colorLight));
        }
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        editText = (EditText) findViewById(R.id.editText);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length() ==  0){
                    Toast.makeText(getApplicationContext(),"You have to enter a name of your note!", Toast.LENGTH_LONG).show();
                }else{
                    Save(editText.getText().toString());
                }

            }
        });

        Button buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NoteActivity.this, NoteSelect.class);
                myIntent.putExtra("display", display);
                startActivityForResult(myIntent, REQUEST_CODE);
            }
        });
        etNote = (EditText)findViewById(R.id.etNote);

    }


    public void Save(String fileName) {
        try {
            String filePath = NoteActivity.this.getDataDir() +
                    File.separator + display + File.separator + fileName;
            OutputStreamWriter out =
                    new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
            out.write(etNote.getText().toString());
            out.close();
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String Open(String fileName) {
        String content = "";
        try {
            String filePath = NoteActivity.this.getDataDir() +
                    File.separator + display + File.separator + fileName;
            InputStream in = new FileInputStream(filePath);
            if ( in != null) {
                InputStreamReader tmp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                } in .close();
                content = buf.toString();
            }
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
        return content;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE && requestCode == REQUEST_CODE) {
            etNote.setText(Open(data.getExtras().getString("note")));
            editText.setText(data.getExtras().getString("note"));
        }
        else {
            etNote.setText("");
            editText.setText("");
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


}
