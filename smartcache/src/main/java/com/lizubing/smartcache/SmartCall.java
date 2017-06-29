package com.lizubing.smartcache;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Request;
import retrofit2.Response;

/**
 * Created by Harry.Kong.
 * Time 2017/5/24.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public interface SmartCall<T> {
    /**
     * Asynchronously send the request and notify {@code callback} of its response or if an error
     * occurred talking to the server, creating the request, or processing the response.
     */

    void enqueue(SmartCallBack<T> smartCallBack);

    /**
     * 是否需要缓存
     */
    SmartCall isCache(Boolean isCache);

    /**
     * Returns a runtime {@link Type} that corresponds to the response type specified in your
     * service.
     */
    Type responseType();

    /**
     * Builds a new {@link Request} that is identical to the one that will be dispatched
     * when the {@link SmartCall} is executed/enqueued.
     */
    Request buildRequest();

    /**
     * Create a new, identical call to this one which can be enqueued or executed even if this call
     * has already been.
     */
    SmartCall<T> clone();

    /* ================================================================ */
    /* Now it's time for the blocking methods - which can't be smart :(
    /* ================================================================ */

    /**
     * Synchronously send the request and return its response. NOTE: No smart caching allowed!
     *
     * @throws IOException      if a problem occurred talking to the server.
     * @throws RuntimeException (and subclasses) if an unexpected error occurs creating the request
     *                          or decoding the response.
     */
    Response<T> execute() throws IOException;

    /**
     * Cancel this call. An attempt will be made to cancel in-flight calls, and if the call has not
     * yet been executed it never will be.
     */
    void cancel();




    class CallBack<T> {
    }
}
