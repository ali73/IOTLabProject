package com.lab.ali.iotlab.Utils.WIFI_P2P;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;

public class IotLabP2PManager {
    private IntentFilter intentFilter;
    private WifiP2pManager p2pManager;
    private Context context;
    private P2P_BroadCastReceiver broadCastReceiver;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager.PeerListListener peerListListener;


    public IotLabP2PManager(Context context){
        this.initIntentFilter();
        this.context = context;
        this.p2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.mChannel = this.p2pManager.initialize(context,context.getMainLooper(),null);
    }
    private void initIntentFilter(){
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }
    public WifiP2pManager getP2pManager() {
        return p2pManager;
    }

    public void setP2pManager(WifiP2pManager p2pManager) {
        this.p2pManager = p2pManager;
    }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }

    public void setIntentFilter(IntentFilter intentFilter) {
        this.intentFilter = intentFilter;
    }

    private void registerReceiver(){
        context.registerReceiver(broadCastReceiver,intentFilter);
    }

    public void unRegisterReceiver(){
        context.unregisterReceiver(broadCastReceiver);
    }

    public void setBroadCastReceiver(P2P_BroadCastReceiver broadCastReceiver) {
        this.broadCastReceiver = broadCastReceiver;
        registerReceiver();
    }

    public void discoverPeers(WifiP2pManager.ActionListener actionListener){
        this.p2pManager.discoverPeers(mChannel,actionListener);
    }

    public WifiP2pManager.PeerListListener getPeerListListener() {
        return peerListListener;
    }

    public void setPeerListListener(WifiP2pManager.PeerListListener peerListListener) {
        this.peerListListener = peerListListener;
    }
    public void scanForPeer(){
        if (this.peerListListener!=null)
            p2pManager.requestPeers(mChannel,this.peerListListener);
    }

    public void connect(WifiP2pConfig config, WifiP2pManager.ActionListener actionListener){
        p2pManager.connect(mChannel,config,actionListener);
    }

    public WifiP2pManager.Channel getmChannel() {
        return mChannel;
    }
}
