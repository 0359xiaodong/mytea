package com.zhang.xmtea.ui;

import java.util.ArrayList;
import java.util.List;

import com.zhang.xmtea.R;
import com.zhang.xmtea.adapter.GuidePagerAdapter;
import com.zhang.xmtea.config.MyConfig;
import com.zhang.xmtea.help.SharepreferencesHelp;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * ʵ������ҳ
 * @author zhangyg
 *
 */
public class GuideActivity extends Activity {

	/** ��־��� */
	public final String TAG = "GuideActivity";
	
	/** SharedPreferences����,�������ò����״����� */
	private SharepreferencesHelp sph;
	
	/** viewPgaer�ؼ�����ʾ����ͼƬ */
	private ViewPager viewPager_guide;
	
	/** ����ҳС�� */
	private LinearLayout linear_guide_dots;
	
	/** viewPager������ */
	private GuidePagerAdapter guidePagerAdapter;
	
	/** ����������Դ */
	private List<View> views;
	
	/** �ײ�ͼƬ */
	private ImageView[] dots;
	
	/** ��֪������ҳ���� */
	private int pageCount;
	
	
	/** ����ÿ��������һ��״̬���� */
	private int currentIndex;
	
	public GuideActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initData();
		initLayout();
	}
	
	/**
	 * ��ʼ������
	 */
	private void initLayout(){
		/**
		 * ����viewpage
		 */
		viewPager_guide = (ViewPager) findViewById(R.id.viewPager_guide);
		LayoutInflater inflater = LayoutInflater.from(this);
		Class<R.drawable> cls = R.drawable.class;//������Դ�ļ�
		for (int i = 0; i < pageCount; i++) {
			View view = inflater.inflate(R.layout.guide_content, null);
			LinearLayout linear_guide_showImg = (LinearLayout) view
					.findViewById(R.id.linear_guide_showImg);
			int imageId = 0;
			try {
				imageId = cls.getDeclaredField("slide" +(i+1)).getInt(R.drawable.slide1);
			} catch (Exception e){
				imageId = R.drawable.slide1;
				e.printStackTrace();
			}
			linear_guide_showImg.setBackgroundResource(imageId);
			views.add(view);
		}
		guidePagerAdapter = new GuidePagerAdapter(views, this);
		viewPager_guide.setAdapter(guidePagerAdapter);
		viewPager_guide.setOnPageChangeListener( new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				dots[arg0].setEnabled(false);
				dots[currentIndex].setEnabled(true);
				currentIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/* ����С������ */
		linear_guide_dots = (LinearLayout) findViewById(R.id.linear_guide_dots);
		for (int i = 0; i < pageCount; i++) {// ѭ��ȡ��С��ͼƬ
			dots[i] = (ImageView) linear_guide_dots.getChildAt(i);
			if (i == 0) {
				dots[i].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
			} else {
				dots[i].setEnabled(true);// ����Ϊ��ɫ
			}
		}
	}
	
	/**
	 * ��ʼ������
	 */
	private void initData() {
		
		/* ���ñ�ʶ����ʾ���������������ҳ */
		sph = new SharepreferencesHelp(GuideActivity.this);
		sph.putInt(MyConfig.IS_FIRST_RUN, MyConfig.NOT_FIRST);
		/* С������Դ */
		pageCount = 3;
		dots = new ImageView[pageCount];
		currentIndex = 0;
		/* ����viewpager����Դ */
		views = new ArrayList<View>();
	}
}
