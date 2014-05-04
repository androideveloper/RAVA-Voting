package com.rau.evoting.ElGamal;

import java.io.IOException;
import java.math.BigInteger;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BigIntegerTypeAdapter extends TypeAdapter<BigInteger> {
	@Override
	public BigInteger read(JsonReader reader) throws IOException {
		return new BigInteger(reader.nextString());
	}

	@Override
	public void write(JsonWriter writer, BigInteger value) throws IOException {
		writer.value(value);
	}
}
