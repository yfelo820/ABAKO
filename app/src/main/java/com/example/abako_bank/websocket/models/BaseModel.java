package com.example.abako_bank.websocket.models;

import androidx.annotation.NonNull;

import com.example.abako_bank.websocket.App;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseModel {


    public static <T extends BaseModel>T fromJson(String json, Class<T>typeOf) {
        return App.getGson().fromJson(json, typeOf);
    }

    @NonNull
    @Override
    public String toString() {
        return App.getGson().toJson(this);
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject(toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}