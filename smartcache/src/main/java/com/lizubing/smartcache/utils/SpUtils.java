/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package com.lizubing.smartcache.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by Harry.Kong.
 * Time 2017/5/24.
 * Email kongpengcheng@aliyun.com.
 * Description:sp操作类
 */
public class SpUtils {

    public static final String NATION = "nation_sp";

    private SharedPreferences mSp;

    private static SpUtils instance;

    public SpUtils(Context ctx) {
        this(ctx, NATION);
    }

    public SpUtils(Context ctx, String file) {
        mSp = ctx.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public static SpUtils get(Context ctx) {
        if (instance == null) {
            instance = new SpUtils(ctx);
        }
        return instance;
    }

    /**
     * get
     *
     * @param key
     * @param defValue
     * @return
     */
    public Object get(String key, Object defValue) {
        if (defValue instanceof Integer) {
            return mSp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return mSp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return mSp.getLong(key, (Long) defValue);
        } else if (defValue instanceof Set<?>) {
            return mSp.getStringSet(key, (Set<String>) defValue);
        }
        return mSp.getString(key, (String) defValue);
    }

//    /**
//     * @param key
//     * @param defValue
//     * @param <T>
//     * @return
//     */
//    public <T> T get(String key, T defValue) {
//        if (defValue instanceof Integer) {
//            return (T) (Object) mSp.getInt(key, (Integer) defValue);
//        } else if (defValue instanceof Boolean) {
//            return (T) (Object) mSp.getBoolean(key, (Boolean) defValue);
//        } else if (defValue instanceof Float) {
//            return (T) (Object) mSp.getFloat(key, (Float) defValue);
//        } else if (defValue instanceof Long) {
//            return (T) (Object) mSp.getLong(key, (Long) defValue);
//        } else if (defValue instanceof Set<?>) {
//            return (T) (Object) mSp.getStringSet(key, (Set<String>) defValue);
//        }
//        return (T) (Object) mSp.getString(key, (String) defValue);
//    }

    /**
     * put key->value
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        SharedPreferences.Editor editor = mSp.edit();
        putWithoutApply(editor, key, value);
        apply(editor);
    }

    /**
     * put object
     *
     * @param obj
     */
    public void put(Object obj) {
        SharedPreferences.Editor editor = mSp.edit();
        for (Class<?> klass = obj.getClass(); !(Object.class.equals(klass));
             klass = klass.getSuperclass()) {
            Field[] fields = klass.getDeclaredFields();
            for (Field item : fields) {
                try {
                    putWithoutApply(editor, item.getName(), item.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        apply(editor);
    }

    public void putWithoutApply(SharedPreferences.Editor editor, String key, Object value) {
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Set<?>) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            editor.putString(key, (String) value);
        }
    }

    public void clear() {
        apply(mSp.edit().clear());
    }

    public void remove(String key) {
        apply(mSp.edit().remove(key));
    }

    public void apply(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
