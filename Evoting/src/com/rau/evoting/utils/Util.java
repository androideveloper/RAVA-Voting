package com.rau.evoting.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.rau.evoting.models.Answer;

public class Util {

	public static <E> void shuffle(ArrayList<E> l) {
		int size = l.size();
		Random r = new Random();
		int x, y;
		E one, two;
		for (int i = 0; i < 100; ++i) {
			x = r.nextInt(size);
			y = r.nextInt(size);
			one = l.get(x); 
			two = l.get(y);
			l.set(x, two);
			l.set(y, one);
		}
	}

	public static String generateRandomToken() {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789@oO01lL";
		Random r = new SecureRandom();
		String pw = "";
		int len = r.nextInt(10) + 30;
		for (int i = 0; i < len; i++) {
			int index = (int) (r.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}

		return pw;
	}
}

