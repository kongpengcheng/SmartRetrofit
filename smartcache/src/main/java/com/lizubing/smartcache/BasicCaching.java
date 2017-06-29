package com.lizubing.smartcache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.google.common.hash.Hashing;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import okhttp3.Request;
import retrofit2.Response;
/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 * A basic caching system that stores responses in RAM & disk
 * It uses {@link DiskLruCache} and {@link LruCache} to do the former.
 * CachingSystem对应的实现类，实现了存取缓存方法，使用的是LruCache以及DiskLruCache算法对其进行缓存
 */
public class BasicCaching implements CachingSystem {
    private DiskLruCache diskCache;
    private LruCache<String, Object> memoryCache;

    public BasicCaching(File diskDirectory, long maxDiskSize, int memoryEntries){
        try{
            diskCache = DiskLruCache.open(diskDirectory, 1, 1, maxDiskSize);
        }catch(IOException exc){
            Log.e("SmartCall", "", exc);
            diskCache = null;
        }

        memoryCache = new LruCache<>(memoryEntries);
    }

    private static final long REASONABLE_DISK_SIZE = 1024 * 1024; // 1 MB
    private static final int REASONABLE_MEM_ENTRIES = 50; // 50 entries

    /***
     * Constructs a BasicCaching system using settings that should work for everyone
     * @param context
     * @return
     */
    public static BasicCaching fromCtx(Context context){
        return new BasicCaching(
                new File(context.getCacheDir(), "retrofit_smartcache"),
                REASONABLE_DISK_SIZE,
                REASONABLE_MEM_ENTRIES);
    }

    @Override
    public <T> void addInCache(Response<T> response, byte[] rawResponse) {
        String cacheKey = urlToKey(response.raw().request().url().url());
        memoryCache.put(cacheKey, rawResponse);

        try {
            DiskLruCache.Editor editor = diskCache.edit(urlToKey(response.raw().request().url().url()));
            editor.set(0, new String(rawResponse, Charset.defaultCharset()));
            editor.commit();
            Log.e("SmartCall", "c缓存成功");
        }catch(IOException exc){
            Log.e("SmartCall", "", exc);
        }
    }

    @Override
    public <T> byte[] getFromCache(Request request) {
        String cacheKey = urlToKey(request.url().url());
        byte[] memoryResponse = (byte[]) memoryCache.get(cacheKey);
        if(memoryResponse != null){
            Log.d("SmartCall", "Memory ");
            return memoryResponse;
        }

        try {
            DiskLruCache.Snapshot cacheSnapshot = diskCache.get(cacheKey);
            if(cacheSnapshot != null){
                Log.d("SmartCall", "Disk ");
                return cacheSnapshot.getString(0).getBytes();
            }else{
                Log.d("SmartCall", "Disk hit!");
                return null;
            }
        }catch(IOException exc){
            return null;
        }
    }

    private String urlToKey(URL url){
        return Hashing.sha1().hashString(url.toString(), Charset.defaultCharset()).toString();
    }
}
