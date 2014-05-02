package com.rava.voting.ui;

import java.math.BigInteger;
import java.nio.charset.Charset;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rava.voting.R;

public class ReceiptInfoFragment extends Fragment {

	public static final String TAG = "ReceiptInfoFragment";

	//public static final String KEY_A = "KEY_A";
	//public static final String KEY_B = "KEY_B";
	//public static final String KEY_MESSAGE = "KEY_MESSAGE";

	private TextView mTextViewA;
	private TextView mTextViewB;
	private TextView mTextViewMessage;
	private TextView mTextViewMessageBigInt;
	
	
	public static ReceiptInfoFragment newInstance(String a, String b, String message) {
		ReceiptInfoFragment fragment = new ReceiptInfoFragment();
		Bundle args = new Bundle();
		args.putString("a", a);
		args.putString("b", b);
		args.putString("message", message);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_receipt_info, container,
				false);
		mTextViewA = (TextView)root.findViewById(R.id.textview_a);
		mTextViewB = (TextView)root.findViewById(R.id.textview_b);
		mTextViewMessage = (TextView)root.findViewById(R.id.textview_message);
		mTextViewMessageBigInt = (TextView)root.findViewById(R.id.textview_message_bigint);
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Bundle args = getArguments();
		BigInteger a = new BigInteger(args.getString("a"));
		BigInteger b = new BigInteger(args.getString("b"));
		String message = args.getString("message");
		Charset charset = Charset.forName("ISO-8859-1");
		byte[] bytes = message.getBytes(charset);
		BigInteger messageBigint = new BigInteger(bytes);
		
		mTextViewA.setText(a.toString());
		mTextViewB.setText(b.toString());
		mTextViewMessage.setText(message);
		mTextViewMessageBigInt.setText(messageBigint.toString());
		
	}

}
