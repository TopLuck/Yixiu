package com.yixiu.carrepair;

import android.app.Application;
import android.util.Log;

import com.base.library.utils.Utils;
import com.yixiu.carrepair.util.JPushUtil;

import io.realm.Realm;

/**
 * Created by liuchengran on 2017/8/21.
 */

public class QApplication extends Application {
    private static QApplication instance;
    private static String TAG = "QApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Utils.init(getApplicationContext());
        Realm.init(this);
        JPushUtil.init();
    }

    /**
     * 单例，返回一个实例
     * @return
     */
    public static QApplication getInstance() {
        if (instance == null) {
            Log.e(TAG, "QApplication is null");

        }
        return instance;
    }
}
