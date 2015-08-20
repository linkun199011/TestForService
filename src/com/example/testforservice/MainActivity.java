package com.example.testforservice;

import com.example.testforservice.TestLocalService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
    //private MyReceiver mMyReceiver;
    private Button mStartIntentService;
    private Button mStopIntentService;
    private Button mStartService;
    private Button mStopService1;
    private Button mStopService2;
    private Button mBindService;
    private Button mUnbindService;
    private Button mGetNumber;
    private Intent service ;
    private Intent intentService ;
    // for bind service
    TestLocalService mLocalService;
    boolean mBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        service = new Intent(this, TestService.class);
        intentService = new Intent(this, TestIntentService.class);
        
        mStartIntentService = (Button) findViewById(R.id.start_intentservice);
        mStopIntentService = (Button) findViewById(R.id.stop_intentservice);
        mStartService = (Button) findViewById(R.id.start_service);
        mStopService1 = (Button) findViewById(R.id.stop_service1);
        mStopService2 = (Button) findViewById(R.id.stop_service2);
        mBindService = (Button) findViewById(R.id.bind_service);
        mUnbindService = (Button) findViewById(R.id.unbind_service);
        mGetNumber = (Button) findViewById(R.id.get_number);
        
        mStartIntentService.setOnClickListener(this);
        mStopIntentService.setOnClickListener(this);
        mStartService.setOnClickListener(this);
        mStopService1.setOnClickListener(this);
        mStopService2.setOnClickListener(this);
        mBindService.setOnClickListener(this);
        mUnbindService.setOnClickListener(this);
        mGetNumber.setOnClickListener(this);
    }
    
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        System.out.println("onresume!!!!!");
        
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        System.out.println("onstop!!!!!");
        if(mBound){
            unbindService(mConnection);
            mBound = false;
            System.out.println("unbind Success !!!!");
        }
        else
            System.out.println("unbind not Success !!!!");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        System.out.println("ondestroy!!!!!");
    }

    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
        case R.id.start_intentservice:
            System.out.println("button1 is clicked.");
            startService(intentService);
            break;
        case R.id.stop_intentservice:
            System.out.println("button2 is clicked.");
            // No need to stop manually
            break;
        case R.id.start_service:
            System.out.println("button3 is clicked.");
            startService(service);
            break;
        case R.id.stop_service1:
            System.out.println("button4 is clicked.");
            Intent intent4=new Intent();
            intent4.setAction("AAAAA");
            intent4.putExtra("cmd", 0);
            sendBroadcast(intent4);
            break;
        case R.id.stop_service2:
            System.out.println("button5 is clicked.");
            stopService(service);
            break;
        case R.id.bind_service:
            // bind to LocalServce
            Intent intent6 = new Intent(this, TestLocalService.class);
            bindService(intent6, mConnection, Context.BIND_AUTO_CREATE);
            break;
        case R.id.get_number:
            if (mBound) {
                System.out.println("get number from service Success !!!!");
                // Call a method from the LocalService.
                // However, if this call were something that might hang, then this request should
                // occur in a separate thread to avoid slowing down the activity performance.
                int num = mLocalService.getRandomNumber();
                Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
            }
            else
                System.out.println("get number from service not Success !!!!");
            
            break;
        case R.id.unbind_service:
            if(mBound){
                unbindService(mConnection);
                mBound = false;
                System.out.println("unbind Success !!!!");
            }
            else
                System.out.println("unbind not Success !!!!");
            
            break;

        default:
            break;
        }
    }
    
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mLocalService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    
}
