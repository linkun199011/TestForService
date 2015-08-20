package com.example.testforservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private final static String TAG = "MyReceiver_linkun";
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(BOOT_ACTION)) {
            Intent intent2 = new Intent(context, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
            Log.d("DEBUG", "开机自动服务自动启动...");
            Toast.makeText(context,
                    "BOOT_COMPLETED detected!",
                    Toast.LENGTH_SHORT).show();
        } 
        else if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            System.out.println("飞行模式广播");
            String message = intent
                    .getStringExtra(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            if (message == null) {
                message = "null";
                Toast.makeText(context,
                        "飞行模式：" + message,
                        Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(context, MainActivity.class);
                //intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS); 
                context.startActivity(intent2);
            }
            else{
                Toast.makeText(context,
                        "飞行模式：" + message,
                        Toast.LENGTH_SHORT).show();
            }
            
        }
    }

}
