package com.example.tuongvy_bai9_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MySmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        processReceive(context, intent);
    }
    public void processReceive(Context context, Intent intent) {
        Bundle extras=intent.getExtras();
        String mess="";
        String body="";
        String address="";
        if(extras!=null)
        {
            Object[] smsEtra=(Object[])extras.get("pdus");
            for (int i = 0; i < smsEtra.length; i++) {
                SmsMessage sms=SmsMessage.createFromPdu((byte[])smsEtra[i]);
                address=sms.getOriginatingAddress();
                mess +="Bạn nhận được 1 tin nhắn từ "+address+"\n"+body+"vừa gữi đến";
            }
            Toast.makeText(context,mess,Toast.LENGTH_LONG).show();
        }
    }
}