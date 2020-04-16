package com.ilayda.mobilprogramming;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserPreferencesActivity extends AppCompatActivity {

    Button btnPhoto;
    ImageView ivPhoto;
    static  final int SELECT_IMAGE = 1;
    Uri uri;
    TextView tvUserName;
    EditText etHeight;
    EditText etWeight;
    EditText etAge;
    RadioGroup radioGroup;
    Button btnSave;
    Switch aSwitch;
    SharedPreferences sharedPreferences;
    static final String myPreference = "my_pref";
    static final String height = "heightKey";
    static  final String weight = "weightKey";
    static final String age = "ageKey";
    static final String KEY_SAVED_RADIO_BUTTON_INDEX = "SAVED_RADIO_BUTTON_INDEX";
    static final String switchh = "switchKey";
    static String display;
    static String imageData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_preferences);

        btnPhoto = (Button)findViewById(R.id.btnPhoto);
        ivPhoto = (ImageView)findViewById(R.id.ivPhoto);
        tvUserName = (TextView)findViewById(R.id.tvUserName);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etWeight = (EditText)findViewById(R.id.etWeight);
        etAge = (EditText)findViewById(R.id.etAge);
        aSwitch = (Switch)findViewById(R.id.switchDark);
        btnSave = (Button)findViewById(R.id.btnSave);
        radioGroup = (RadioGroup)findViewById(R.id.rgGender);
        radioGroup.setOnCheckedChangeListener(radioGroupOnCheckedChangeListener);

        SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
         display = preferences.getString("display", "");
        final TextView displayInfo = (TextView) findViewById(R.id.tvUserName);
        displayInfo.setText(display);


        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(display+"image")){
            String imageData = sharedPreferences.getString(display+"image", "");
            Bitmap btm = decodeBase64(imageData);
            ivPhoto.setImageBitmap(btm);
        }
        if(sharedPreferences.contains(display+age)){
            etAge.setText(sharedPreferences.getString(display+age, ""));
        }
        if(sharedPreferences.contains(display+weight)){
            etWeight.setText(sharedPreferences.getString(display+weight, ""));
        }
        if(sharedPreferences.contains(display+weight)){
            etHeight.setText(sharedPreferences.getString(display+height, ""));
        }
        if(sharedPreferences.contains(display+KEY_SAVED_RADIO_BUTTON_INDEX )){
            int savedRadioIndex = sharedPreferences.getInt(display+KEY_SAVED_RADIO_BUTTON_INDEX, 0);
            RadioButton savedCheckedRadioButton = (RadioButton)radioGroup.getChildAt(savedRadioIndex);
            savedCheckedRadioButton.setChecked(true);
        }
        if(sharedPreferences.contains(display+switchh)){
            boolean switchBool = sharedPreferences.getBoolean(display+switchh, true);
            aSwitch.setChecked(switchBool);
        }



        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //Herhangi bir dosya secmek icin kullan. Resim ya da belge
                startActivityForResult(intent, SELECT_IMAGE); //sectigini kullanabilmek icin
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                String h = etHeight.getText().toString();
                String a = etAge.getText().toString();
                String w = etWeight.getText().toString();

                if(aSwitch.isChecked()){
                    editor.putBoolean(display+switchh, true);

                }else{
                    editor.putBoolean(display+switchh, false);
                }

                editor.putString(display+height, h);
                editor.putString(display+age, a);
                editor.putString(display+weight, w);
                if(imageData != null)
                {
                    editor.putString(display+"image", imageData);
                }
                editor.commit();


                Intent intent = new Intent(UserPreferencesActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    OnCheckedChangeListener radioGroupOnCheckedChangeListener =
            new OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(checkedId);
                    int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);

                    sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(display+KEY_SAVED_RADIO_BUTTON_INDEX, checkedIndex);
                    editor.commit();

                }};


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK){ //RESULT_OK --> Kullanici secim yapabilmis.
            uri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap btmap = BitmapFactory.decodeFile(picturePath);//decode method called


            imageData = encodeTobase64(btmap);


            ivPhoto.setImageURI(uri);
            Toast.makeText(getApplicationContext(),"The photo can added successfully!", Toast.LENGTH_LONG).show();
        }else if(requestCode == RESULT_CANCELED){ //Secilemedi ise..
            Toast.makeText(getApplicationContext(),"The photo cannot added!", Toast.LENGTH_LONG).show();
        }
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }





}
