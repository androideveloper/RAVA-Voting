package com.rau.evoting.ElGamal;

import java.math.BigInteger;

public class ChaumPedersen {

	private BigInteger a;
	private BigInteger b;
	private String message;

	public ChaumPedersen(BigInteger a, BigInteger b, String message) {
		this.a = a;
		this.b = b;
		this.message = message;
	}

	public BigInteger getA() {
		return a;
	}

	public void setA(BigInteger a) {
		this.a = a;
	}

	public BigInteger getB() {
		return b;
	}

	public void setB(BigInteger b) {
		this.b = b;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
