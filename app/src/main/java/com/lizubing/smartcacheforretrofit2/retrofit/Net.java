
package com.lizubing.smartcacheforretrofit2.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lizubing.smartcache.BasicCaching;
import com.lizubing.smartcache.SmartCallFactory;
import com.lizubing.smartcacheforretrofit2.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:对retrofit二次封装
 */
public class Net {
    public static final String HOST = "http://www.tngou.net/";
    private static Net ourInstance;
    private final Retrofit client;

    private Net() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        SmartCallFactory smartFactory = new SmartCallFactory(BasicCaching.fromCtx(MyApplication.getContext()));
        SmartCallFactory smartFactoryTest = new SmartCallFactory(BasicCaching.fromCtx(MyApplication.getContext()));

        //实现拦截器，设置请求头
        Interceptor interceptorImpl = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request compressedRequest = request.newBuilder()
                        .header("X-Requested-With", "XMLHttpRequest")
                        .build();
                return chain.proceed(compressedRequest);
            }
        };
        //设置OKHttpClient
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptorImpl);//创建OKHttpClient的Builder
        //build OKHttpClient
        OkHttpClient okHttpClient = httpClientBuilder.build();
        client = new Retrofit.Builder()
                .baseUrl(HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(smartFactoryTest)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public synchronized static Net getInstance() {
        if (null == ourInstance) {
            synchronized (Net.class) {
                if (ourInstance == null) {
                    ourInstance = new Net();
                }

            }
        }
        return ourInstance;
    }

    /**
     * 获取网络框架
     *
     * @return
     */
    public Retrofit getRetrofit() {
        return client;
    }

    /**
     * 创建一个业务请求
     *
     * @param convertClass 业务请求接口的class
     * @return
     */
    public <T> T create(Class<T> convertClass) {
        return client.create(convertClass);
    }
}
