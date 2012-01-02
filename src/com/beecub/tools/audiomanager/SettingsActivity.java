package com.beecub.tools.audiomanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getPreferenceManager().setSharedPreferencesName(
                AudioManagerActivity.PREFS_NAME);
        addPreferencesFromResource(R.xml.settings);
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(
                this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
    }
}