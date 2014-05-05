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
		mTextViewContent = (TextView) root.findViewById(R.id.scan_content);

		mButtonScan.setOnClickListener(new ScanClick());

		return root;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, data);
		if (scanningResult != null && scanningResult.getContents() != null) {
			String content = scanningResult.getContents();
			mTextViewContent.setText("CONTENT: " + content);

			FragmentTransaction ft = getActivity().getFragmentManager()
					.beginTransaction();
			ReceiptInfoFragment frag = ReceiptInfoFragment.newInstance(content);
			ft.replace(R.id.container, frag, ReceiptInfoFragment.TAG);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.addToBackStack(null);
			ft.commit();
		} else {
			Toast.makeText(getActivity(), "No result", Toast.LENGTH_SHORT)
					.show();
			mTextViewContent.setText("No result");
		}
	}

	private class ScanClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			IntentIntegrator integrator = new IntentIntegrator(
					ScannerFragment.this);
			integrator.addExtra("SCAN_WIDTH", 500);
			integrator.addExtra("SCAN_HEIGHT", 500);
			integrator
					.addExtra("PROMPT_MESSAGE",
							"Place a receipt barcode inside the viewfinder square to scan it");
			integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
		}

	}

}
