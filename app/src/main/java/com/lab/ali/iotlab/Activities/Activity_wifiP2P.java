package com.lab.ali.iotlab.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lab.ali.iotlab.Adapters.WifiP2PDeviceAdapter;
import com.lab.ali.iotlab.R;
import com.lab.ali.iotlab.Utils.CallBack;
import com.lab.ali.iotlab.Utils.WIFI_P2P.IotLabP2PManager;
import com.lab.ali.iotlab.Utils.WIFI_P2P.P2P_BroadCastReceiver;

import java.util.ArrayList;
import java.util.List;

public class Activity_wifiP2P extends AppCompatActivity {
    TextView stateTxtView;
    IotLabP2PManager  mManager;
    WifiP2PDeviceAdapter adapter;
    RecyclerView recyclerView;
    Button scanForPeerBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_p2p);
        stateTxtView = findViewById(R.id.wifip2p_status);
        scanForPeerBtn = findViewById(R.id.wifiSearch);
        recyclerView = findViewById(R.id.p2pList);


        mManager = new IotLabP2PManager(getApplicationContext());
        if (WifiP2pManager.WIFI_P2P_STATE_ENABLED!=2){
            noWifiDirectInterfaceDialog();
        }
        P2P_BroadCastReceiver broadCastReceiver = new P2P_BroadCastReceiver();
        broadCastReceiver.setP2pStateChangedCallback(new CallBack() {
            @Override
            public void callBack(@Nullable Intent intent) {
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
                if (stateTxtView!=null)
                    if (state==WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                        stateTxtView.setText("State: Enabled");
                    else
                        stateTxtView.setText("State: Disabled");
            }
        });
        mManager.setBroadCastReceiver(broadCastReceiver);
        adapter= new WifiP2PDeviceAdapter(getApplicationContext());
        adapter.setConnectionCallback(new CallBack() {
            @Override
            public void callBack(@Nullable Intent intent) {
                WifiP2pConfig config = intent.getParcelableExtra("config");
                mManager.connect(config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("Connect","Successful");
                    }

                    @Override
                    public void onFailure(int i) {
                        Log.d("Connect","Failed!");
                    }
                });
            }
        });
        mManager.setPeerListListener(new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                List<WifiP2pDevice> refreshedPeers =new ArrayList<>(wifiP2pDeviceList.getDeviceList());
                if (refreshedPeers.size()==0) {
                    Log.d("PeerListListener","No Peers available");
                }
                else
                    Log.d("PeerListListener","Peers available");
                adapter.clear();
                adapter.addAll(refreshedPeers);
            }
        });


        scanForPeerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.scanForPeer();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }


    public void noWifiDirectInterfaceDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(getApplicationContext()).create();
        dialog.setTitle("Error");
        dialog.setMessage("No WIFI-Direct interface is available on this device");
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

}
