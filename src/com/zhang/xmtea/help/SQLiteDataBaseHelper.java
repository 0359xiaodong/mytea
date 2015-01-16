package com.zhang.xmtea.help;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
//OPEN_READONLY �����������ֻ����ʽ�����ݿ⣨����ֵΪ��1��
//OPEN_READWRITE�������Զ�д��ʽ�����ݿ⣨����ֵΪ��0��
//CREATE_IF_NECESSARY�������ݿⲻ����ʱ�������ݿ�
//NO_LOCALIZED_COLLATORS�������ݿ�ʱ�������ݱ��ػ����Զ����ݿ�������򣨳���ֵΪ��16��
//mDatabaseHelper.getWritableDatabase();���Ƽ����ã�����������˻��ǻ����д�������쳣
//�õ���Ȩ��
//��sdcardд��Ȩ��<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
//��sdcard�д�����ɾ���ļ���Ȩ��<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
//ע�����ݿ�Ŀ�дȨ�ޣ�����
//linux�µ�Ȩ��
//-rwx------: �ļ������߶��ļ����ж�ȡ��д���ִ�е�Ȩ�ޡ�
//-rwxr-��r--: �ļ������߾��ж���д��ִ�е�Ȩ�ޣ������û�����ж�ȡ��Ȩ�ޡ�
//-rw-rw-r-x: �ļ���������ͬ���û����ļ����ж�д��Ȩ�ޣ��������û������ж�ȡ��ִ�е�Ȩ�ޡ�
//drwx--x--x: Ŀ¼�����߾��ж�д�����Ŀ¼��Ȩ��,�����û����ܽ����Ŀ¼��ȴ�޷���ȡ�κ����ݡ�
//Drwx------: ����Ŀ¼�����߾���������Ȩ��֮�⣬�����û��Ը�Ŀ¼��ȫû���κ�Ȩ�ޡ�
//r(Read����ȡ)�����ļ����ԣ����ж�ȡ�ļ����ݵ�Ȩ�ޣ���Ŀ¼��˵���������Ŀ ¼��Ȩ�ޡ�
//w(Write,д��)�����ļ����ԣ������������޸��ļ����ݵ�Ȩ�ޣ���Ŀ¼��˵������ɾ�����ƶ�Ŀ¼���ļ���Ȩ�ޡ�
//x(eXecute��ִ��)�����ļ����ԣ�����ִ���ļ���Ȩ�ޣ���Ŀ¼����˵���û����н���Ŀ¼��Ȩ�ޡ�
//����ر�ִ��Ȩ�ޣ����ʾ�ַ����ɴ�д��
//-rwSr-Sr-T 1 root root 4096 6�� 23 08��17 conf
//ע������Ϳ����Ĳ�ͬ
//SQLiteDatabase���Դ���insert��update��delete��query�ĸ�������Ȼ����������������SQL��䡣��Ϊ����װ


/**
 * 
 * @author zhangyg
 *
 */
public class SQLiteDataBaseHelper {
	
	/** ��־��ӡ */
	private static final String TAG = "SQLiteDataBaseHelper";
	/** ���ڹ���Ͳ���SQLite���ݿ� */
	private SQLiteDatabase database = null;
	/** ��SQLiteOpenHelper�̳й���������ʵ�����ݿ�Ľ�������� */
	private MySQLiteOpen mySQLiteOpen = null;
	// ================================
	/** SD���ĸ�Ŀ¼ */
	private final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	/** ��Ĭ�����ݿ�·�� */
	private final String PATH = SDCARD_ROOT + File.separator + "zhang"
			+ File.separator + "tea.db";
	// ==============================
	/** Ҫ���������ݿ����� */
	private static final String DB_NAME = "apkbus.db";
	/** ���ݿ�汾 */
	private static final int VERSION = 1;
	/** �������� */
	private static final String SQL_CREATE_TABLE = "CREATE TABLE tb_teacontents(_id INTEGER PRIMARY KEY, title , source , create_time , type)";


	
	/**
	 * �̳�SQLiteOpenHelper�࣬�ڹ��췽���зֱ���Ҫ����Context,���ݿ�����,CursorFactory(һ�㴫��null
	 * 
	 * ΪĬ�����ݿ�),���ݿ�汾��(����Ϊ����)����SQLiteOpenHelper������ִ�е���onCreate����
	 * 
	 * �ڹ��캯��ʱ��û�������������ݿ�
	 * 
	 * ���ڵ���getWritableDatabase����getReadableDatabase����ʱ������ȥ�������ݿ�
	 * 
	 * ����һ��SQLiteDatabase����
	 * 
	 * ���ݴ洢����data/data/Ӧ�ð���/databases
	 * 
	 * @author zhangyg
	 * 
	 */
	private class MySQLiteOpen extends SQLiteOpenHelper {
		/**
		 * ���췽��
		 * 
		 * @param context
		 * @param name
		 * @param factory
		 * @param version
		 */
		public MySQLiteOpen(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			Log.i(TAG, "==MySQLiteOpen()");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			if (newVersion > oldVersion) {
				db.execSQL("DROP TABLE IF EXISTS tb_apkbus");
				onCreate(db);
			}
		}

	}

	/**
	 * Ĭ�ϵĹ��췽���Զ�ȥ����һ��Ĭ�ϵ����ݿ�·��(SD�������ݣ���Ҫ������ļ����޸�)
	 */
	public SQLiteDataBaseHelper() {
		database = SQLiteDatabase.openDatabase(PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	/**
	 * �������ݿ⣬�������ݿ����
	 * 
	 * @param context
	 */
	public SQLiteDataBaseHelper(Context context, String name) {
		mySQLiteOpen = new MySQLiteOpen(context, DB_NAME, null, VERSION);
		database = mySQLiteOpen.getReadableDatabase();
		// database = mySQLiteOpen.getWritableDatabase();
	}


	/**
	 * @���� ��ѯ���ݷ���Cursor
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Cursor selectCursor(String sql, String[] selectionArgs) {
		return database.rawQuery(sql, selectionArgs);
	}

	/**
	 * @���� ִ�д�ռλ����select��䣬����list����
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public List<Map<String, String>> SelectData(String sql,
			String[] selectionArgs) {
		Cursor cursor = selectCursor(sql, selectionArgs);
		return cursorToList(cursor);
	}

	/**
	 * @���� ��֪һ��cursor�õ�List����
	 * @param cursor
	 * @return
	 */
	private List<Map<String, String>> cursorToList(Cursor cursor) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] arrColumnName = cursor.getColumnNames();
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < arrColumnName.length; i++) {
				String cols_value = cursor.getString(i);
				map.put(arrColumnName[i], cols_value);
			}
			list.add(map);
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}


	/**
	 * @���� ִ�д�ռλ����update��insert��delete��䣬�������ݿ⣬����true��false
	 * @param sql
	 * @param bindArgs
	 *            �ʺ��еĲ���ֵ
	 * @return boolean
	 */
	public boolean updataData(String sql, Object[] bindArgs) {
		try {
			if (bindArgs == null) {
				database.execSQL(sql);
			} else {
				database.execSQL(sql, bindArgs);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @���� ִ�д�ռλ����select��䣬���ؽ�����ĸ���������Ѿ���ѯ���˲��Ƽ�����ʹ�ã�ռ�ڴ�
	 * @param sql
	 * @param selectionArgs
	 * @return int
	 */
	public int selectCount(String sql, String[] selectionArgs) {
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		int count = 0;
		if (cursor != null) {
			count = cursor.getCount();
			cursor.close();
		}
		return count;
	}

	/**
	 * @���� �ر����ݿ������
	 */
	public void destroy() {
		if (mySQLiteOpen != null) {
			mySQLiteOpen.close();
			mySQLiteOpen = null;
		}
		if (database != null) {
			database.close();
			database = null;
		}
	}
}
