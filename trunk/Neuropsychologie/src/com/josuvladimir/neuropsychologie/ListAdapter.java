package com.josuvladimir.neuropsychologie;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	List<String> mStrings;
	Context	mContext;
	LayoutInflater mInflater;
	public ListAdapter(Context context, List<String> strings) {
		mContext = context;
		mStrings = strings;
		mInflater = LayoutInflater.from(mContext);
	}

	public ListAdapter(Context context, String[] strings) {
		mContext = context;
		mStrings = Arrays.asList(strings);
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.text_list_item);
		textView.setText(mStrings.get(position));
		return convertView;
	}
}
