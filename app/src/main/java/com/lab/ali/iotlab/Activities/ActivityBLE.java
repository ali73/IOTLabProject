package com.lab.ali.iotlab.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.PagerAdapter_BLE;

public class ActivityBLE extends AppCompatActivity {

    PagerAdapter_BLE pagerAdapter_ble ;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        viewPager = findViewById(R.id.ble_viewpager);
        tabLayout = findViewById(R.id.ble_tablayout);
        pagerAdapter_ble = new PagerAdapter_BLE(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter_ble);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}