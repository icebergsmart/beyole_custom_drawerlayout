package com.beyole.adapter;

import com.beyole.customdrawerlayout.R;
import com.beyole.entity.MenuItem;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuAdapter extends ArrayAdapter<MenuItem> {

	private LayoutInflater mInflater;
	private int mSelected;

	public LeftMenuAdapter(Context context, MenuItem[] objects) {
		super(context, -1, objects);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_left_menu, parent, false);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.id_item_icon);
		TextView title = (TextView) convertView.findViewById(R.id.id_item_title);
		title.setText(getItem(position).getText());
		iv.setImageResource(getItem(position).getIcon());
		convertView.setBackgroundColor(Color.TRANSPARENT);
		return convertView;
	}

	public void setSected(int position) {
		this.mSelected = position;
		notifyDataSetChanged();
	}

}
