package com.rau.evoting.ElGamal;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;

import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

import com.rau.evoting.utils.RandomHelper;

public class ElGamalHelper {

	private static ElGamalParameters params = GlobalParameters.getParams();;
	private ElGamalPrivateKeyParameters prKeyParams;
	private ElGamalPublicKeyParameters pubKeyParams;
	private BigInteger r = null;
	private Charset charset = Charset.forName("ISO-8859-1");

	public ElGamalHelper() {

		prKeyParams = new ElGamalPrivateKeyParameters(
				RandomHelper.randomNonZeroBigInteger(params.getP().subtract(
						new BigInteger("1"))), params);
		pubKeyParams = new ElGamalPublicKeyParameters(params.getG().modPow(
				prKeyParams.getX(), params.getP()), params);
	}

	public ElGamalHelper(String pubKey) {
		
		pubKeyParams = new ElGamalPublicKeyParameters(new BigInteger(pubKey),
				params);
	}

	public ElGamalHelper(String pubKey, String prKey) {
		
		prKeyParams = new ElGamalPrivateKeyParameters(new BigInteger(prKey),
				params);
		pubKeyParams = new ElGamalPublicKeyParameters(new BigInteger(pubKey),
				params);
	}

	public String encode(String text) {
		EncryptEngine eng = new EncryptEngine();
		eng.initForEncrypt(pubKeyParams);
		byte[] in = text.getBytes(charset);
		byte[] b = eng.encode(in, 0, in.length);
		r = eng.getR();
		String encoded = new String(b, charset);
		return encoded;
	}

	public String decode(String codedText) {
		DecryptEngine eng = new DecryptEngine();
		byte[] in = codedText.getBytes(charset);
		eng.initForDecrypt(prKeyParams);
		byte[] b = eng.decode(in, 0, in.length);
		String decoded = new String(b, charset);
		return decoded;
	}

	public static String getElectionPublicKey(List<String> publicKeys) {
		String key = "";
		BigInteger res = new BigInteger("1");
		for (String k : publicKeys) {
			res = res.multiply(new BigInteger(k));
			res = res.mod(params.getP());
		}
		key = res.toString();
		return key;
	}

	public int getPrivateKeyHash() {
		return prKeyParams.getX().hashCode();
	}

	public String getPrivateKey() {
		return prKeyParams.getX().toString();
	}

	public int getPublicKeyHash() {
		return pubKeyParams.getY().hashCode();
	}

	public String getPublicKey() {
		return pubKeyParams.getY().toString();
	}

	public ElGamalPublicKeyParameters getPubKeyParams() {
		return pubKeyParams;
	}

	public ElGamalPrivateKeyParameters getPrKeyParams() {
		return prKeyParams;
	}

	public BigInteger getR() {
		return r;
	}

}
