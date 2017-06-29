package com.lizubing.smartcacheforretrofit2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lizubing.smartcacheforretrofit2.base.MyBaseAdapter;
import com.lizubing.smartcacheforretrofit2.retrofit.ImageListBean;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class ImageListAdapter extends MyBaseAdapter {
    @Override
    protected View getRealView(int position, View convertView, final ViewGroup parent) {
        ListViewItem item;
        if (null == convertView||null == convertView.getTag()) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_imagelist, null);
            item = new ListViewItem(convertView);
            convertView.setTag(item);
        }else{
            item = (ListViewItem) convertView.getTag();
        }
        final ImageListBean.TngouBean info = (ImageListBean.TngouBean) _data.get(position);
        item.home_title.setText(info.getTitle());
        item.home_img.setAspectRatio(2.0f);
        FrescoHelper.displayImageview(item.home_img, "http://tnfs.tngou.net/image"+info.getImg(), false, 15);
        return convertView;
    }

    public class ListViewItem {
        public SimpleDraweeView home_img;// 图片
        public TextView home_title;// 标题
        public ListViewItem(View v) {
            home_img = (SimpleDraweeView) v.findViewById(R.id.img_icon);
            home_title = (TextView) v.findViewById(R.id.tv_title);
        }
    }
}
