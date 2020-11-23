package com.totalcross;

import totalcross.ui.MainWindow;
import totalcross.ui.Label;

import com.totalcross.ui.Initial;

import totalcross.sys.Settings;
public class HelloTC extends MainWindow {
    
    public HelloTC() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
    public void initUI() {
       
        swap(new Initial());
    }
}
