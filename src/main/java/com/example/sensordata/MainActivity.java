package com.example.sensordata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    // System sensor manager instance.
    private SensorManager mSensorManager;

    // Accelerometer and gyroscope sensors, as retrieved from the sensor manager.
    private Sensor mSensorAcc;
    private Sensor mSensorGyro;

    // TextViews to display current Accelerometer sensor values.
    private TextView xValue, yValue, zValue;

    //TextViews to display current Gyroscope sensor values.
    private TextView pValue, qValue, rValue;
    float x=0,y=0,z=0,p=0,q=0,r=0;
    long startTime= new Date().getTime();
    long currentTime;

    //Matrix to send
    ArrayList< ArrayList<Float> > sensorMatrix= new ArrayList< ArrayList<Float> >();
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all view variables.
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        pValue = (TextView) findViewById(R.id.pValue);
        qValue = (TextView) findViewById(R.id.qValue);
        rValue = (TextView) findViewById(R.id.rValue);



        // Get an instance of the sensor manager.
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        
        mSensorAcc = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        mSensorGyro = mSensorManager.getDefaultSensor(
                Sensor.TYPE_GYROSCOPE);

        // Get the error message from string resources.
        String sensor_error = getResources().getString(R.string.error_no_sensor);

        // If either mSensorLight or mSensorProximity are null, those sensors
        // are not available in the device.  Set the text to the error message
        if (mSensorAcc == null) {
            xValue.setText(sensor_error);
            yValue.setText(sensor_error);
            zValue.setText(sensor_error);
        }
        if (mSensorGyro == null) {
            pValue.setText(sensor_error);
            qValue.setText(sensor_error);
            rValue.setText(sensor_error);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onPause().
        if (mSensorAcc != null) {
            mSensorManager.registerListener(this, mSensorAcc,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorGyro != null) {
            mSensorManager.registerListener(this, mSensorGyro,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
//        Timer time= new Timer();
//        time.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is paused.
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        // The sensor type (as defined in the Sensor class).
        //int sensorType = sensorEvent.sensor.getType();
        //Timer time= new Timer();

        int sensorType = sensorEvent.sensor.getType();
//        x = sensorEvent.values[0];
//        y = sensorEvent.values[1];
//        z = sensorEvent.values[2];
//        p = sensorEvent.values[0];
//        q = sensorEvent.values[1];
//        r = sensorEvent.values[2];
        switch(sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                break;
            case Sensor.TYPE_GYROSCOPE:
                p = sensorEvent.values[0];
                q = sensorEvent.values[1];
                r = sensorEvent.values[2];
                break;
            default:
                System.out.println("Default Encountered");
        }

//        time.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                xValue.setText("x value is : " + x);
//                yValue.setText("y value is : " + y);
//                zValue.setText("z value is : " + z);
//
//                pValue.setText("x value is : " + p);
//                qValue.setText("y value is : " + q);
//                rValue.setText("z value is : " + r);
//            }
//        },0,2000);
//        xValue.setText("x value is : " + x);
//        yValue.setText("y value is : " + y);
//        zValue.setText("z value is : " + z);
//
//        pValue.setText("x value is : " + p);
//        qValue.setText("y value is : " + q);
//        rValue.setText("z value is : " + r);

          currentTime= new Date().getTime();
          System.out.println(currentTime-startTime);
        if(currentTime-startTime>500){
            xValue.setText("x value is : " + x);
            yValue.setText("y value is : " + y);
            zValue.setText("z value is : " + z);

            pValue.setText("x value is : " + p);
            qValue.setText("y value is : " + q);
            rValue.setText("z value is : " + r);

            sensorMatrix.add(new ArrayList<Float>(Arrays.asList(x,y,z,p,q,r)));
            counter+=1;
            System.out.println(startTime-currentTime + " ---- " + counter);
            startTime=currentTime;
        }
        if(counter==128){
            counter=0;
            System.out.println(sensorMatrix);
        }
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }

    /**
     * Abstract method in SensorEventListener.  It must be implemented, but is
     * unused in this app.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
