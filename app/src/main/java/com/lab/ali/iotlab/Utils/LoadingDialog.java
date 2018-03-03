package com.lab.ali.iotlab.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import com.lab.ali.iotlab.R;

/**
 * Created by ali on 3/1/18.
 */

public class LoadingDialog {
    public Activity activity;
    Dialog loadingDialog;
    public LoadingDialog(Activity activity){
        this.activity = activity;
        loadingDialog = new Dialog(activity);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
    }

    public void show(){
        loadingDialog.show();
    }
    public void dismiss(){
        loadingDialog.dismiss();
    }
}
