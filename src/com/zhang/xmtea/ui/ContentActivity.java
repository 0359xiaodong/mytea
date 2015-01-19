package com.zhang.xmtea.ui;

import java.util.Map;

import com.zhang.xmtea.R;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.help.*;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��ʾ����ҳ�Ļ
 * 
 * @author zhangyg
 * 
 */
public class ContentActivity extends Activity {

	/** ��־���� */
	private final String TAG = "ContentActivity";

	/** �����ҳ�ı��� */
	private TextView textView_content_title;

	/** ����ʱ����Ϣ */
	private TextView textView_content_create_time;

	/** ��Դ */
	private TextView textView_content_source;

	/** ��ʾ��ҳ�Ŀؼ� */
	private WebView webView_content_wap_content;

	/** ��ǰ���ҳ����������õ���ֵ */
	private Map<String, Object> mapValue;

	/** ���ݿ���� */
	SQLiteDataBaseHelper db;

	public ContentActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		initLayout();
		initData();
	}

	/**
	 * ��ʼ������
	 */
	private void initLayout() {
		textView_content_title = (TextView) findViewById(R.id.textView_content_title);
		textView_content_create_time = (TextView) findViewById(R.id.textView_content_create_time);
		textView_content_source = (TextView) findViewById(R.id.textView_content_source);
		webView_content_wap_content = (WebView) findViewById(R.id.webView_content_wap_content);
	}

	/**
	 * ��ʼ������
	 * 
	 */
	private void initData() {
		db = new SQLiteDataBaseHelper(this, "tea");
		Intent intent = getIntent();
		String id = intent.getStringExtra("id");
		if (id != null) {
			new ContentTask(ContentActivity.this).execute(MyConfig.CONTENT
					+ "&id=" + id);
		}
	}

	class ContentTask extends AsyncTask<String, Void, Object> {
		/** �����Ķ��� */
		private Context context;
		/** �������ݵ���ʾ�Ի��� */
		private ProgressDialog pDialog;

		public ContentTask(Context context) {
			this.context = context;
			pDialog = new ProgressDialog(this.context);
			pDialog.setTitle("���Ժ�");
			pDialog.setMessage("���ڼ������Ժ�...");

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog.show();
		}

		/**
		 * �������粢��������
		 */
		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			String jsonString = MyHttpClientHelper.loadTextFromURL(params[0],
					"UTF-8");
			Map<String, Object> map = JsonHelper.jsonStringToMap(jsonString,
					new String[] { "id", "title", "source", "wap_content",
							"create_time" }, "data");
			if (map != null && map.size() != 0) {
				/** ��ӵ����ݿ� */
				String id = map.get("id").toString();
				String title = map.get("title").toString();
				String source = map.get("source").toString();
				String create_time = map.get("create_time").toString();

				String sql = "INSERT INTO tb_teacontents(_id,title,source,create_time,type) values (?,?,?,?,?)";
				boolean flag = db.updataData(sql, new String[] { id, title,
						source, create_time, "1" });// 1��ʾ�Ѿ��鿴����2��ʾ���ղ��ˡ�
				Log.i(TAG, "�Ƿ��Ѿ�����flag==" + flag);
			}
			return map;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				mapValue = (Map<String, Object>) result;
				textView_content_title
						.setText(mapValue.get("title").toString());
				textView_content_source.setText(mapValue.get("source")
						.toString());
				textView_content_create_time.setText(mapValue
						.get("create_time").toString());
				webView_content_wap_content.loadDataWithBaseURL(null, mapValue
						.get("wap_content").toString(), "text/html", "UTF-8",
						null);

			}
			pDialog.dismiss();

		}
	}
	
	/**
	 * �Լ�д��������ť�ĵ���������������ʽд���˿ؼ�������
	 */
	public void clickButton(View view) {
		switch (view.getId()) {
		case R.id.imageView_content_back:
			/* ���� */
			this.finish();
			break;
		case R.id.imageView_content_collect:
			/* �ղ� */
			String sql = "UPDATE tb_teacontents SET type = ? WHERE _id = ?";
			if(mapValue!=null&&!mapValue.equals("")){
				String type = "2";//�ѱ��������Ϊ���ղ���
				String id = mapValue.get("id").toString();
				Log.i(TAG, "type==" + type + ",id==" + id);
				db.updataData(sql, new String[] { type, id });
				Toast.makeText(ContentActivity.this, "�ղسɹ�", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.imageView_content_share:
			/* ���� */
			Intent intent = new Intent(ContentActivity.this,ShareActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);// �������Ƴ�Ч��
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// �ر����ݿ�
		if(db!=null){
			db.destroy();
		}
	}

	
}
