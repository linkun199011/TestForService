package com.example.testforservice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

public class TestService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    //
    private MyReceiver mMyReceiver;
    private CommandReceiver mCmdReceiver;

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            /*long endTime = System.currentTimeMillis() + 30 * 1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }*/
            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            //stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        System.out.println("Service onStart !!!!!");
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        // ADD BROADCAST
        IntentFilter intentFilter1 = new IntentFilter(
                Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mMyReceiver = new MyReceiver();
        registerReceiver(mMyReceiver, intentFilter1);
        //
        IntentFilter intentFilter2=new IntentFilter();
        intentFilter2.addAction("AAAAA");
        mCmdReceiver = new CommandReceiver();
        registerReceiver(mCmdReceiver,intentFilter2);
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the
        // job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        
        
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("Service onDestroy !!!!!");
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        if (mMyReceiver != null) {
            unregisterReceiver(mMyReceiver);
        }
        if (mCmdReceiver != null){
            unregisterReceiver(mCmdReceiver);
        }
    }

    // 接受广播
    private class CommandReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("CommandReceiver onReceive");
            int cmd = intent.getIntExtra("cmd", -1);
            System.out.println("cmd = " + cmd);
            if (cmd == 0) {// 如果等于0
                System.out.println("CommandReceiver stopSel");
                stopSelf();// 停止服务
            }
        }

    }
    

}
