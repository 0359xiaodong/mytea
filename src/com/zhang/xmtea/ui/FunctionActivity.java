package com.zhang.xmtea.ui;

import java.util.List;
import java.util.Map;

import com.zhang.xmtea.R;
import com.zhang.xmtea.app.MyApplication;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.fragment.CollectFragment;
import com.zhang.xmtea.fragment.ContentFragment;
import com.zhang.xmtea.fragment.CopyrightFragment;
import com.zhang.xmtea.fragment.FeedbackFragment;
import com.zhang.xmtea.fragment.FunTeaFragment;
import com.zhang.xmtea.fragment.FunTeaFragment.OnMyButtonClickListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.zhang.xmtea.help.*;

/**
 * ����ҳ�����ݻ�ȡ��ת��Ϣ�Ĳ�ͬ�����Ҳ��ͬ��
 * 
 * @author zhangyg
 * 
 */
public class FunctionActivity extends FragmentActivity implements
		OnMyButtonClickListener {
	private final String TAG = "FunctionActivity";
	/** ���Է�fragment�Ŀؼ� */
	private FrameLayout frameLayout_fun;
	/** ������һҳ */
	private ImageView imageView_fun_return;
	/** ��������ʾ������ */
	private TextView textView_fun_title;
	/** ��������ʾ�ı������,0:��ٿƣ�1���ҵ��ղأ�2�����¼��3����Ȩ��Ϣ��4�����������5���������� */
	private String[] titleName = { "��ٿ�", "�ҵ��ղ�", "�����¼", "��Ȩ��Ϣ", "�������", "" };
	/** Fragment������ */
	private FragmentManager fragmentManager;
	/** ÿһ��fragment */
	private Fragment fragment;
	/** ����ͼƬ��map */
	private Map<String, Bitmap> cacheImageMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int titleTag = initData();
		setContentView(R.layout.activity_function);
		frameLayout_fun = (FrameLayout) findViewById(R.id.frameLayout_fun);
		imageView_fun_return = (ImageView) findViewById(R.id.imageView_fun_return);
		imageView_fun_return.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onKeyDown(4,null);
			}
		});
		textView_fun_title = (TextView) findViewById(R.id.textView_fun_title);
		initFragment(titleTag, null);
	}
	/**
	 * �������ؼ���ʱ��fragment��ʼ��ջ
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==4){
			textView_fun_title.setText(titleName[0]); 
			if(fragmentManager.getBackStackEntryCount()==1){
				finish();
			}else{
				fragmentManager.popBackStack();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	private void initFragment(int titleTag, String text) {
		String titleStr = "";
		switch (titleTag) {
		case 0:
			titleStr = titleName[0];
			fragment = new FunTeaFragment(FunctionActivity.this);
			break;
		case 1:
			titleStr = titleName[1];
			fragment = new CollectFragment("2", FunctionActivity.this);
			break;
		case 2:
			titleStr = titleName[2];
			fragment = new CollectFragment("1", FunctionActivity.this);
			break;
		case 3:
			titleStr = titleName[3];
			fragment = new CopyrightFragment();
			break;
		case 4:
			titleStr = titleName[4];
			fragment = new FeedbackFragment();
			break;
		case 5:
			textView_fun_title.setText(text); 
			String urlStr = MyConfig.SEARCH + "&search=" + text;
			new FunTask(FunctionActivity.this, urlStr).execute(urlStr);
			return;

		default:
			break;
		}
		if (fragment != null) {
			fragment.setArguments(getIntent().getExtras());
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.addToBackStack(titleStr);//����ÿ��fragment��ʾ����
			textView_fun_title.setText(titleStr); 
			fragmentTransaction.replace(R.id.frameLayout_fun, fragment)
					.commit();
		}
	}

	/**
	 * ��ʼ�����ݣ���Ҫ�ж�һ��Ӧ����ʾ����fragment
	 */
	private int initData() {
		cacheImageMap = ((MyApplication) this.getApplication())
				.getCacheImageMap();
		fragmentManager = this.getSupportFragmentManager();
		Intent intent = getIntent();
		String titleTagStr = intent.getStringExtra("titleTagStr");
		
		int titleTag = 0;
		try {
			titleTag = Integer.parseInt(titleTagStr);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		}
		return titleTag;
	}

	/**
	 * ��������õ�����ҳ������
	 * 
	 * @author �ٴ���
	 * 
	 */
	class FunTask extends AsyncTask<String, Void, Object> {
		private Context context;
		private ProgressDialog pDialog;
		private String urlStr;

		public FunTask(Context context, String urlStr) {
			this.context = context;
			this.urlStr = urlStr;
			pDialog = new ProgressDialog(this.context);
			pDialog.setTitle("���Ե�");
			pDialog.setMessage("���ڼ���...");
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}

		@Override
		protected Object doInBackground(String... params) {
			String jsonString = MyHttpClientHelper.loadTextFromURL(params[0],
					"UTF-8");// ������ʵõ�����
			List<Map<String, Object>> list = JsonHelper.jsonStringToList(
					jsonString, new String[] { "title", "source", "nickname",
							"create_time", "wap_thumb", "id" }, "data");
			return list;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			fragment = new ContentFragment(urlStr,
					(List<Map<String, Object>>) result, cacheImageMap);
			fragment.setArguments(getIntent().getExtras());
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.replace(R.id.frameLayout_fun, fragment)
					.commit();
			pDialog.dismiss();

		}
	}

	@Override
	public void onMyButtonClick(int titleTag, String text) {
		initFragment(titleTag, text);
	}

}
