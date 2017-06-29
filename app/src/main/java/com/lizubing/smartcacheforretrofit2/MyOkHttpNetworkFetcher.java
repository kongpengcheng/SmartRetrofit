package com.lizubing.smartcacheforretrofit2;

import android.net.Uri;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.BaseNetworkFetcher;
import com.facebook.imagepipeline.producers.BaseProducerContextCallbacks;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.ProducerContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class MyOkHttpNetworkFetcher extends BaseNetworkFetcher<MyOkHttpNetworkFetcher.MyOkHttpNetworkFetchState> {

    public static class MyOkHttpNetworkFetchState extends FetchState {

        public long submitTime;
        public long responseTime;
        public long fetchCompleteTime;

        public MyOkHttpNetworkFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
            super(consumer, context);
        }

    }

    private static final String TAG = "MyOkHttpNetworkFetcher";

    private static final String QUEUE_TIME = "queue_time";
    private static final String FETCH_TIME = "fetch_time";
    private static final String TOTAL_TIME = "total_time";
    private static final String IMAGE_SIZE = "image_size";

    private final OkHttpClient mOkHttpClient;

    private Executor mCancellationExecutor;

    public MyOkHttpNetworkFetcher(OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
        mCancellationExecutor = okHttpClient.dispatcher().executorService();
    }

    @Override
    public MyOkHttpNetworkFetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        return new MyOkHttpNetworkFetchState(consumer, producerContext);
    }

    @Override
    public void fetch(final MyOkHttpNetworkFetchState fetchState, final Callback callback) {
        fetchState.submitTime = SystemClock.elapsedRealtime();

        final Uri uri = fetchState.getUri();

        final Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().noStore().build())
                .url(uri.toString())
                .get()
                .build();

        final Call call = mOkHttpClient.newCall(request);

        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            @Override
            public void onCancellationRequested() {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    call.cancel();
                } else {
                    mCancellationExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            call.cancel();
                        }
                    });
                }
            }
        });

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleException(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fetchState.responseTime = SystemClock.elapsedRealtime();
                final ResponseBody body = response.body();
                try {
                    long contentLength = body.contentLength();
                    if (contentLength < 0) {
                        contentLength = 0;
                    }
                    callback.onResponse(body.byteStream(), (int) contentLength);
                } catch (Exception e) {
                    handleException(call, e, callback);
                } finally {
                    try {
                        body.close();
                    } catch (Exception e) {
                        Log.w(TAG, "Exception when closing response body", e);
                    }
                }
            }
        });

    }

    @Override
    public void onFetchCompletion(MyOkHttpNetworkFetchState fetchState, int byteSize) {
        fetchState.fetchCompleteTime = SystemClock.elapsedRealtime();
    }

    @Override
    public Map<String, String> getExtraMap(MyOkHttpNetworkFetchState fetchState, int byteSize) {
        Map<String, String> extraMap = new HashMap<>(4);
        extraMap.put(QUEUE_TIME, Long.toString(fetchState.responseTime - fetchState.submitTime));
        extraMap.put(FETCH_TIME, Long.toString(fetchState.fetchCompleteTime - fetchState.responseTime));
        extraMap.put(TOTAL_TIME, Long.toString(fetchState.fetchCompleteTime - fetchState.submitTime));
        extraMap.put(IMAGE_SIZE, Integer.toString(byteSize));
        return extraMap;
    }

    /**
     * Handles exceptions.
     * <p>
     * <p> OkHttp notifies callers of cancellations via an IOException. If IOException is caught
     * after request cancellation, then the exception is interpreted as successful cancellation
     * and onCancellation is called. Otherwise onFailure is called.
     */
    private void handleException(final Call call, final Exception e, final Callback callback) {
        if (call.isCanceled()) {
            callback.onCancellation();
        } else {
            callback.onFailure(e);
        }
    }

}