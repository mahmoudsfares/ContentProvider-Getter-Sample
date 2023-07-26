package com.example.mygetter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.example.mygetter.data.PinProviderContract;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int PIN_COLUMN_INDEX = 1;
    private static final String PIN_BROADCAST_RECEIVER_ACTION = "data-fetched";

    private Cursor c;
    private TextView tv;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // this is the cursor query: SELECT * FROM `device-pin` ORDER BY ROWID DESC LIMIT 1
            // the device-pin table has 2 columns (id, pin)
            // the int arg of the cursor.getString() method is the column index
            // so, we need to get the string value of the column with the index 1 (second column)
            tv.setText(c.getString(PIN_COLUMN_INDEX));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(receiver, new IntentFilter(PIN_BROADCAST_RECEIVER_ACTION));

        tv = findViewById(R.id.tv);
        getDataFromResolver();
    }

    private void getDataFromResolver(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                c = getContentResolver().query(PinProviderContract.CONTENT_URI, null, null, null, null);
                if(c.moveToNext())
                    sendBroadcast(new Intent(PIN_BROADCAST_RECEIVER_ACTION));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}