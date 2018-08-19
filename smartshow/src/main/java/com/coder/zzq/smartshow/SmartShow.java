package com.coder.zzq.smartshow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.snackbar.SnackbarSetting;
import com.coder.zzq.smartshow.toast.ToastSetting;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class SmartShow {

    private static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
        ensureContextNotNull();
        sApplication.registerActivityLifecycleCallbacks(new ActivityLifecycleCallback() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                ActivityStack.push(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                setupDismissOnLeave();
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                ActivityStack.pop(activity);
                SmartSnackbar.destroy(activity);
            }
        });
    }

    private static void setupDismissOnLeave() {
        if (SmartToast.isDismissOnLeave()) {
            SmartToast.dismiss();
        }
        if (SmartSnackbar.isDismissOnLeave()) {
            SmartSnackbar.dismiss();
        }
    }

    private static void ensureContextNotNull() {
        if (sApplication == null) {
            throw new NullPointerException("初始化SmartShow的context不可为null！");
        }
    }

    public static ToastSetting toastSetting() {
        ensureContextNotNull();
        return SmartToast.init(sApplication);
    }


    public static SnackbarSetting snackbarSetting() {
        ensureContextNotNull();
        return SmartSnackbar.init(sApplication);
    }


}
