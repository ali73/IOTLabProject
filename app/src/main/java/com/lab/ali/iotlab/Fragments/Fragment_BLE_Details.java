package com.lab.ali.iotlab.Fragments;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lab.ali.iotlab.Activities.ActivityBLE;
import com.lab.ali.iotlab.Adapters.BLEServicesListAdapter;
import com.lab.ali.iotlab.R;

public class Fragment_BLE_Details extends Fragment {
    RecyclerView recyclerView;
    public static BLEServicesListAdapter adapter;
    BluetoothAdapter BTAdapter ;
    BluetoothManager bManager;

    public  Fragment_BLE_Details(){
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ble_services,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BLEServicesListAdapter(getContext());
        recyclerView = view.findViewById(R.id.ble_services);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        bManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        BTAdapter = bManager.getAdapter();
    }

    public BLEServicesListAdapter getAdapter() {
        return adapter;
    }
    public static void notifyDatasetChanged(){
        adapter.addAll(ActivityBLE.serviceList);
    }
}
