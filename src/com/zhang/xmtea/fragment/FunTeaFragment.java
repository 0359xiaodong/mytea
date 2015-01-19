package com.zhang.xmtea.fragment;

import com.zhang.xmtea.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����������ҳ�����Ǳ���Ϊ��ٿ���ҳ
 * 
 * @author zhangyg
 * 
 */
@SuppressLint("ValidFragment")
public class FunTeaFragment extends Fragment {
	private final String TAG = "FunTeaFragment";
	/** �����ı��� */
	private EditText editText_funtea_searchEdit;
	/** ������ť */
	private ImageView imageView_funtea_searchBtn;
	/** ������������ */
	private TextView textView_funtea_serachtea;
	/** �ҵ��ղ� */
	private TextView textView_funtea_mycollect;
	/** �鿴���ʼ�¼ */
	private TextView textView_funtea_selectlog;
	/** ��Ȩ��Ϣ */
	private TextView textView_funtea_copyright;
	/** ������� */
	private TextView textView_funtea_feedback;
	/** �Զ��������ĵ��������� */
	private FunTeaOnClickListener funTeaOnClickListener;
	/** �ӿڻص����ͣ���Activity������ */
	private OnMyButtonClickListener mListener;
	/** �༭�򶯻����� */
	Animation shake;

	/** ��ֹ���� */
	public FunTeaFragment() {

	}

	/**
	 * ������Activity�������ģ�������fragment����
	 * 
	 * @param context
	 */
	public FunTeaFragment(Context context) {
		mListener = ((OnMyButtonClickListener) context);
		shake = AnimationUtils.loadAnimation(context, R.anim.shake);// ���ض�����Դ�ļ�
	}

	/**
	 * ������activity����ʱ�������ܵĽӿ�
	 * 
	 * @author zhangyg
	 * 
	 */
	public interface OnMyButtonClickListener {
		/**
		 * ��������ҳ�浥������
		 * 
		 * @param titleTag
		 *            title���
		 * @param text
		 *            ���������ҳ�棬��ô�Ѵ�ֵ����ȥ������дnull����
		 */
		public void onMyButtonClick(int titleTag, String text);// �ӿ��ж���һ������
	}

	/**
	 * ��Ҫ�������ݵĳ�ʼ��
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		funTeaOnClickListener = new FunTeaOnClickListener();
	}

	/**
	 * ��Ҫ���ڲ��ֵĳ�ʼ��
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Log.i(TAG, "onCreateView()==");
		View view = inflater.inflate(R.layout.fragment_fun_tea, null);
		editText_funtea_searchEdit = (EditText) view
				.findViewById(R.id.editText_funtea_searchEdit);
		imageView_funtea_searchBtn = (ImageView) view
				.findViewById(R.id.imageView_funtea_searchBtn);
		textView_funtea_serachtea = (TextView) view
				.findViewById(R.id.textView_funtea_serachtea);
		textView_funtea_mycollect = (TextView) view
				.findViewById(R.id.textView_funtea_mycollect);
		textView_funtea_selectlog = (TextView) view
				.findViewById(R.id.textView_funtea_selectlog);
		textView_funtea_copyright = (TextView) view
				.findViewById(R.id.textView_funtea_copyright);
		textView_funtea_feedback = (TextView) view
				.findViewById(R.id.textView_funtea_feedback);
		imageView_funtea_searchBtn.setOnClickListener(funTeaOnClickListener);
		textView_funtea_serachtea.setOnClickListener(funTeaOnClickListener);
		textView_funtea_mycollect.setOnClickListener(funTeaOnClickListener);
		textView_funtea_selectlog.setOnClickListener(funTeaOnClickListener);
		textView_funtea_copyright.setOnClickListener(funTeaOnClickListener);
		textView_funtea_feedback.setOnClickListener(funTeaOnClickListener);
		return view;
	}

	class FunTeaOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int vId = v.getId();
			if (vId == imageView_funtea_searchBtn.getId()) {
				String searchStr = editText_funtea_searchEdit.getText()
						.toString();
				if (!"".equals(searchStr)) {
					mListener.onMyButtonClick(5, searchStr);
				} else {
					editText_funtea_searchEdit.startAnimation(shake); // ��������Ŷ���Ч��
				}
			} else if (vId == textView_funtea_serachtea.getId()) {
				mListener.onMyButtonClick(5, "��");
			} else if (vId == textView_funtea_mycollect.getId()) {
				mListener.onMyButtonClick(1, null);
			} else if (vId == textView_funtea_selectlog.getId()) {
				mListener.onMyButtonClick(2, null);
			} else if (vId == textView_funtea_copyright.getId()) {
				mListener.onMyButtonClick(3, null);
			} else if (vId == textView_funtea_feedback.getId()) {
				mListener.onMyButtonClick(4, null);
			}
		}

	}
}
