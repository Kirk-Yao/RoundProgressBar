package com.example.admin.viewflippertest;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private CircleProgressView circleProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgress = (CircleProgressView) findViewById(R.id.circle_progress);
        startDownload();
    }

    private void startDownload(){
        new Thread(){

            @Override
            public void run() {
                super.run();
                float progress = circleProgress.getProgress();
                ++progress;
                circleProgress.setProgress(progress);
                try {
                    sleep(30);
                    if (progress <= 100){
                        startDownload();
                    } else {
                        circleProgress.progressFinish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
