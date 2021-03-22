package com.example.cs441program5;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Display;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private float background1y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView background1 = findViewById(R.id.imageView);
        ImageView background2 = findViewById(R.id.imageView2);
        ImageView background3 = findViewById(R.id.imageView3);

        ImageView ambulance = findViewById(R.id.ambulance);
        ambulance.setX(130);

        ImageView car2 = findViewById(R.id.car2);
        car2.setX(450);

        ImageView van = findViewById(R.id.van);
        van.setX(290);

        ImageView taxi = findViewById(R.id.taxi);
        taxi.setX(580);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ImageView text = findViewById(R.id.car);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        SensorEventListener accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(text.getX() >= 115 && (-sensorEvent.values[0] < 0)){
                    text.setX(text.getX() + (-sensorEvent.values[0] * 5));
                } else if(text.getX() <= 690 && (-sensorEvent.values[0] > 0)){
                    text.setX(text.getX() + (-sensorEvent.values[0] * 5));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = background1.getHeight();
                final float translationY = height * progress;
                background1.setTranslationY(translationY);
                background2.setTranslationY(translationY - height/2);
                background3.setTranslationY(translationY - height);
            }
        });
        final ValueAnimator carAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        carAnimator.setRepeatCount(ValueAnimator.INFINITE);
        carAnimator.setInterpolator(new LinearInterpolator());
        carAnimator.setDuration(10000L);
        Random rand = new Random();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {
               final float progress = (float) animation.getAnimatedValue();
               final float height = background1.getHeight();
               car2.setTranslationY((height * 3 * progress) - height);
               van.setTranslationY((height* 2 * progress) - height);
               ambulance.setTranslationY((height * 4 * progress) - height);
               taxi.setTranslationY((height * 5 * progress) - height);
           }
        });
        carAnimator.start();
        animator.start();

    }
}