package com.zhang.xmtea.help;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ������
 * @author zhangyg
 *
 */
public class SharepreferencesHelp {
	
	//1��SharedPreferences������ѡ��
	//��һ���ص㣺
	//��1���Լ�ֵ�Ե���ʽ���浽Ŀ¼Ϊ/data/data/����/shared_prefsĿ¼��*.xml�ļ��С�
	//��2��Ŀǰ֧�ֵ�����������string��int��long��float��boolean��
	//��3����֧���Զ����Object��
	//��4��ͨ�������洢app�ϵ�������Ϣ�����Ƿ��񶯡��Ƿ����Ч��С��Ϸ���֡�
	//��5��SharedPreferences��һ���ӿڣ��޷�ֱ�Ӵ���ʵ������ͨ��Context��getSharedPreferences(String name, int mode)��ʽ����ȡʵ����
	//��6��mode����������ֵ
	//Context.MODE_PRIVATE��ָ����SharedPreferences������ֻ�ܱ���Ӧ�ó������д��
	//Context.MODE_WORLD_READABLE��ָ��SharedPreferences�����ܱ�����Ӧ�ó���������ǲ�֧��д��
	//Context.MODE_WORLD_WRITEABLE��ָ����ShaedPreferences�����ܱ�����Ӧ�ó������д��
	//������������(�ɴ��������࣬��Ȩ��)
	//��1������
	//ͨ��Context�µ�getSharedPreferences()������ȡʵ����
	//prefs = Context.getSharedPreferences(������ַ�����,int mode);//��ȡSharedPreferencesʵ��
	//mode����������ֵ
	//Context.MODE_PRIVATE��ָ����SharedPreferences������ֻ�ܱ���Ӧ�ó������д��
	//Context.MODE_WORLD_READABLE��ָ��SharedPreferences�����ܱ�����Ӧ�ó���������ǲ�֧��д��
	//Context.MODE_WORLD_WRITEABLE��ָ����ShaedPreferences�����ܱ�����Ӧ�ó������д��
	//����edit()��ȡSharedPreferences.Editor���õ�Editor����
	//SharedPreferences.Editor editor = prefs.edit();//��ȡEditorʵ��
	//��2������
	//ͨ��SharedPreferences.Editor�ӿ��ṩ��put()������SharedPreferences���и��£�
	//editor.putInt(��age��,38);//���ݸ���
	//editor:putString();
	//editor.commit();
	//��3��ɾ���ֶ��ҵ��ļ�ɾ�ˣ��������°�װ���
	//��4���ģ�ͨ��������ͬ���ظ�putֵ�Ϳ��ԡ�
	//��5���飺prefs.getString(��age��);//���������ֵ��˼��

	private final static String TAG = "SharedPreferencesHelper";
	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	
	public SharepreferencesHelp(Context context) {
		// TODO Auto-generated constructor stub
		prefs = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		editor = prefs.edit();
	}
	
	/**
	 * ���ݼ��ַ������洢һ���ַ���ֵ
	 * 
	 * @param key
	 * @param value
	 * @return �����ύ�Ƿ�ɹ�
	 */
	public boolean putString(String key, String value) {
		editor.putString(key, value);
		return editor.commit();
	}

	/**
	 * ����keyֵ�õ��洢��������û���ҵ�value�ͷ���null
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return prefs.getString(key, null);
	}

	/**
	 * ���ݼ��ַ������洢һ��intֵ
	 * 
	 * @param key
	 * @param value
	 * @return �����ύ�Ƿ�ɹ�
	 */
	public boolean putInt(String key, int value) {
		editor.putInt(key, value);
		return editor.commit();
	}

	/**
	 * ����keyֵ�õ��洢��������û���ҵ�value�ͷ���-1
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		return prefs.getInt(key, -1);
	}

	/**
	 * �������
	 * 
	 * @return
	 */
	public boolean clear() {
		editor.clear();
		return editor.commit();
	}

	/**
	 * �رյ�ǰ����
	 * 
	 * @return
	 */
	public void close() {
		prefs = null;
	}
}
