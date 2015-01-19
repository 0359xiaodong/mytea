package com.zhang.xmtea.fragment.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zhang.xmtea.R;
import com.zhang.xmtea.ui.ContentActivity;
import com.zhang.xmtea.weight.XListView;
import com.zhang.xmtea.weight.XListView.IXListViewListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * ���ڹ�ͬ��fragment�У��о�һ�¹�ͬ�ģ�����Ҫʵ��һ�������������صģ��Զ����listView���Լ����ļ���
 * 
 * �򵥵�˵��������ҪlistView��fragment���ͼ̳����
 * 
 * @author zhangyg
 * 
 */
public class BaseListFragment extends Fragment implements IXListViewListener{

	private final String TAG = "BaseListFragment";
	
	/** �Զ���listView */
	protected XListView listview;
	
	/** �����������listview���� */
	protected View view;
	
	/** inflater��䲼���� */
	LayoutInflater mInflater;
	
	/** ������ */
	protected boolean mIsScroll = false;
	
	public ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mInflater = inflater;
		view = inflater.inflate(R.layout.fragment_content, null);
		listview = (XListView) view.findViewById(R.id.listView_contentfragment);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	/**
	 * ��ת����ҳ����ҳ
	 * 
	 * @param position
	 *            ��Ŀ����
	 */
	public void goContentActivity(String idStr) {
		Intent intent = new Intent(getActivity(), ContentActivity.class);
		intent.putExtra("id", idStr);
		startActivity(intent);
	}
	
	public BaseListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
