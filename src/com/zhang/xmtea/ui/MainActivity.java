package com.zhang.xmtea.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhang.xmtea.R;
import com.zhang.xmtea.adapter.MainFragmentPagerAdapter;
import com.zhang.xmtea.app.MyApplication;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.fragment.ContentFragment;
import com.zhang.xmtea.help.JsonHelper;
import com.zhang.xmtea.help.MyHttpClientHelper;

/**
 * ʵ�������� �ٿ����ż��ص�
 * 
 * @author zhangyg
 * 
 */
public class MainActivity extends FragmentActivity {

	private final String TAG = "MainActivity";

	/** ������ */
	private LinearLayout layout_main_container;

	/** �洢ÿһ��Fragment��viewPager */
	private ViewPager viewPager_main_content;

	/** �ı��������� */
	private TextView[] arr_tabspec;

	/** viewpager������������Դ */
	private List<Fragment> list = null;

	/** ������viewPager������ */
	private MainFragmentPagerAdapter mainFragmentPagerAdapter;

	/** ��������������ݵ�url */
	private String[] urlStr;

	/** ȫ�ֱ���֮ͼƬ����map */
	protected Map<String, Bitmap> cacheImageMap;

	public MainActivity() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * oncreate�������ڵĿ�ʼ
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initdaohang();
	}

	private void initData() {
		cacheImageMap = ((MyApplication) (this.getApplication()))
				.getCacheImageMap();
		urlStr = new String[6];
		urlStr[0] = MyConfig.JSON_LIST_DATA_0;
		urlStr[1] = MyConfig.JSON_LIST_DATA_1;
		urlStr[2] = MyConfig.JSON_LIST_DATA_2;
		urlStr[3] = MyConfig.JSON_LIST_DATA_3;
		urlStr[4] = MyConfig.JSON_LIST_DATA_4;
		urlStr[5] = "";
		new MyTask(MainActivity.this).execute(urlStr[0], urlStr[1], urlStr[2],
				urlStr[3], urlStr[4]);
	}

	private void initdaohang() {

		layout_main_container = (LinearLayout) findViewById(R.id.layout_main_container);
		arr_tabspec = new TextView[layout_main_container.getChildCount()];
		for (int i = 0; i < arr_tabspec.length; i++) {
			TextView view = (TextView) layout_main_container.getChildAt(i);
			if (i == 5) {
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						goSearchActivity();

					}
				});
				break;
			}
			arr_tabspec[i] = view;
			if (i == 0) {
				arr_tabspec[i].setEnabled(false);
			} else {
				arr_tabspec[i].setEnabled(true);
				arr_tabspec[i].setBackgroundColor(Color.parseColor("#ebebeb"));
			}
			arr_tabspec[i].setTextColor(Color.GRAY);
			arr_tabspec[i].setTag(i);
			arr_tabspec[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					viewPager_main_content.setCurrentItem((Integer) v.getTag(),
							false);
				}
			});
		}

	}

	/**
	 * ��ʼ��viewPager����
	 */
	private void initLayout(Map<String, List<Map<String, Object>>> result) {
		Log.i(TAG, "==initLayout()");

		/* viewPager */
		list = new ArrayList<Fragment>();
		for (int i = 0; i < arr_tabspec.length; i++) {
			Fragment fragment = new ContentFragment(urlStr[i],
					(List<Map<String, Object>>) result.get(urlStr[i]),
					cacheImageMap);
          list.add(fragment);
		}
		mainFragmentPagerAdapter = new MainFragmentPagerAdapter(
				getSupportFragmentManager(), list);
		viewPager_main_content = (ViewPager) findViewById(R.id.viewPager_main_content);
		viewPager_main_content.setAdapter(mainFragmentPagerAdapter);
		viewPager_main_content
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						Log.i(TAG, "onPageSelected()==arg0-->" + arg0);
						if (arg0 == 5) {
							goSearchActivity();// ��ת������ҳ��Ȼ��ԭ��ʾ���һ�������ݵ�viewPagerҳ
							arr_tabspec[4].performClick();
							return;
						}
						for (int i = 0; i < arr_tabspec.length - 1; i++) {
							if (i == arg0) {
								arr_tabspec[i].setEnabled(false);
								arr_tabspec[i].setTextColor(Color
										.parseColor("#3d9d01"));
								arr_tabspec[i]
										.setBackgroundResource(R.drawable.tabbg);
							} else {
								arr_tabspec[i].setEnabled(true);
								arr_tabspec[i].setTextColor(Color.GRAY);
								arr_tabspec[i].setBackgroundColor(Color
										.parseColor("#ebebeb"));
							}
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

	}
	
	/**
	 * ��ת������ҳ
	 */
	private void goSearchActivity() {
		Log.i(TAG, "==��ת");
		Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
		intent.putExtra("titleTagStr", "0");// 0��ʾ��ٿ�
		startActivity(intent);

	}
	
	/**
	 * ͨ���첽���ػ����ҳ����
	 * @author zhangyg
	 *
	 */
	class MyTask extends AsyncTask<String, Void, Object>{
		private Context context;
		private ProgressDialog pDialog;
				
		public MyTask(Context context) {
			this.context = context;
			pDialog = new ProgressDialog(this.context);
			pDialog.setTitle("�������");
			pDialog.setMessage("���ڼ���...");
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}
		@Override
		protected Object doInBackground(String... params) {
			Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
			for (int i = 0; i < params.length; i++) {
				String jsonString = MyHttpClientHelper.loadTextFromURL(
						params[i], "UTF-8");// ������ʵõ�����
				List<Map<String, Object>> list = JsonHelper.jsonStringToList(
						jsonString, new String[] { "title", "source",
								"nickname", "create_time", "wap_thumb", "id" },
						"data");
				map.put(params[i], list);
			}
			return map;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			// Log.i(TAG,"result=="+result);
			initLayout((Map<String, List<Map<String, Object>>>) result);
			pDialog.dismiss();

		}
		
	}
	
	/**
	 * ��ʾ�Ƿ��˳��Ի���
	 */
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("�Ƿ��˳�?");
		builder.setTitle("������ʾ");
		builder.setPositiveButton("��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.exit(0);
					}
				});
		builder.setNegativeButton("��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	/**
	 * ���ؼ�˫��
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return true;
		}
		return true;
	}
}
