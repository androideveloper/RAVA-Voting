package com.rau.evoting.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import net.glxn.qrgen.QRCode;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.rau.evoting.ElGamal.ElGamalHelper;

public class BarcodeHelper {

	public static StreamedContent getBarcodeFromString(String s) {
		StreamedContent barcode1 = null;
		try {
			File file = QRCode.from(s).file();
			barcode1 = new DefaultStreamedContent(new FileInputStream(file),
					"image/jpeg");
			file.delete();
		} catch (Exception e) {
			System.out.println("ex_EX" + e.getCause());
			e.printStackTrace();
		}

		return barcode1;
	}

	public static StreamedContent getBarcodeFromIntList(List<Integer> list) {
		String s = StringHelper.converInttListToString(list);
		return getBarcodeFromString(s);
	}

	public static StreamedContent getEncodedBarcodeFromIntList(
			List<Integer> list, String pubKey) {
		// System.out.print("count:" + list.size());
		String s = StringHelper.converInttListToString(list);
		// System.out.println("string:" + s);
		ElGamalHelper e = new ElGamalHelper(pubKey);
		String encoded = e.oldEncode(s);
		System.out.println("encoded:" + encoded);
		return getBarcodeFromString(encoded);
	}
}
