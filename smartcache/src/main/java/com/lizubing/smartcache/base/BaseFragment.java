package com.lizubing.smartcache.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by qibin on 2016/7/21.
 */

public abstract class BaseFragment extends Fragment {

    private long mCurrentMs = System.currentTimeMillis();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        setup(rootView, savedInstanceState);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        HookUtil.hookClick(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgentFixed.onPageStart(TAG);
//        //海极网页面统计
//        MobEvent.onEventStart(getActivity(), EventIdConst.PAGE_LOAD_DURATION, TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgentFixed.onPageEnd(TAG);
//        //海极网页面统计
//        MobEvent.onEventEnd(getActivity(), EventIdConst.PAGE_LOAD_DURATION, TAG);
    }


    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(getActivity(), activity), requestCode);
    }

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
//        ShowLoadingUtil.onDestory();
        super.onDestroy();
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }
}
