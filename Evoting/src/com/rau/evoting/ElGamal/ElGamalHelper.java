package com.rau.evoting.ElGamal;

import java.math.BigInteger;
import java.util.List;

import org.bouncycastle.crypto.engines.ElGamalEngine;
import org.bouncycastle.crypto.params.ElGamalParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

import com.rau.evoting.utils.RandomHelper;

public class ElGamalHelper {

	private ElGamalParameters params = null;
	private ElGamalPrivateKeyParameters prKeyParams;
	private ElGamalPublicKeyParameters pubKeyParams;
	private ElGamalEngine engine;
	private BigInteger r = null;
	
	public ElGamalHelper() {
		
		GlobalParameters.getInstance();
		params = GlobalParameters.getParams();
		
		prKeyParams = new ElGamalPrivateKeyParameters(
				RandomHelper.randomNonZeroBigInteger(params.getP().subtract(
						new BigInteger("1"))), params);
		pubKeyParams = new ElGamalPublicKeyParameters(params.getG().modPow(
				prKeyParams.getX(), params.getP()), params);
		engine = new ElGamalEngine();
	}
	
	public ElGamalHelper(String pubKey){
		GlobalParameters.getInstance();
		params = GlobalParameters.getParams();
		
		pubKeyParams = new ElGamalPublicKeyParameters(new BigInteger(pubKey), params);
		engine = new ElGamalEngine();
	}

	public String oldEncode(String text) {
		engine.init(true, pubKeyParams);
		byte[] in = text.getBytes();
		byte[] b = engine.processBlock(in, 0, in.length);
		String encoded = new String(b);
		return encoded;
	}

	public String oldDecode(String codedText) {
		byte[] in = codedText.getBytes(); 
		engine.init(false, prKeyParams);
		byte[] b = engine.processBlock(in, 0, in.length);
		String decoded = new String(b);
		return decoded;
	}
	
	public String newEncode(String text) {
		EncryptEngine eng = new EncryptEngine();
		eng.initForEncrypt(pubKeyParams);
		byte[] in = text.getBytes();
		byte[] b = eng.encode(in, 0, in.length);
		r = eng.getR();
		String encoded = new String(b);
		return encoded;
	}

	public String newDecode(String codedText) {
		DecryptEngine eng = new DecryptEngine();
		byte[] in = codedText.getBytes(); 
		eng.initForDecrypt(prKeyParams);
		byte[] b = eng.decode(in, 0, in.length);
		String decoded = new String(b);
		return decoded;
	}

	public String getElectionPublicKey(List<String> publicKeys) {
		String key = "";
		BigInteger res = new BigInteger("1");
		for (String k : publicKeys) {
			res = res.multiply(new BigInteger(k));
			res = res.mod(pubKeyParams.getParameters().getP());
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

	public String getP() {
		return params.getP().toString();
	}

	public String getG() {
		return params.getG().toString();
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
