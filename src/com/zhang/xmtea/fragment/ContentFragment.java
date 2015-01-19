package com.zhang.xmtea.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhang.xmtea.R;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.fragment.base.BaseListFragment;
import com.zhang.xmtea.help.JsonHelper;
import com.zhang.xmtea.help.MyHttpClientHelper;
import com.zhang.xmtea.weight.MyViewPager;


/**
 * ��ҳ���fragment����չʾ
 * 
 * @author zhangyg
 * 
 */
@SuppressLint("ValidFragment")
public class ContentFragment extends BaseListFragment implements
		View.OnClickListener {
	private final String TAG = "ContentFragment";
	private List<Map<String, Object>> list;
	/** listView������ */
	protected ContentBaseadapter mAdapter;
	private String urlStr;
	/** ȫ�ֱ���֮ͼƬ����map */
	protected Map<String, Bitmap> cacheImageMap;
	/** ����ҳ�� */
	private int page = 1;
	/** fragment��ʶ */
	private int fragmentIndex;
	// ======================������ֻ�е�һҳ�Ż���ֵĹ��ҳ
	/** ��ѡ��ť�飬��ЩС�� */
	private RadioGroup radioGroup_fragment;
	/** ��һҳ��Ҫչʾ�Ĺ�� */
	private MyViewPager viewPager_fragment;
	/** ���viewPager��list */
	private List<ImageView> viewList;
	/** viewpager�������� */
	private MyAdvertisementAdapter myAdvertisementAdapter;
	/** ����ϵĸ��ؼ���Ϊ��ȷ����С����ȡ */
	private RelativeLayout relative_fragment_content;
	/** ����listview�������ص����� */
	private List<Map<String, Object>> jsonList;
	/** �ֲ�ͼƬ�ַ���title���� */
	private String[] titleArr = new String[3];
	/** �ֲ�����ϵ����� */
	private TextView textView_fragment_content_titleName;

	public ContentFragment() {
		fragmentIndex = 1;
	}

	/**
	 * ��Activity�д���fragment�Ĺ��췽��
	 * 
	 * @param urlStr
	 *            ��һ��������ַ
	 * @param list
	 *            ����
	 * @param cacheImageMap
	 *            �����ͼƬ
	 */
	public ContentFragment(String urlStr, List<Map<String, Object>> list,
			Map<String, Bitmap> cacheImageMap) {
		this.list = list;
		this.urlStr = urlStr;
		this.cacheImageMap = cacheImageMap;
		if (urlStr.equals(MyConfig.JSON_LIST_DATA_0)) {
			fragmentIndex = 1;
		} else {
			fragmentIndex = 0;
		}
	}

	/**
	 * ���û�ȡ����
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (list == null) {
			list = new ArrayList<Map<String, Object>>();
		}
		mAdapter = new ContentBaseadapter(getActivity(), list);
	}

	/**
	 * ���ڴ�������
	 * 
	 * ��ʼ�����֣�
	 * 
	 * �õ�listView�ؼ�
	 * 
	 * ����listView����
	 * 
	 * ����listView����
	 * 
	 * ����listview��Adapter
	 * 
	 * ΪAdapter׷������Դ
	 * 
	 * ��adapter��listview�ؼ���
	 * 
	 * ����listView����
	 * 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (urlStr.equals("")) {
			/* ����ǵ���ҳ����ô����ʾlistView���� */
			listview.setPullLoadEnable(false);
		}
		/* ���ؼ� */
		relative_fragment_content = (RelativeLayout) view
				.findViewById(R.id.relative_fragment_content);
		textView_fragment_content_titleName = (TextView) view
				.findViewById(R.id.textView_fragment_content_titleName);
		if (fragmentIndex == 1) {// 1���ǵ�һҳ��0����ʾ����ҳ
			/* ��ʼ�����ֲ�������� */
			new MyTask(0).execute(MyConfig.JSON_URL);
			relative_fragment_content
					.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, getButtonViewPager()));
			/* �õ�viewPager���󣬲���������ҳ�� */
			viewPager_fragment = (MyViewPager) view
					.findViewById(R.id.viewPager_fragment);
			/* ���ø߶� */
			// viewPager_fragment.setLayoutParams(new LinearLayout.LayoutParams(
			// LayoutParams.MATCH_PARENT, getButtonViewPager()));

			viewList = new ArrayList<ImageView>();
			for (int i = 0; i < 3; i++) {
				ImageView imageView = new ImageView(getActivity());
				imageView.setImageResource(R.drawable.ic_launcher);
				imageView.setOnClickListener(this);
				viewList.add(imageView);
			}
			myAdvertisementAdapter = new MyAdvertisementAdapter(viewList);
			viewPager_fragment.setAdapter(new MyAdvertisementAdapter(viewList));
			/* ҳ�滬��ʱ�ļ��� */
			viewPager_fragment
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int arg0) {
							radioGroup_fragment.getChildAt(arg0).performClick();
						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {

						}

						@Override
						public void onPageScrollStateChanged(int arg0) {

						}
					});
			/* �������ɵ�ѡ��ť�����Ѱ�ť�ŵ����� */
			radioGroup_fragment = (RadioGroup) view
					.findViewById(R.id.radioGroup_fragment);
			for (int i = 0; i < radioGroup_fragment.getChildCount(); i++) {
				// RadioButton rButton =new RadioButton(getActivity());// ������ѡ��ť
				RadioButton rButton = (RadioButton) radioGroup_fragment
						.getChildAt(i);
				rButton.setTag(i);// ����tag���±��뻬��������±걣��һ��
				// rButton.setLayoutParams(new LinearLayout.LayoutParams(15,
				// 15));// ���õ�Ŀ��
				rButton.setBackgroundResource(R.drawable.slide_image_dot_c2);//
				// ����״̬����
				rButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));// ȥ����ѡ��ťǰ��ĵ���ɫΪ��
				// radioGroup_fragment.addView(rButton);
			}
			/* ��ѡ��ť�鱻ѡ�еļ��� */
			radioGroup_fragment
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							// Log.i(TAG, "==�õ���ť�����ı䵱ǰ��ť��ʾ����ɫ");
							RadioButton rButton = (RadioButton) group
									.findViewById(checkedId);

							// Log.i(TAG, "==�õ���Ӧ��tagֵ(tagֵ�����±�)");
							int index = (Integer) (rButton.getTag());

							// Log.i(TAG, "�ó���ť�е�tagֵ�������±� index==" + index);
							viewPager_fragment.setCurrentItem(index);

							// �ı䵱ǰ��ʾ������
							textView_fragment_content_titleName
									.setText(titleArr[index]);
						}
					});
			radioGroup_fragment.getChildAt(0).performClick();// Ĭ�����õ�һ��ҳ���ǵ���״̬
		} else {
			relative_fragment_content.setVisibility(View.GONE);
		}
		listview.setXListViewListener(this);// ���ü�������������
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i(TAG, "==onItemClick(),position=" + position + ",id=" + id);
				Log.i(TAG, "==�Զ���adapter�е�ÿһ���������Ū����");
				String idStr = list.get((int) id).get("id").toString();
				goContentActivity(idStr);
			}
		});

		return view;
	}

	/**
	 * ʵ�����һ������Adapter
	 * 
	 * @author zhangyg
	 * 
	 */
	class MyAdvertisementAdapter extends PagerAdapter {
		private List<ImageView> list = null;

		public MyAdvertisementAdapter(List<ImageView> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			if (list != null) {
				return list.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));// ÿһ��itemʵ��������
			return list.get(position);
		}
	}

	/**
	 * �����ֻ��ߴ���ViewPager�߶�
	 * 
	 * @param i
	 */
	private int getButtonViewPager() {
		Display display = getActivity().getWindowManager().getDefaultDisplay(); // Activity#getWindowManager()
		int lenButton = 0;
		lenButton = display.getWidth() * 1 / 2;
		return lenButton;
	}

	/**
	 * fragment��listview������
	 * 
	 * @author zhangyg
	 * 
	 */
	class ContentBaseadapter extends BaseAdapter {
		private Context context;
		private List<Map<String, Object>> list;

		public ContentBaseadapter(Context context,
				List<Map<String, Object>> list) {
			this.context = context;
			this.list = list;
		}

		public void addList(List<Map<String, Object>> list) {
			this.list.addAll(list);
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
				mhHolder.nickname = (TextView) convertView
						.findViewById(R.id.nickname);
				mhHolder.create_time = (TextView) convertView
						.findViewById(R.id.create_time);
				mhHolder.wap_thumb = (ImageView) convertView
						.findViewById(R.id.wap_thumb);
				convertView.setTag(mhHolder);
			} else {
				mhHolder = (ViewHolder) convertView.getTag();
			}

			String title = (String) list.get(position).get("title");
			String source = (String) list.get(position).get("source");
			String create_time = (String) list.get(position).get("create_time");
			String nickname = (String) list.get(position).get("nickname");
			String wap_thumb = list.get(position).get("wap_thumb").toString();

			mhHolder.title.setText(title);
			mhHolder.source.setText(source);
			mhHolder.create_time.setText(create_time);
			mhHolder.nickname.setText(nickname);
			// mhHolder.wap_thumb.setImageResource(R.drawable.ic_launcher);
			/* �ж�Ҫ��Ҫ��ͼƬ��λ�� */
			if (wap_thumb == null || wap_thumb.equals("")) {
				mhHolder.wap_thumb.setVisibility(View.GONE);
			} else {
				mhHolder.wap_thumb.setVisibility(View.INVISIBLE);
			}
			if (cacheImageMap.get(wap_thumb) == null) {
				/* �����ڿ�ʼ�������磬������ͼƬ */
				new MyTask(mhHolder.wap_thumb, wap_thumb, 1).execute(wap_thumb);
			} else {
				mhHolder.wap_thumb.setImageBitmap(cacheImageMap.get(wap_thumb));
				mhHolder.wap_thumb.setVisibility(View.VISIBLE);
			}
			return convertView;
		}

		class ViewHolder {
			private TextView title;
			private TextView source;
			private TextView nickname;
			private TextView create_time;
			private ImageView wap_thumb;
		}
	}

	/** �첽���񣬷��������ȡͼƬ�������� */
	class MyTask extends AsyncTask<String, Integer, Object> {
		/** ��Ϊ����������ͼƬ��ַ�ַ��� */
		private String urlStr;
		/** ��ʾͼƬ�Ŀؼ� */
		private ImageView image;
		/** ������ǣ�0����listviewСͼƬ��1������ҳ���� */
		private int flag;

		public MyTask(ImageView image, String urlStr, int flag) {
			this.urlStr = urlStr;
			this.image = image;
			this.flag = flag;
		}

		public MyTask(int flag) {
			this.flag = flag;
		}

		@Override
		protected Object doInBackground(String... params) {
			Object obj = null;
			byte[] bitmapByte = MyHttpClientHelper.loadByteFromURL(params[0]);
			switch (flag) {
			case 0:
				Log.i(TAG, "==�ֲ�ͼƬjosn�и��ֵ�ַ����");
				jsonTolist(bitmapByte,
						new String[] { "image_s", "id", "title" }, "data", flag);
				break;
			case 1:
				obj = bitmapByte;
				break;
			case 2:
				String jsonString = MyHttpClientHelper.loadTextFromURL(
						params[0], "UTF-8");// ������ʵõ�����
				List<Map<String, Object>> list = JsonHelper.jsonStringToList(
						jsonString, new String[] { "title", "source",
								"nickname", "create_time", "wap_thumb", "id" },
						"data");
				obj = list;

				break;
			default:
				obj = bitmapByte;
				break;
			}
			return obj;
		}

		@Override
		protected void onPostExecute(Object obj) {
			super.onPostExecute(obj);
			if (obj == null) {
				return;
			}
			switch (flag) {
			case 1:
				byte[] result = (byte[]) obj;
				Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0,
						result.length);
				image.setImageBitmap(bitmap);
				image.setVisibility(View.VISIBLE);
				cacheImageMap.put(urlStr, bitmap);
				break;
			case 2:
				List<Map<String, Object>> list = (List<Map<String, Object>>) obj;
				mAdapter.addList(list);
				mAdapter.notifyDataSetChanged();
				if (list.size() == 0) {
					/* ȷ����������û�������ˣ���ôlistView���������������� */
					listview.setPullLoadEnable(false);
				}
				break;
			case 3:
				Log.i(TAG, "==ÿ���䲥ͼƬ�����ݼ���");
				getBitmap(urlStr, (byte[]) obj, 0);
				break;
			case 4:
				Log.i(TAG, "==ÿ���䲥ͼƬ�����ݼ���");
				getBitmap(urlStr, (byte[]) obj, 1);
				break;
			case 5:
				Log.i(TAG, "==ÿ���䲥ͼƬ�����ݼ���");
				getBitmap(urlStr, (byte[]) obj, 2);
				break;
			default:
				break;
			}

		}
	}

	/**
	 * ��ȡ�ֽ����飬ת��bitmapͼƬ��ʾ
	 * 
	 * @param urlStr
	 * @param result
	 * @param i
	 * @return
	 */
	public Bitmap getBitmap(String urlStr, byte[] result, int i) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
		ImageView imageView = (ImageView) viewList.get(i);
		imageView.setImageBitmap(bitmap);
		cacheImageMap.put(urlStr, bitmap);
		myAdvertisementAdapter.notifyDataSetChanged();
		radioGroup_fragment.getChildAt(0).performClick();
		// �ı䵱ǰ��ʾ������ҲΪ��һҳ
		textView_fragment_content_titleName.setText(titleArr[0]);
		return bitmap;
	}

	/**
	 * JSON�������ݷ���
	 * 
	 * @param result
	 * @param jsonStrings
	 * @param string
	 * @param flag
	 * @return
	 */
	public void jsonTolist(byte[] result, String[] jsonStrings, String string,
			int flag) {
		String str = "";
		String urlStr = null;
		try {
			str = new String(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		jsonList = JsonHelper.jsonStringToList(str, jsonStrings, string);
		Log.i(TAG, "jsonList==" + jsonList);
		for (int i = 0; i < jsonList.size(); i++) {
			urlStr = jsonList.get(i).get("image_s").toString();
			String id = jsonList.get(i).get("id").toString();
			String title = jsonList.get(i).get("title").toString();
			titleArr[i] = title;
			Log.i(TAG, "urlStr==" + urlStr);
			ImageView imageView = (ImageView) viewList.get(i);
			imageView.setTag(id);
			if (cacheImageMap.get(urlStr) == null) {
				new MyTask(3 + i).execute(urlStr);// ����ÿһ��ͼƬ
			} else {
				imageView.setImageBitmap(cacheImageMap.get(urlStr));
				myAdvertisementAdapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * ListViewˢ��
	 */
	@Override
	public void onRefresh() {
	}

	/**
	 * ListView����
	 */
	@Override
	public void onLoadMore() {
		new MyTask(2).execute(urlStr + "&page=" + ++page);
	}

	/**
	 * �������ͼƬ�ĵ�������
	 */
	@Override
	public void onClick(View v) {
		Log.i(TAG, "v.getId()==" + v.getId());
		goContentActivity(v.getTag().toString());
	}

}
