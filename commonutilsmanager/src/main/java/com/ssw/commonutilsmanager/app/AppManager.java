package com.ssw.commonutilsmanager.app;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import static com.ssw.commonutilsmanager.common.ConstantList.DEV_MODE;

public class AppManager {
    private static final String TAG = "AppManager";

    private static AppManager appManager;

    public static synchronized AppManager getInstance() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    public boolean isAppRunning(Context context, String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            if (DEV_MODE) {
                e.printStackTrace();
            }
            return "1.0";
        }
    }
}
