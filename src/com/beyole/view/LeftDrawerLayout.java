package com.beyole.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class LeftDrawerLayout extends ViewGroup {

	private static final int MIN_DRAWER_MARGIN = 64;// dp
	// Mininum Velocity that will be detected as fling
	private static final int MIN_VELOCITY_FLING = 400;// dip per second
	// drawer�븸��������С��߾�
	private int mMinDrawerMargin;
	private View mLeftMenuView;
	private View mContentView;

	private ViewDragHelper mHelper;
	// menu��ʾ���Ĳ���ռ����İٷֱ�
	private float mLeftMenuOnScreen;

	public LeftDrawerLayout(Context context) {
		super(context);
	}

	public LeftDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ����drawer����С�߾�
		final float density = getResources().getDisplayMetrics().density;// �����ܶ�
		final float minVel = MIN_VELOCITY_FLING * density;
		mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);
		mHelper = ViewDragHelper.create(this, 1.0f, new Callback() {

			/**
			 * �����ƶ���ΧΪ-child.getWidth()<=left<=0
			 */
			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
				int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
				return newLeft;
			}

			/**
			 * �����view
			 */
			@Override
			public boolean tryCaptureView(View child, int pointerId) {
				return child == mLeftMenuView;
			}

			/**
			 * ��Ե���
			 */
			@Override
			public void onEdgeDragStarted(int edgeFlags, int pointerId) {
				mHelper.captureChildView(mLeftMenuView, pointerId);
			}

			/**
			 * ��ָ�ͷ�
			 */

			@Override
			public void onViewReleased(View releasedChild, float xvel, float yvel) {
				final int childWidth = releasedChild.getWidth();
				// �ж��ƶ��ǲ����Ѿ�������50%��menu��Ȼ��ߺ����˶��ٶȴ���0
				float offset = (childWidth + releasedChild.getLeft()) * 1.0f / childWidth;
				mHelper.settleCapturedViewAt(xvel > 0 || xvel == 0 && offset > 0.5f ? 0 : -childWidth, releasedChild.getTop());
				// �ػ�
				invalidate();
			}

			/**
			 * ��captureView��λ�÷����ı�ʱ�ص�
			 */
			@Override
			public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
				final int childWidth = changedView.getWidth();
				float offset = (float) ((childWidth + left) / childWidth);
				mLeftMenuOnScreen = offset;
				//offset == 0 ? View.INVISIBLE : 
				changedView.setVisibility(View.VISIBLE);
				invalidate();
			}

			/**
			 * ���ڲ�����view�е���¼�ʱ����onInteceptTouchEvent��Ȼ����ô˷����ж��Ƿ����0��
			 * �ſɲ���TryCaptureView
			 */
			@Override
			public int getViewHorizontalDragRange(View child) {
				return mLeftMenuView == child ? child.getWidth() : 0;
			}
		});
		// ����EdgeTrackΪ���Ե
		mHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
		// ����minVelocity
		mHelper.setMinVelocity(minVel);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSize, heightSize);
		// ������view
		View leftMenuView = getChildAt(1);
		MarginLayoutParams lp = (MarginLayoutParams) leftMenuView.getLayoutParams();
		final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec, mMinDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
		final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec, lp.topMargin + lp.bottomMargin, lp.height);
		leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);

		View contentView = getChildAt(0);
		lp = (MarginLayoutParams) contentView.getLayoutParams();
		final int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
		final int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
		contentView.measure(contentWidthSpec, contentHeightSpec);
		mLeftMenuView = leftMenuView;
		mContentView = contentView;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View menuView = mLeftMenuView;
		View contentView = mContentView;
		MarginLayoutParams lp = (MarginLayoutParams) mContentView.getLayoutParams();
		contentView.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + contentView.getMeasuredWidth(), lp.topMargin + contentView.getMeasuredHeight());
		lp = (MarginLayoutParams) mLeftMenuView.getLayoutParams();
		final int menuWidth = menuView.getMeasuredWidth();
		int childLeft = -menuWidth + (int) (menuWidth * mLeftMenuOnScreen);
		menuView.layout(childLeft, lp.topMargin, childLeft + menuWidth, lp.topMargin + menuView.getMeasuredHeight());
	}

	@Override
	public void computeScroll() {
		if (mHelper.continueSettling(true)) {
			invalidate();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mHelper.processTouchEvent(event);
		return true;
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	public void closeDrawer() {
		View menuView = mLeftMenuView;
		mLeftMenuOnScreen = 0f;
		mHelper.smoothSlideViewTo(menuView, -menuView.getWidth(), menuView.getTop());
	}

	public void openDrawer() {
		View menuView = mLeftMenuView;
		mLeftMenuOnScreen = 1.0f;
		mHelper.smoothSlideViewTo(menuView, 0, menuView.getTop());
	}
}
