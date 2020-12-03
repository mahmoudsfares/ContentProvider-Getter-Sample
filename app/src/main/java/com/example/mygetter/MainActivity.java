package com.example.mygetter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mygetter.data.PinProviderContract;

public class MainActivity extends AppCompatActivity {

    Cursor c;
    TextView tv;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tv.setText(c.getString(1));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(receiver, new IntentFilter("data-fetched"));

        tv = findViewById(R.id.tv);
        getDataFromResolver();
    }

    private void getDataFromResolver(){
        new Thread(() -> {
            c = getContentResolver().query(PinProviderContract.CONTENT_URI, null, null, null, null);
            if(c.moveToNext())
                sendBroadcast(new Intent("data-fetched"));
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}