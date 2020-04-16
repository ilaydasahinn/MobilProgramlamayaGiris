package com.ilayda.mobilprogramming;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    TextView tvSensor;
    SensorManager sensorManager;
    Sensor sensor;
    Sensor sensor2;
    static int counter = 0;
    static float old;
    static long startx;
    static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);

        tvSensor = (TextView)findViewById(R.id.tvSensor);
        sensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        RelativeLayout ll = (RelativeLayout) findViewById(R.id.sensorLayout);
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(event.values[0] >= 650){
                ll.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                tvSensor.setTextColor(getResources().getColor(R.color.colorDark));
            }else{
                ll.setBackgroundColor(getResources().getColor(R.color.colorDark));
                tvSensor.setTextColor(getResources().getColor(R.color.colorWhite));
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float diff = (float) Math.sqrt(x * x + y * y + z * z);

            if (Math.abs(diff-old) < 0.01){
                if(counter == 0){
                    counter = 1;
                    startx = System.currentTimeMillis();
                }else{
                    long endx = System.currentTimeMillis();
                    if((endx-startx) > 5000) {
                        if(flag){
                            Toast.makeText(getApplicationContext(),"The application is finished because the phone has been stable for 5 seconds.", Toast.LENGTH_LONG).show();
                            flag = false;
                        }
                        this.finishAffinity();
                    }
                }
            } else{
                flag = true;

                counter = 0;
            }
            old = diff;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
