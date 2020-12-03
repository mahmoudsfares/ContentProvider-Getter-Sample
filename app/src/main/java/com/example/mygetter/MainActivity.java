package com.example.mygetter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    Cursor c;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

//        if(checkPermissions())
            getDataFromResolver();

        new Handler().postDelayed(() -> {
            if(c.moveToNext())
                tv.setText(c.getString(1));
        }, 2000);
    }

    private void getDataFromResolver(){
        new Thread(() -> c = getContentResolver().query(Uri.parse("content://com.example.myprovider.provider.pinprovider/device-pin"), null, null, null, null)).start();
    }

//    private boolean checkPermissions(){
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//        || ActivityCompat.checkSelfPermission(this, "com.example.myprovider.TERMS_READ") != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, "com.example.myprovider.TERMS_READ"}, 10);
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode != 10){
//            if(grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this, "you have to accept all permessions!", Toast.LENGTH_SHORT).show();
//        }
//        else
//            getDataFromResolver();
//    }
}