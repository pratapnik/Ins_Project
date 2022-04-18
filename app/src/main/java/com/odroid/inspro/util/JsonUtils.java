package com.odroid.inspro.util;


import com.google.gson.Gson;

public class JsonUtils {

    private static Gson gsonInstance = null;


    public static Gson getGson() {
        if (gsonInstance == null) {
            gsonInstance = new Gson();
        }
        return gsonInstance;
    }


}