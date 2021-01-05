package com.example.sensordata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * SensorListeners demonstrates how to gain access to sensors (here, the light
 * and proximity sensors), how to register sensor listeners, and how to
 * handle sensor events.
 */
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

        // Get light and proximity sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
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
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL)
        if (mSensorAcc != null) {
            mSensorManager.registerListener(this, mSensorAcc,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorGyro != null) {
            mSensorManager.registerListener(this, mSensorGyro,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
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

        // The new data value of the sensor.  Both the light and proximity
        // sensors report one value at a time, which is always the first
        // element in the values array.
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        float p = sensorEvent.values[0];
        float q = sensorEvent.values[1];
        float r = sensorEvent.values[2];

        xValue.setText("x value is : " + x);
        yValue.setText("y value is : " + y);
        zValue.setText("z value is : " + z);

        pValue.setText("p value is : " + p);
        qValue.setText("q value is : " + q);
        rValue.setText("r value is : " + r);


    }

    /**
     * Abstract method in SensorEventListener.  It must be implemented, but is
     * unused in this app.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}