package com.lizubing.smartcacheforretrofit2.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry.Kong.
 * Time 2017/5/5.
 * Email kongpengcheng@aliyun.com.
 * Description:
 */
public class MyBaseAdapter extends BaseAdapter {
	@SuppressWarnings("rawtypes")
	protected ArrayList _data = new ArrayList();

	@Override
	public int getCount() {
		return getDataSize();
	}
	/**
	 * data数据的大小
	 * @return
	 */
	public int getDataSize() {
		return _data.size();
	}

	@Override
	public Object getItem(int arg0) {
		if (_data.size() > arg0) {
			return _data.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@SuppressWarnings("rawtypes")
	public void setData(ArrayList data) {
		_data = data;
		notifyDataSetChanged();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getData() {
		return _data == null ? (_data = new ArrayList()) : _data;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(List data) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.addAll(data);
		notifyDataSetChanged();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addItem(Object obj) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.add(obj);
		notifyDataSetChanged();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addItem(int pos, Object obj) {
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.add(pos, obj);
		notifyDataSetChanged();
	}

	public void removeItem(Object obj) {
		_data.remove(obj);
		notifyDataSetChanged();
	}
	public void removeByPosition(int i) {
		_data.remove(i);
		notifyDataSetChanged();
	}
	

	public void clear() {
		_data.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return getRealView(position, convertView, parent);
	}

	protected View getRealView(int position, View convertView, ViewGroup parent) {
		 return null;
	}
}
