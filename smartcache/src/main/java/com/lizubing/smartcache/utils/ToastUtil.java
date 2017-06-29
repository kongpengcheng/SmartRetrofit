package com.lizubing.smartcache.utils;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Field;


/**
 * Created by Harry.Kong.
 * Time 2017/5/24.
 * Email kongpengcheng@aliyun.com.
 * Description:弹框toast
 */
public class ToastUtil {

    public static Toast toast;

    public static void showToastShort(String toastText) {
        if (toast == null) {
            toast = Toast.makeText(App.get(), "", Toast.LENGTH_SHORT);
            updToastTextSize(toast);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setText(toastText + "");
        toast.show();
    }

    public static void showToastLong(String toastText) {
        showToastShort(toastText);
    }

    public static void showToastCenter(String toastText) {
        showToastShort(toastText);
    }

    public static void showToastCenterShort(String toastText) {
        showToastShort(toastText);
    }

    public static void showToastShort(int res) {
        showToastShort(App.get().getString(res));
    }

    public static void showToastLong(int res) {
        showToastLong(App.get().getString(res));
    }

    private static void updToastTextSize(Toast toast) {
        try {
            Field f = toast.getClass().getDeclaredField("mNextView");
            f.setAccessible(true);
            ViewGroup view = (ViewGroup) f.get(toast);
            if (view == null) {
                return;
            }
            TextView tv = (TextView) view.getChildAt(0);
            if (tv == null) {
                return;
            }
            tv.setTextSize(50);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}