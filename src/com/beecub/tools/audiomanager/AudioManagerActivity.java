package com.beecub.tools.audiomanager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class AudioManagerActivity extends Activity {
    
    public static final String PREFS_NAME = "AudioManager";
    
    private SeekBar mSeekBar1;
    private SeekBar mSeekBar2;
    private SeekBar mSeekBar3;
    private SeekBar mSeekBar4;
    private SeekBar mSeekBar5;
    private SeekBar mSeekBar6;
    
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private TextView mTextView6;
    
//    private CheckBox mCheckBox;
    
    private AudioManager mAudioManager;
    
    private GoogleAnalyticsTracker tracker;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        mCheckBox = (CheckBox) findViewById(R.id.checkBox1);
        
        setupSeekBars();
        setupTextViews();
        
        tracker = GoogleAnalyticsTracker.getInstance();        
        tracker.startNewSession("UA-28054420-1", this);        
        tracker.trackPageView("/GOLauncherFontsHomeScreen");
        PackageInfo pinfo;
        int versionNumber = 0;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = pinfo.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tracker.trackPageView("/AudioManagerVersion" + versionNumber);
        tracker.dispatch();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        tracker.dispatch();
        tracker.stopSession();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.settings:
//                Intent settingsIntent = new Intent();
//                settingsIntent.setClass(this, SettingsActivity.class);
//                startActivity(settingsIntent);
//                return true;
            case R.id.quit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void updateSeekBars() {
        mSeekBar1.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        mSeekBar2.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        mSeekBar3.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
        mSeekBar4.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        mSeekBar5.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        mSeekBar6.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
        
        updateTextViews();
    }
    
    public void updateTextViews() {
        mTextView1.setText(mSeekBar1.getProgress() + "/" + mSeekBar1.getMax());
        mTextView2.setText(mSeekBar2.getProgress() + "/" + mSeekBar2.getMax());
        mTextView3.setText(mSeekBar3.getProgress() + "/" + mSeekBar3.getMax());
        mTextView4.setText(mSeekBar4.getProgress() + "/" + mSeekBar4.getMax());
        mTextView5.setText(mSeekBar5.getProgress() + "/" + mSeekBar5.getMax());
        mTextView6.setText(mSeekBar6.getProgress() + "/" + mSeekBar6.getMax());
    }
    
    public void setupTextViews() {
        mTextView1 = (TextView) findViewById(R.id.seekBar1Text);
        mTextView2 = (TextView) findViewById(R.id.seekBar2Text);
        mTextView3 = (TextView) findViewById(R.id.seekBar3Text);
        mTextView4 = (TextView) findViewById(R.id.seekBar4Text);
        mTextView5 = (TextView) findViewById(R.id.seekBar5Text);
        mTextView6 = (TextView) findViewById(R.id.seekBar6Text);
        updateTextViews();
    }
    
    public void setupSeekBars() {
        mSeekBar1 = (SeekBar)findViewById(R.id.seekBar1);
        mSeekBar1.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
        mSeekBar1.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        mSeekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() { 
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                
                if(seekBar.getProgress() == 0) {
                    mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                    if(mCheckBox.isChecked())
//                        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                    else
//                        mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                }
                
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
        
        mSeekBar2 = (SeekBar)findViewById(R.id.seekBar2);
        mSeekBar2.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mSeekBar2.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        mSeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
        
        mSeekBar3 = (SeekBar)findViewById(R.id.seekBar3);
        mSeekBar3.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
        mSeekBar3.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_RING));
        mSeekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mAudioManager.setStreamVolume(AudioManager.STREAM_RING, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
        
        mSeekBar4 = (SeekBar)findViewById(R.id.seekBar4);
        mSeekBar4.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
        mSeekBar4.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        mSeekBar4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
        
        mSeekBar5 = (SeekBar)findViewById(R.id.seekBar5);
        mSeekBar5.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
        mSeekBar5.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_ALARM));
        mSeekBar5.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_ALARM, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
        
        mSeekBar6 = (SeekBar)findViewById(R.id.seekBar6); 
        mSeekBar6.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
        mSeekBar6.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));
        mSeekBar6.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, seekBar.getProgress(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                updateSeekBars();
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
    }
}