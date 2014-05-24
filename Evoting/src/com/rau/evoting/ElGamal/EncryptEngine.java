package com.rau.evoting.ElGamal;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

import com.rau.evoting.utils.Pair;

public class EncryptEngine {

	private ElGamalPublicKeyParameters pubKey = null;
	private SecureRandom random;
	private int bitSize;

	private static final BigInteger ZERO = BigInteger.ZERO;
	//private static final BigInteger ONE = BigInteger.ONE;
	private static final BigInteger TWO = BigInteger.valueOf(2);

	private BigInteger r = null;

	public void initForEncrypt(ElGamalPublicKeyParameters key) {
		pubKey = key;
		bitSize = key.getParameters().getP().bitLength();
		random = new SecureRandom();
	}

	public int getInputBlockSize() {
		return (bitSize - 1) / 8;

	}

	public int getOutputBlockSize() {
		return 2 * ((bitSize + 7) / 8);
	}

	public byte[] encode(byte[] in, int inOff, int inLen) {

		if (pubKey == null) {
			throw new IllegalStateException("ElGamal engine notinitialised");
		}

		int maxLength = (bitSize - 1 + 7) / 8;

		if (inLen > maxLength) {
			throw new DataLengthException(
					"input too large for ElGamal cipher.\n");
		}

		BigInteger p = pubKey.getParameters().getP();

		byte[] block;
		if (inOff != 0 || inLen != in.length) {
			block = new byte[inLen];

			System.arraycopy(in, inOff, block, 0, inLen);
		} else {
			block = in;
		}

		BigInteger input = new BigInteger(1, block);

		if (input.bitLength() >= p.bitLength()) {
			throw new DataLengthException(
					"input too large for ElGamal cipher.\n");
		}


		int pBitLength = p.bitLength();
		r = new BigInteger(pBitLength, random);

		while (r.equals(ZERO) || (r.compareTo(p.subtract(TWO)) > 0)) {
			r = new BigInteger(pBitLength, random);
		}

		BigInteger g = pubKey.getParameters().getG();
		BigInteger gamma = g.modPow(r, p);
		BigInteger phi = input.multiply(pubKey.getY().modPow(r, p)).mod(p);

		//System.out.println("enc_a:" + phi);
		//System.out.println("enc_b:" + gamma);
		
		byte[] out1 = gamma.toByteArray();
		byte[] out2 = phi.toByteArray();
		byte[] output = new byte[this.getOutputBlockSize()];

		if (out1.length > output.length / 2) {
			System.arraycopy(out1, 1, output, output.length / 2
					- (out1.length - 1), out1.length - 1);
		} else {
			System.arraycopy(out1, 0, output, output.length / 2 - out1.length,
					out1.length);
		}

		if (out2.length > output.length / 2) {
			System.arraycopy(out2, 1, output,
					output.length - (out2.length - 1), out2.length - 1);
		} else {
			System.arraycopy(out2, 0, output, output.length - out2.length,
					out2.length);
		}

		return output;
	}
	
	public byte[] reEncode(byte[] in, int inOff, int inLen) {

		if (pubKey == null) {
			throw new IllegalStateException("ElGamal engine notinitialised");
		}

		int maxLength = (bitSize - 1 + 7) / 8;

		if (inLen/2 > maxLength) {
		//	throw new DataLengthException(
		//			"input1 too large for ElGamal cipher.\n");
		}

		BigInteger p = pubKey.getParameters().getP();

		byte[] block;
		if (inOff != 0 || inLen != in.length) {
			block = new byte[inLen];

			System.arraycopy(in, inOff, block, 0, inLen);
		} else {
			block = in;
		}

		BigInteger input = new BigInteger(1, block);
		//System.out.println("input:" + input);
		Pair<BigInteger, BigInteger> enc = CryptoUtil.getBigIntEncodedA_B(input.toString());
		BigInteger a = enc.getFirst();
		BigInteger b = enc.getSecond();

		//System.out.println("reenc_a:" + a);
		//System.out.println("reenc_b:" + b);
		
		if (a.bitLength() >= p.bitLength() || b.bitLength() >= p.bitLength()) {
			//throw new DataLengthException(
				//	"input2 too large for ElGamal cipher.\n");
		}


		int pBitLength = p.bitLength();
		r = new BigInteger(pBitLength, random);

		while (r.equals(ZERO) || (r.compareTo(p.subtract(TWO)) > 0)) {
			r = new BigInteger(pBitLength, random);
		}

		BigInteger g = pubKey.getParameters().getG();
		BigInteger gamma = a.multiply(g.modPow(r, p)).mod(p);
		BigInteger phi = b.multiply(pubKey.getY().modPow(r, p)).mod(p);

		byte[] out1 = gamma.toByteArray();
		byte[] out2 = phi.toByteArray();
		byte[] output = new byte[this.getOutputBlockSize()];

		if (out1.length > output.length / 2) {
			System.arraycopy(out1, 1, output, output.length / 2
					- (out1.length - 1), out1.length - 1);
		} else {
			System.arraycopy(out1, 0, output, output.length / 2 - out1.length,
					out1.length);
		}

		if (out2.length > output.length / 2) {
			System.arraycopy(out2, 1, output,
					output.length - (out2.length - 1), out2.length - 1);
		} else {
			System.arraycopy(out2, 0, output, output.length - out2.length,
					out2.length);
		}

		return output;
	}

	public BigInteger getR() {
		return r;
	}
}
