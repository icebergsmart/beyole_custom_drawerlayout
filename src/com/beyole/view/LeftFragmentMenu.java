package com.beyole.view;

import com.beyole.adapter.LeftMenuAdapter;
import com.beyole.customdrawerlayout.R;
import com.beyole.entity.MenuItem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LeftFragmentMenu extends ListFragment {

	private static final int SIZE_MENU_ITEM = 3;
	private MenuItem[] mItems = new MenuItem[SIZE_MENU_ITEM];
	private LeftMenuAdapter mAdapter;
	private LayoutInflater mInflater;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		MenuItem menuItem = null;
		for (int i = 0; i < SIZE_MENU_ITEM; i++) {
			menuItem = new MenuItem(false, "menu" + (i + 1), R.drawable.ic_launcher, R.drawable.ic_launcher);
			mItems[i] = menuItem;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.setBackgroundColor(0xffffffff);
		setListAdapter(mAdapter = new LeftMenuAdapter(getActivity(), mItems));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (mOnMenuItemSelectedListener != null) {
			mOnMenuItemSelectedListener.menuItemSelected(((MenuItem) getListAdapter().getItem(position)).getText());
		}
		mAdapter.setSected(position);
	}

	public interface OnMenuItemSelectedListener {
		void menuItemSelected(String title);
	}

	private OnMenuItemSelectedListener mOnMenuItemSelectedListener;

	public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener mOnMenuItemSelectedListener) {
		this.mOnMenuItemSelectedListener = mOnMenuItemSelectedListener;
	}
}
