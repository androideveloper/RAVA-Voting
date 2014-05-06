package com.rau.evoting.ElGamal;

import java.math.BigInteger;
import java.nio.charset.Charset;

import com.rau.evoting.utils.Pair;

public class CryptoUtil {

	private static Charset charset = Charset.forName("ISO-8859-1");
	
	public static Pair<BigInteger, BigInteger> getEncodedA_B(String text) {
		byte[] in = text.getBytes(charset);
		
		int inLen = in.length;
		
		byte[] in1 = new byte[inLen / 2];
		byte[] in2 = new byte[inLen / 2];

		System.arraycopy(in, 0, in1, 0, in1.length);
		System.arraycopy(in, in1.length, in2, 0, in2.length);

		BigInteger a = new BigInteger(1, in1);
		BigInteger b = new BigInteger(1, in2);
		
		Pair<BigInteger, BigInteger> res = new Pair<BigInteger, BigInteger>(a, b);
		return res;
	}
	
	public static Pair<BigInteger, BigInteger> getBigIntEncodedA_B(String text) {
		BigInteger bg = new BigInteger(text);
		byte[] in = bg.toByteArray();
		
		int inLen = in.length;
		
		byte[] in1 = new byte[inLen / 2];
		byte[] in2 = new byte[inLen / 2];

		System.arraycopy(in, 0, in1, 0, in1.length);
		System.arraycopy(in, in1.length, in2, 0, in2.length);

		BigInteger a = new BigInteger(1, in1);
		BigInteger b = new BigInteger(1, in2);
		
		Pair<BigInteger, BigInteger> res = new Pair<BigInteger, BigInteger>(a, b);
		return res;
	}

}
