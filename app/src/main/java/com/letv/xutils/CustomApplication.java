package com.letv.xutils;

import android.app.Application;

import org.xutils.x;

/**
 * Created by zhanghuancheng on 17-4-7.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(CustomApplication.this);
        x.Ext.setDebug(true);
    }
}
