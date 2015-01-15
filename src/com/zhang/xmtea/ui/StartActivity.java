package com.zhang.xmtea.ui;

import com.zhang.xmtea.R;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.help.SharepreferencesHelp;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;

/**
 * 
 * ʵ��ÿ�����������ʾ3����棬���ж��Ƿ��ǵ�һ�����У��Ƿ�Ҫ��������ҳ��
 * 
 * @author zhangyg
 * 
 */
public class StartActivity extends Activity {

	/** ��־��� */
	private final String TAG = "StartActivity";
	/** SharedPreferences����,�����ж��Ƿ����״����� */
	private SharepreferencesHelp sph;
	/** ��Ҫ���ܣ���ת����ͬ�Ľ��� */
	private Handler mHandler;

	/**
	 * ���������ʼ
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		initData();
	}

	/**
	 * ���ݳ�ʼ��
	 */
	private void initData() {
		Log.i(TAG, "==initData()");
		sph = new SharepreferencesHelp(StartActivity.this);
		mHandler = new Handler() {
			/**
			 * 0����ת���û���ҳ��1����ת������ҳ��
			 */
			public void handleMessage(android.os.Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {
				case 0:
					goActivity(MainActivity.class);
					break;
				case 1:
					goActivity(GuideActivity.class);
					break;
				}
			};
		};
		int isFirst = sph.getInt(MyConfig.IS_FIRST_RUN);

		if (isFirst == MyConfig.NOT_FIRST) {
			mHandler.sendEmptyMessageDelayed(0, 3000);
		} else {
			mHandler.sendEmptyMessageDelayed(1, 3000);
		}
	}

	/**
	 * ·ת��ĳҳ��
	 */
	protected void goActivity(Class<?> cls) {

		Intent intent = new Intent(this, cls);
		startActivity(intent);
		this.finish();
	}

}
