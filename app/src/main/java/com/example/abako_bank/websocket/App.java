package com.example.abako_bank.websocket;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

public final class App {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static OkHttpClient okHttpClient;
    private static Gson gson;
    private static boolean inForeground;

    public static void onCreate(Context context2) {
        context = context2;
        okHttpClient = new OkHttpClient.Builder()
                .build();
        gson = new GsonBuilder().create();
    }

    public static Context getContext() {
        return context;
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static Gson getGson() {
        return gson;
    }

    public static void setInForeground(boolean inForeground) {
        App.inForeground = inForeground;
    }

    public static boolean isInForeground() {
        return inForeground;
    }
}
