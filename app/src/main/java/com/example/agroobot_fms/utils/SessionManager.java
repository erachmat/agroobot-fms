package com.example.agroobot_fms.utils;

import static android.content.Context.MODE_PRIVATE;

import static com.example.agroobot_fms.utils.Constants.USER_TOKEN;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.agroobot_fms.R;

public class SessionManager {

    Context context;

    public SessionManager(Context context) {
        this.context = context;
    }

    SharedPreferences sharedPreferences = context.getSharedPreferences(
            "PREF", MODE_PRIVATE);

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String fetchAuthToken() {
        return sharedPreferences.getString(USER_TOKEN, null);
    }

}
