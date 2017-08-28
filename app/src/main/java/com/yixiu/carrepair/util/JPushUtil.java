package com.yixiu.carrepair.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.yixiu.carrepair.QApplication;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by liuchengran on 2017/8/21.
 */

public class JPushUtil {
    private String TAG = "JPushUtil";
    public JPushUtil() {

    }
    public static void init() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(QApplication.getInstance().getApplicationContext());
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(QApplication.getInstance().getApplicationContext());
        builder.notificationFlags = Notification.FLAG_ONGOING_EVENT
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = 0;
        JPushInterface.setDefaultPushNotificationBuilder(builder);
        //JPushInterface.resumePush(QApplication.getInstance());

    }
    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    public void setAlias(String alias) {
        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    public void logout() {
        //JPushInterface.stopPush(QApplication.getInstance());
        JPushInterface.setAlias(QApplication.getInstance(), null, mAliasCallback);
        NotificationManager notificationManager = (NotificationManager) QApplication.getInstance().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    Set<String> tags = new HashSet<>();
                    tags.add((String) msg.obj);
                    JPushInterface.setAliasAndTags(QApplication.getInstance().getApplicationContext(),
                            (String) msg.obj,
                            tags,
                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}
