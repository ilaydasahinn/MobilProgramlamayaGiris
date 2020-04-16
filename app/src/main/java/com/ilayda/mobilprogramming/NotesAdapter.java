package com.ilayda.mobilprogramming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class NotesAdapter extends RecyclerView.Adapter  <NotesAdapter.MyViewHolder> {
    private static final int REQUEST_CODE = 5;
    private List<NotesBuilder>
            notesList;
    private Activity mActivity;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public Button button;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView);
            button = (Button) view.findViewById(R.id.button) ;
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout2);

        }
    }



    public NotesAdapter(List< NotesBuilder > notesList, Context context) {
        this.notesList = notesList;
        mActivity = (Activity) context;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.note_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(listItem);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final NotesBuilder note = notesList.get(position);
        holder.title.setText(notesList.get(position).getTitle());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    //Toast.makeText(view.getContext(),"click on item: "+note.getTitle(),Toast.LENGTH_LONG).show();
                    Intent data = mActivity.getIntent();
                    data.putExtra("note", note.getTitle());
                    mActivity.setResult(REQUEST_CODE, data);
                    mActivity.finish();
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filePath = mActivity.getDataDir() + File.separator + mActivity.getIntent().getExtras().getString("display")+ File.separator + note.getTitle();
                File fdelete = new File(filePath);
                if (fdelete.exists()) {
                    fdelete.delete();
                }
                Toast.makeText(mActivity.getApplicationContext(),"The note is deleted successfully!", Toast.LENGTH_LONG).show();
                mActivity.finish();
            }
        });
    }
    @Override
    public int getItemCount() {
        return notesList.size();
    }

}

