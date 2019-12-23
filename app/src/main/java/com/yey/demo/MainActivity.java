package com.yey.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import com.yey.library_wry.WaterRippleY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        WaterRippleY waterRippleY = (WaterRippleY) findViewById(R.id.wry);
        waterRippleY.setColors(new int[]{resources.getColor(R.color.colorAccent),
                resources.getColor(R.color.colorPrimary),
                resources.getColor(R.color.colorPrimaryDark),
                Color.BLUE,
                Color.YELLOW
        });
    }
}
