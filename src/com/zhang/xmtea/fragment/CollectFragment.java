package com.zhang.xmtea.fragment;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhang.xmtea.R;
import com.zhang.xmtea.fragment.base.BaseListFragment;
import com.zhang.xmtea.help.SQLiteDataBaseHelper;


/**
 * �ղ�ҳ����Ҫ������ʾ���ҵ��ղء��롰�鿴���ʼ�¼"��
 * 
 * @author zhangyg
 * 
 */
@SuppressLint("ValidFragment")
public class CollectFragment extends BaseListFragment {
	/** ��ӡ��־ */
	private final String TAG = "CollectFragment";
	/** listView������Դ */
	private List<Map<String, String>> list = null;
	/** ���ݿ���� */
	private SQLiteDataBaseHelper db;
	/** listView��adapter */
	private CollectAdapter collectAdapter;
	/**��Ҫ��ҳ���*/
	private RelativeLayout relative_fragment_content;

	/**
	 * ��ֹ����
	 */
	public CollectFragment() {

	}

	/**
	 * ���ù��췽��ȷ�����������ֵ
	 * 
	 * @param type
	 *            1����ʾ�������ѯ��ʱ����Ը���������2��ʾ�ղ�
	 */
	public CollectFragment(String type,Context context) {
		db = new SQLiteDataBaseHelper(context, "tea");
		if ("1".equals(type)) {
			String sql = "SELECT * FROM tb_teacontents";
			list = db.SelectData(sql, null);
		} else if ("2".equals(type)) {
			String sql = "SELECT * FROM tb_teacontents WHERE type = ?";
			list = db.SelectData(sql, new String[]{"2"});
		}
		
	}

	/**
	 * ��ʼ����
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);// �ص�����Ĺ��췽��������listview���Լ�view���ֶԻ�
		relative_fragment_content = (RelativeLayout) view
				.findViewById(R.id.relative_fragment_content);
		relative_fragment_content.setVisibility(View.GONE);
		listview.setPullLoadEnable(false);//��������
		if (list != null&&list.size()!=0) {
			collectAdapter = new CollectAdapter(getActivity(), list);
			listview.setAdapter(collectAdapter);
			listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String idStr = list.get((int) arg3).get("_id").toString();
					goContentActivity( idStr);

				}
			});
		} else {

		}
		return view;// ���շ������view������ɱ���������

	}

	class CollectAdapter extends BaseAdapter {
		private Context context;
		private List<Map<String, String>> list;

		public CollectAdapter(Context context, List<Map<String, String>> list) {
			this.context = context;
			this.list = list;
		}


		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mhHolder;
			if (convertView == null) {
				mhHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_listview, null);
				mhHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				mhHolder.source = (TextView) convertView
						.findViewById(R.id.source);
				mhHolder.create_time = (TextView) convertView
						.findViewById(R.id.create_time);
				convertView.setTag(mhHolder);
			} else {
				mhHolder = (ViewHolder) convertView.getTag();
			}

			String title = (String) list.get(position).get("title");
			String source = (String) list.get(position).get("source");
			String create_time = (String) list.get(position).get("create_time");

			mhHolder.title.setText(title);
			mhHolder.source.setText(source);
			mhHolder.create_time.setText(create_time);
			return convertView;
		}

		class ViewHolder {
			private TextView title;
			private TextView source;
			private TextView create_time;
		}

	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//�ر����ݿ�
		if(db!=null){
			db.destroy();
		}
	}
}
