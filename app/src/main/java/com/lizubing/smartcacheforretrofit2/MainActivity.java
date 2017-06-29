package com.lizubing.smartcacheforretrofit2;

import android.Manifest;
import android.os.Build;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.lizubing.smartcache.SmartCallBack;
import com.lizubing.smartcacheforretrofit2.retrofit.ImageListBean;
import com.lizubing.smartcacheforretrofit2.retrofit.MeoHttp;
import com.lizubing.smartcacheforretrofit2.retrofit.Net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ImageListAdapter adapter;
    private String url = "http://58.56.128.105:8080/rrs/center?method=rrs.gis.get.regionNew&timestamp=1495874699574&access_token=YTcwNWJmOGItMmNjYS00OTQ5LWE4YTMtYjAzMGI5YmFiZTY4&page_size=10000";
    private String urldd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urldd = url.replaceAll("&timestamp=\\d{10,13}", "");
      //  String newStr = url.substring(url.indexOf("&timestamp"), url.indexOf("&access_token"));
        Log.d("url------------>", urldd);
        initView();
        initData();
        //  requestMultiplePermissions();

    }

    private static final int REQUEST_CODE = 111;

    private void requestMultiplePermissions() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
//            int grantResult = grantResults[0];
//            boolean granted = grantResult == PackageManager.PERMISSION_GRANTED;

        }
    }

    Boolean isTrue = true;

    private void initData() {
//        Net.getInstance().create(MeoHttp.class).getImageList().isCache(isTrue).enqueue(new Callback<ImageListBean>() {
//        @Override
//        public void onResponse(Call<ImageListBean> call, Response<ImageListBean> response) {
//            Log.d("success", "成功");
//            Log.d("success", " 是否缓存" );
//            adapter.setData(response.body().getTngou());
//        }
//
//        @Override
//        public void onFailure(Call<ImageListBean> call, Throwable t) {
//
//        }
//    });


        Net.getInstance().create(MeoHttp.class).getImageList().isCache(isTrue).enqueue(new SmartCallBack<ImageListBean>() {
            @Override
            public void onResponse(Call<ImageListBean> call, Response<ImageListBean> response, Boolean iscatch) {
                Log.d("success----》", "成功");
                Log.d("success----》", " 是否缓存数据" + iscatch);
                adapter.setData(response.body().getTngou());

            }

            @Override
            public void onFailure(Call<ImageListBean> call, Throwable t) {

            }
        });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
        adapter = new ImageListAdapter();
        listView.setAdapter(adapter);
    }


}
