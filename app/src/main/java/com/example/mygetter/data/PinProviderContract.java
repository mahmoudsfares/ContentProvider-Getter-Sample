package com.example.mygetter.data;

import android.net.Uri;

public class PinProviderContract {

    private static final String AUTHORITY = "com.example.myprovider.provider";
    private static final String PATH = "device-pin";

    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH);
}
