package com.lizubing.smartcache.utils;

import android.app.Activity;

import java.util.Stack;
/**
 * Created by Harry.Kong.
 * Time 2017/5/24.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class AppManager {

    private Stack<Activity> mActivities = new Stack<>();
    private static AppManager sInstance;

    public static AppManager getInstance() {
        if (sInstance == null) {
            sInstance = new AppManager();
        }
        return sInstance;
    }

    private AppManager() {
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void add(Activity activity) {
        mActivities.add(activity);
    }

    /**
     * 移除当前activity
     */
    public void pop() {
        if (mActivities.isEmpty()) return;

        Activity a = mActivities.lastElement();
        remove(a);
    }

    /**
     * 移除activity
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (activity != null) {
            mActivities.remove(activity);
        }
    }

    /**
     * 移除指定activity
     *
     * @param activity
     */
    public void finish(Class<?> activity) {
        if (mActivities.isEmpty()) return;
        for (Activity a : mActivities) {
            if (a.getClass().equals(activity)) {
                a.finish();
                remove(a);
                return;
            }
        }
    }

    /**
     * 获取指定activity
     *
     * @param klass
     * @return
     */
    public Activity get(Class<?> klass) {
        for (Activity a : mActivities) {
            if (a.getClass().equals(klass)) return a;
        }

        return null;
    }

    /**
     * 获取所有activity
     */
    public Stack<Activity> getAll() {
        return mActivities;
    }

    /**
     * 获取栈顶的activity
     *
     * @return
     */
    public Activity top() {
        if (mActivities.isEmpty()) {
            return null;
        }
        return mActivities.peek();
    }
}
