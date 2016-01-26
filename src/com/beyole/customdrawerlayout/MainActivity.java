package com.beyole.customdrawerlayout;

import com.beyole.view.LeftDrawerLayout;
import com.beyole.view.LeftFragmentMenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private LeftFragmentMenu mMenuFragment;
	private LeftDrawerLayout drawerLayout;
	private TextView mContentTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		drawerLayout = (LeftDrawerLayout) findViewById(R.id.id_leftdrawer);
		mContentTv = (TextView) findViewById(R.id.id_content_tv);
		FragmentManager fm = getSupportFragmentManager();
		mMenuFragment = (LeftFragmentMenu) fm.findFragmentById(R.id.id_content_menu);
		if (mMenuFragment == null) {
			fm.beginTransaction().add(R.id.id_content_menu, mMenuFragment = new LeftFragmentMenu()).commit();
		}
		mMenuFragment.setOnMenuItemSelectedListener(new LeftFragmentMenu.OnMenuItemSelectedListener() {

			@Override
			public void menuItemSelected(String title) {
				drawerLayout.closeDrawer();
				mContentTv.setText(title);
			}
		});
	}

}
