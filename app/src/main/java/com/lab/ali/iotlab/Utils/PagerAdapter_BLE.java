package com.lab.ali.iotlab.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.Fragments.BLEListFragment;
import com.lab.ali.iotlab.Fragments.Fragment_BLE_Details;

public class PagerAdapter_BLE extends FragmentPagerAdapter {

    Fragment[] fragments;
    String[] titles;
    public PagerAdapter_BLE(FragmentManager fm){
        super(fm);
        this.fragments =new Fragment[2];
        fragments[0] = new BLEListFragment();
        fragments[1] = new Fragment_BLE_Details();
        titles = new String[2];
        titles[0] = "Devices";
        titles[1] = "Details";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
