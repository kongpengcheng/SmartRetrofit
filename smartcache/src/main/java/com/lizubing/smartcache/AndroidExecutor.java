package com.lizubing.smartcache;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
/*线程池里面封装了一个handler为了post数据到主线程*/
class AndroidExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable r) {
        handler.post(r);
    }
}