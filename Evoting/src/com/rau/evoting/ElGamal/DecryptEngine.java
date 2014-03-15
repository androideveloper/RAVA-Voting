package com.rau.evoting.ElGamal;

import java.math.BigInteger;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.util.BigIntegers;

public class DecryptEngine {

	private ElGamalPrivateKeyParameters prKey;
	private int bitSize;

	//private static final BigInteger ZERO = BigInteger.ZERO;
	private static final BigInteger ONE = BigInteger.ONE;
	//private static final BigInteger TWO = BigInteger.valueOf(2);

	public void initForDecrypt(ElGamalPrivateKeyParameters key) {
		prKey = key;
		bitSize = key.getParameters().getP().bitLength();
	}

	public int getInputBlockSize() {
		return 2 * ((bitSize + 7) / 8);
	}

	public int getOutputBlockSize() {
		return (bitSize - 1) / 8;
	}

	public byte[] decode(byte[] in, int inOff, int inLen) {

		if (prKey == null) {
			throw new IllegalStateException("ElGamal engine not initialised");
		}

		int maxLength = getInputBlockSize();

		if (inLen > maxLength) {
			throw new DataLengthException(
					"input too large for ElGamal cipher.\n");
		}

		BigInteger p = prKey.getParameters().getP();

		byte[] in1 = new byte[inLen / 2];
		byte[] in2 = new byte[inLen / 2];

		System.arraycopy(in, inOff, in1, 0, in1.length);
		System.arraycopy(in, inOff + in1.length, in2, 0, in2.length);

		BigInteger gamma = new BigInteger(1, in1);
		BigInteger phi = new BigInteger(1, in2);

		// a shortcut, which generally relies on p being prime amongst other
		// things.
		// if a problem with this shows up, check the p and g values!
		BigInteger m = gamma.modPow(p.subtract(ONE).subtract(prKey.getX()), p)
				.multiply(phi).mod(p);

		return BigIntegers.asUnsignedByteArray(m);
	}

}
