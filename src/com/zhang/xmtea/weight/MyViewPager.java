package com.zhang.xmtea.weight;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	
	private final String TAG = "MyViewPager";
	
	/** ����ʱ���µĵ� **/
	PointF downP = new PointF();
	
	/** ����ʱ��ǰ�ĵ� **/
	PointF curP = new PointF();
	
	OnSingleTouchListener onSingleTouchListener;
	
	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// �����ش����¼������λ�õ�ʱ�򣬷���true��
		// ˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// ÿ�ν���onTouch�¼�����¼��ǰ�İ��µ�����
		curP.x = arg0.getX();
		curP.y = arg0.getY();

		if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
			// ��¼����ʱ�������
			// �мǲ����� downP = curP �������ڸı�curP��ʱ��downPҲ��ı�
			downP.x = arg0.getX();
			downP.y = arg0.getY();
			// �˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if (arg0.getAction() == MotionEvent.ACTION_MOVE) {
			// �˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
			getParent().requestDisallowInterceptTouchEvent(true);
		}

		if (arg0.getAction() == MotionEvent.ACTION_UP) {
			// ��upʱ�ж��Ƿ��º����ֵ�����Ϊһ����
			// �����һ���㣬��ִ�е���¼����������Լ�д�ĵ���¼���������onclick
			if (downP.x == curP.x && downP.y == curP.y) {
				onSingleTouch();
				
				return false;
			}
		}

		// return super.onTouchEvent(arg0);
		return super.onTouchEvent(arg0);
	}
	/**
	 * ����
	 */
	public void onSingleTouch() {
		Log.i(TAG, "����");
		if (onSingleTouchListener != null) {
			onSingleTouchListener.onSingleTouch();
		}
	}
	/**
	 * ��������¼��ӿ�
	 */
	public interface OnSingleTouchListener {
		public void onSingleTouch();
	}
	/**
	 * 
	 * @param onSingleTouchListener
	 */
	public void setOnSingleTouchListener(
			OnSingleTouchListener onSingleTouchListener) {
		this.onSingleTouchListener = onSingleTouchListener;
	}
}
