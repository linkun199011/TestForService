package com.example.testforservice;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestIntentService extends IntentService {
    /**
     * A constructor is required, and must call the super IntentService(String)
     * constructor with a name for the worker thread.
     */
    public TestIntentService() {
        super("TestIntentService");
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "IntentService start", Toast.LENGTH_SHORT).show();
        super.onCreate();
    }
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("start_intentservice in IntentService");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity  
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  
                new Intent(this, MainActivity.class), 0);  
        // 下面需兼容Android 2.x版本是的处理方式  
        // Notification notify1 = new Notification(R.drawable.message,  
        // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());  
        Notification notify1 = new Notification();  
        notify1.icon = R.drawable.ic_launcher;
        notify1.tickerText = "TickerText:您有新短消息，请注意查收！";  
        notify1.when = System.currentTimeMillis();  
        notify1.setLatestEventInfo(this, "Notification Title",  
                "This is the notification message", pendingIntent);  
        notify1.number = 1;  
        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。  
        // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示  
        manager.notify(1, notify1); 
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "IntentService end", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
