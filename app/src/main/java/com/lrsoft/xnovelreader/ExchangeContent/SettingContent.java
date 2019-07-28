package com.lrsoft.xnovelreader.ExchangeContent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.lrsoft.xnovelreader.AppDefaultSetting;
import com.lrsoft.xnovelreader.MainActivity;
import com.lrsoft.xnovelreader.R;

public class SettingContent extends Fragment {
    private MainActivity callbackObj;
    public SettingContent(MainActivity callback){
        callbackObj = callback;
    }
    private AppDefaultSetting appDefaultSetting;
    private EditText splitDist = null;
    private SwitchCompat nightMode = null;
    private ConstraintLayout totalPlane = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appDefaultSetting = new AppDefaultSetting(getActivity());
        View view = inflater.inflate(R.layout.exchange_setting_content, container, false);
        totalPlane = view.findViewById(R.id.setting_total_plane);
        nightMode = view.findViewById(R.id.setting_nightMode_switch);
        splitDist = view.findViewById(R.id.setting_slip_textbox);
        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AppDefaultSetting appDefaultSetting = new AppDefaultSetting(getActivity());
                appDefaultSetting.setUseNightMode(b);
            }
        });
        splitDist.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                try{
                    if(!splitDist.getText().toString().isEmpty()){
                        int slipValue = Integer.parseInt(splitDist.getText().toString());
                        appDefaultSetting.setslipDistance(slipValue);
                    }
                }catch (Exception expt){}
            }
        });
        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        appDefaultSetting.updateConfig();
        if(nightMode!=null){
            nightMode.setChecked(appDefaultSetting.getUseNightMode());
        }
        if(splitDist!=null){
            splitDist.setText(String.valueOf(appDefaultSetting.getSlipDistance()));
        }
    }
}
