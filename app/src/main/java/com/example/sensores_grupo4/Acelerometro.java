package com.example.sensores_grupo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.EditText;
import android.widget.TextView;

public class Acelerometro extends AppCompatActivity {


    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acelerometro);


        final TextView sonido = (TextView)findViewById(R.id.tvSonido);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if(sensor == null) finish();
        sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                if(x < -5 && whip == 0){
                    whip++;
                    sonido.setText("sonido"+whip);
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                }
                else if(x > 5 && whip  == 1){
                    whip++;
                    sonido.setText("sonido"+whip);
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                if(whip==2){
                    whip=0;
                    sound(); 
                    sonido.setText("sonido"+whip);
                }
            }

           

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }


        };
        star();
    }

    private void star() {
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void sound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.Latigo);
        mediaPlayer.start();
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause(){
        stop();
        super.onPause();
    }

    @Override
    protected void onResume(){
        star();
        super.onResume();
    }
}