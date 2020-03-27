package com.chann.crystalshineproject.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.chann.crystalshineproject.service.PreferenceUtility.LOGGED_IN_PREF;


public class SaveSharedPreference
{
    private String token;
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

}
