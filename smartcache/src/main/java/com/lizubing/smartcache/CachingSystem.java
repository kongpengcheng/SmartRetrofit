package com.lizubing.smartcache;


import okhttp3.Request;
import retrofit2.Response;
/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
/*接口里面就两方法存缓存以及获取缓存*/
public interface CachingSystem {
    <T> void addInCache(Response<T> response, byte[] rawResponse);

    <T> byte[] getFromCache(Request request);
}
