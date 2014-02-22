package com.rau.evoting.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class BarcodeHelper {

	public static StreamedContent getBarcodeFromString(String s) {
		StreamedContent barcode1 = null;
		try {
			File barcodeFile = new File("file2:" + s);
			//barcodeFile.
			Barcode bCode = BarcodeFactory.createCode128(s);
			//bCode.setLabel("aaa");
			BarcodeImageHandler.saveJPEG(bCode,
					barcodeFile);
			
			barcode1 = new DefaultStreamedContent(new FileInputStream(
					barcodeFile), "image/jpeg");
			//barcodeFile.delete();
		} catch (OutputException e) {
			// TODO Auto-generated catch block
			System.out.println("output_EX");
			e.printStackTrace();
		} catch (BarcodeException e) {
			// TODO Auto-generated catch block
			System.out.println("barcode_EX");
			e.printStackTrace();
		}catch (Exception e){
			// TODO Auto-generated catch block
			System.out.println("ex_EX" + e.getMessage());
			e.printStackTrace();
		}
		

		return barcode1;
	}

	public static StreamedContent getBarcodeFromIntList(List<Integer> list){
		String s = StringHelper.converInttListToString(list);
		//System.out.println(s);
		//List<Integer> x =  StringHelper.converStringToInttList(s);
		return getBarcodeFromString(s);
	}
	
	public static StreamedContent getEncodedBarcodeFromIntList(List<Integer> list, String pubKey){
		System.out.print("count:" + list.size());
		String s = StringHelper.converInttListToString(list);
		System.out.println("string:" + s);
		ElGamalHelper e = new ElGamalHelper(pubKey);
		String encoded = e.encode(s);
		System.out.println("encoded:" + encoded);
		return getBarcodeFromString(encoded);
	}
}
