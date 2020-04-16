package com.ilayda.mobilprogramming;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListUserActivity extends AppCompatActivity {

    ImageView ivPhoto;

    private boolean darkmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);




        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

        String display = preferences.getString("display", "");

        darkmode = preferences.getBoolean("darkmod", false);
        RecyclerView ll = (RecyclerView)findViewById(R.id.recyclerView);
        //RelativeLayout ll = (RelativeLayout) findViewById(R.id.recyclerView);

        if(darkmode){
            ll.setBackground(getResources().getDrawable(R.drawable.gradient));

        }
        else
        {
            ll.setBackgroundColor(getResources().getColor(R.color.colorLight));
        }


        int count = 0;

        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry entry : allEntries.entrySet()) {
            if(entry.getKey().toString().contains("data"))
                count++;
        }

        SharedPreferences userdata = getSharedPreferences("my_pref", MODE_PRIVATE);


        int i = 0;
        MyListData[] myListData = new MyListData[count];
        for (Map.Entry entry : allEntries.entrySet()) {
            if(entry.getKey().toString().contains("data")) {
                String name = entry.getValue().toString();
                String password = entry.getKey().toString().split(",-,-,")[1];
                String img = userdata.getString(name+"image", "");
                myListData[i] = (new MyListData(name, password, img));
                i++;
            }
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);




    }
}
