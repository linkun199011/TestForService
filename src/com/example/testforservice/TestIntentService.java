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
        // ����һ��PendingIntent����Intent���ƣ���ͬ�������ڲ������ϵ��ã���Ҫ������״̬��������activity�����Բ��õ���PendingIntent,�����Notification��ת�������ĸ�Activity  
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,  
                new Intent(this, MainActivity.class), 0);  
        // ���������Android 2.x�汾�ǵĴ���ʽ  
        // Notification notify1 = new Notification(R.drawable.message,  
        // "TickerText:" + "�����¶���Ϣ����ע����գ�", System.currentTimeMillis());  
        Notification notify1 = new Notification();  
        notify1.icon = R.drawable.ic_launcher;
        notify1.tickerText = "TickerText:�����¶���Ϣ����ע����գ�";  
        notify1.when = System.currentTimeMillis();  
        notify1.setLatestEventInfo(this, "Notification Title",  
                "This is the notification message", pendingIntent);  
        notify1.number = 1;  
        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL������֪ͨ���û����ʱ��֪ͨ���������  
        // ͨ��֪ͨ������������֪ͨ�����id��ͬ����ÿclick����statu��������һ����ʾ  
        manager.notify(1, notify1); 
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Toast.makeText(this, "IntentService end", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

}
