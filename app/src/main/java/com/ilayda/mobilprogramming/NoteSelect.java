package com.ilayda.mobilprogramming;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteSelect extends AppCompatActivity {
    private boolean darkmode;
    private List<
        NotesBuilder >
    notesList = new ArrayList<>
    ();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;
    static String display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_select);
        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        display = preferences.getString("display", "");

        darkmode = preferences.getBoolean("darkmod", false);
        RecyclerView ll = (RecyclerView)findViewById(R.id.notes);
        //RelativeLayout ll = (RelativeLayout) findViewById(R.id.recyclerView);

        if(darkmode){
            ll.setBackground(getResources().getDrawable(R.drawable.gradient));

        }
        else
        {
            ll.setBackgroundColor(getResources().getColor(R.color.colorLight));
        }

        notesRecycler = (RecyclerView) findViewById(R.id.notes);

        nAdapter = new NotesAdapter(notesList,this);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);
        prepareNotes();

    }

    private void prepareNotes() {
        String filePath = NoteSelect.this.getDataDir() +
                File.separator + display;
        File directory = new File(filePath);
        File[] files = directory.listFiles();
        if(directory.exists()){
            for (File f : files) {
                if (f.isFile() || f.isDirectory()) {
                    NotesBuilder note = new NotesBuilder(f.getName(), Open(f.getName()));
                    notesList.add(note);
                }
            }
        }else{
            File folder = new File(NoteSelect.this.getDataDir() +
                    File.separator + display);

            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

    }

    public String Open(String fileName) {
        String content = "";
        try {
            String filePath = NoteSelect.this.getDataDir() +
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
}
