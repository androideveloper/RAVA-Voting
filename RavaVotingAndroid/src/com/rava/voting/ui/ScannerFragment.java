package com.rava.voting.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.rava.voting.R;

public class ScannerFragment extends Fragment {

	public static final String TAG = "ScannerFragment";

	private Button mButtonScan;
	private TextView mTextViewFormat;
	private TextView mTextViewContent;

	public static ScannerFragment newInstance() {
		ScannerFragment fragment = new ScannerFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public ScannerFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_scanner, container,
				false);
		mButtonScan = (Button) root.findViewById(R.id.button_scan);
		mTextViewFormat = (TextView) root.findViewById(R.id.scan_format);
		mTextViewContent = (TextView) root.findViewById(R.id.scan_content);

		mButtonScan.setOnClickListener(new ScanClick());

		return root;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		if (scanningResult != null) {
			String format = scanningResult.getFormatName();
			String content = scanningResult.getContents();
			mTextViewFormat.setText("FORMAT: " + format);
			mTextViewContent.setText("CONTENT: " + content);
		} else {
			Toast.makeText(getActivity(), "No result", Toast.LENGTH_SHORT)
					.show();
			mTextViewFormat.setText("No result");
			mTextViewContent.setText("No result");
		}
	}

	private class ScanClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
		/*	IntentIntegrator integrator = new IntentIntegrator(
					ScannerFragment.this);
			integrator.addExtra("SCAN_WIDTH", 500);
			integrator.addExtra("SCAN_HEIGHT", 500);
			integrator
					.addExtra("PROMPT_MESSAGE",
							"Place a receipt barcode inside the viewfinder square to scan it");
			integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES); */
			
			FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
			ReceiptInfoFragment frag = ReceiptInfoFragment.newInstance("111111", "121312312412", "1.2.3");
			ft.replace(R.id.container, frag, ReceiptInfoFragment.TAG);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();
		}

	}

}
