package com.lizubing.smartcacheforretrofit2;

import android.content.Context;

import com.facebook.imagepipeline.core.ImagePipelineConfig;

import okhttp3.OkHttpClient;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class MyOkHttpImagePipelineConfigFactory {

    public static ImagePipelineConfig.Builder newBuilder(Context context, OkHttpClient okHttpClient) {
        return ImagePipelineConfig.newBuilder(context).setNetworkFetcher(new MyOkHttpNetworkFetcher(okHttpClient));
    }

}
