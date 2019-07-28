package com.lrsoft.xnovelreader;

import android.content.Context;
import android.content.SharedPreferences;

public class AppDefaultSetting {
    private boolean useNightMode = false;
    private int slipDistance = 400;
    private Context context;
    public AppDefaultSetting(Context context){
        this.context = context;
        updateConfig();
    }
    public void updateConfig(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("XNRConfig.cfg",Context.MODE_PRIVATE);
        useNightMode = sharedPreferences.getBoolean("useNightMode", false);
        slipDistance = sharedPreferences.getInt("slipDistance", 400);
    }
    public boolean getUseNightMode(){
        return useNightMode;
    }
    public int getSlipDistance(){
        return slipDistance;
    }
    public void setUseNightMode(boolean use){
        SharedPreferences.Editor editor = context.getSharedPreferences("XNRConfig.cfg",Context.MODE_PRIVATE).edit();
        editor.putBoolean("useNightMode", use);
        editor.commit();
        useNightMode = use;
    }
    public void setslipDistance(int dist){
        SharedPreferences.Editor editor = context.getSharedPreferences("XNRConfig.cfg",Context.MODE_PRIVATE).edit();
        editor.putInt("slipDistance",dist);
        editor.commit();
        slipDistance = dist;
    }
}
