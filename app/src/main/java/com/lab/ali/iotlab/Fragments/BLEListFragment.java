package com.lab.ali.iotlab.Fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lab.ali.iotlab.Activities.ActivityBLE;
import com.lab.ali.iotlab.Adapters.BLEListAdapter;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.LoadingDialog;

public class BLEListFragment extends Fragment implements BluetoothAdapter.LeScanCallback{
    BluetoothAdapter adapter ;
    BluetoothManager bManager;
    Button scanBtn;
    Handler scanHandler;
    RecyclerView recyclerView;
    BLEListAdapter rcAdapter;
    LoadingDialog loadingDialog;
    private static final int BLUETOOTH_ENABLE_RC = 671;
    private final int SCAN_DURATION = 10000;

    private void initBluetooth(){
        if (adapter!=null && !adapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,BLUETOOTH_ENABLE_RC);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ble_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scanBtn = view.findViewById(R.id.scan);
        recyclerView = view.findViewById(R.id.ble_list);
        loadingDialog = new LoadingDialog(getActivity());
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext()).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("No bluetooth is available on your device.");
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }

        bManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        adapter = bManager.getAdapter();
        initBluetooth();
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBtn.setEnabled(false);
                rcAdapter.clear();
                adapter.startLeScan(BLEListFragment.this);
                loadingDialog.show();
                scanHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.stopLeScan(BLEListFragment.this);
                        loadingDialog.dismiss();
                        scanBtn.setEnabled(true);
                    }
                },SCAN_DURATION);
            }
        });
        scanHandler = new Handler();

        rcAdapter = new BLEListAdapter(getActivity().getApplicationContext(),adapter);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


    }

    @Override
    public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
        Log.d("BLE","Scan completed!");
        rcAdapter.addDevice(bluetoothDevice);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.stopLeScan(this);
    }
}
