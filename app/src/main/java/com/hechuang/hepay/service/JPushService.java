package com.hechuang.hepay.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.hechuang.hepay.api.ApiFactory;
import com.hechuang.hepay.ui.activity.WebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class JPushService extends BroadcastReceiver {

    private static final String TAG = "JIGUANG";

    // RegistrationID 定义
    // 集成了 JPush SDK 的应用程序在第一次成功注册到 JPush 服务器时，
    // JPush 服务器会给客户端返回一个唯一的该设备的标识 - RegistrationID。
    // JPush SDK 会以广播的形式发送 RegistrationID 到应用程序。
    // 应用程序可以把此 RegistrationID 保存以自己的应用服务器上，
    // 然后就可以根据 RegistrationID 来向设备推送消息或者通知。
    public static String regId;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            Bundle bundle = intent.getExtras();
//            Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                ceshibaidu(context, bundle.getString(JPushInterface.EXTRA_MESSAGE));
                // 对应极光后台的 - 自定义消息  默认不会出现在notification上 所以一般都选用发送通知
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                ceshibaidu(context, bundle.getString(JPushInterface.EXTRA_ALERT));
//                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知:" + bundle.getString(JPushInterface.EXTRA_ALERT));
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
                Intent i = new Intent(context, WebActivity.class);
                i.putExtra("web_url", ApiFactory.HOST + "index.php/Home/Record/record_ecoin");
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
//                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
//                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
//                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

        } catch (Exception e) {

        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {

        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
//                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
//                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    protected String appId = "11212186";

    protected String appKey = "DbNKXfU3Za11gAwcyHKbM2cp";

    protected String secretKey = "liEHOaFONkTERKxMnICClRO1oBRdUhAE";

    private void ceshibaidu(Context context, String text) {
        LoggerProxy.printable(true);
        SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
//                Log.d(TAG, "onSynthesizeStart: " + s);
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
//                Log.d(TAG, "onSynthesizeDataArrived: " + s);
            }

            @Override
            public void onSynthesizeFinish(String s) {
//                Log.d(TAG, "onSynthesizeFinish: " + s);
            }

            @Override
            public void onSpeechStart(String s) {
//                Log.d(TAG, "onSpeechStart: " + s);
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
//                Log.d(TAG, "onSpeechProgressChanged: " + s + "\n" + i);
            }

            @Override
            public void onSpeechFinish(String s) {
//                Log.d(TAG, "onSpeechFinish: " + s);
            }

            @Override
            public void onError(String s, SpeechError speechError) {
//                Log.d(TAG, "onError: " + s + "\n" + speechError);
            }
        });
        mSpeechSynthesizer.setAppId(appId);
        mSpeechSynthesizer.setApiKey(appKey, secretKey);
        mSpeechSynthesizer.auth(TtsMode.MIX);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置发声的人声音，在线生效
        // 设置合成的音量，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");
        mSpeechSynthesizer.initTts(TtsMode.MIX); // 初始化离在线混合模式，如果只需要在线合成功能，使用 TtsMode.ONLINE
        mSpeechSynthesizer.speak(text);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
