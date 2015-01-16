package com.zhang.xmtea.app;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.graphics.Bitmap;

public class MyApplication extends Application {
	/** ������������ͼƬ<��ַ��bitmap> */
	private Map<String, Bitmap> cacheImageMap = new HashMap<String, Bitmap>();

	@Override
	public void onCreate() {
		super.onCreate();
		cacheImageMap = new HashMap<String, Bitmap>();
	}

	public Map<String, Bitmap> getCacheImageMap() {
		return cacheImageMap;
	}

}
