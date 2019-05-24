package com.hechuang.hepay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hechuang.hepay.service.JPushService;

public class BoardcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent = new Intent(context, JPushService.class);
        context.startService(pushintent);
    }
}
