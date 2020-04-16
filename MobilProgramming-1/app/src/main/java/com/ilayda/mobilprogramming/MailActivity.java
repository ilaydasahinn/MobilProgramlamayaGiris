package com.ilayda.mobilprogramming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MailActivity extends AppCompatActivity {

    EditText etTo, etSubject, etMessage;
    Button btnSend, btnAttach;
    static  final int SELECT_IMAGE = 12;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);

        etTo = (EditText)findViewById(R.id.etTo);
        etSubject = (EditText)findViewById(R.id.etSeubject);
        etMessage = (EditText)findViewById(R.id.etMessage);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnAttach = (Button)findViewById(R.id.btnAttach);

        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //Herhangi bir dosya secmek icin kullan. Resim ya da belge
                intent.setType("*/*"); //Her turdeki dosyayi alabilsin diye
                startActivityForResult(intent, SELECT_IMAGE); //sectigini kullanabilmek icin

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(new String[] { etTo.getText().toString() }, etSubject.getText().toString() ,  etMessage.getText().toString(), uri);

            }
        });
    }
    //Kullanici dosyayi sectikten sonra sistem bir intent meydana getiriyor.
    //Bu intent secilen dosyanin URI sini sakliyor, onu almamiz lazim.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK){ //RESULT_OK --> Kullanici secim yapabilmis.
            uri = data.getData();
            Toast.makeText(getApplicationContext(),"Attachment Successful", Toast.LENGTH_LONG).show();
        }else if(requestCode == RESULT_CANCELED){ //Secilemedi ise..
            Toast.makeText(getApplicationContext(),"Attachment Unsuccessful", Toast.LENGTH_LONG).show();
        }
    }

    public void sendMail(String[] addresses, String subject, String body, Uri attachment){

        Intent intent = new Intent(Intent.ACTION_SEND); //Bir seyleri gondermek icin kullanilan intent
        intent.setType("*/*"); //Her turlu veri paylasilabilir.
        intent.putExtra(Intent.EXTRA_EMAIL, addresses); //Email'in gonderecegi adresler burada belirlenir.
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        startActivity(intent);


    }

}
