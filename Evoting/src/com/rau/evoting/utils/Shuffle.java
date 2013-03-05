package com.rau.evoting.utils;

import java.util.ArrayList;
import java.util.Random;

import com.rau.evoting.models.Candidate;


public class Shuffle {
	
	public static void shuffle(ArrayList<Candidate> l){
		int size = l.size() ;
		Random r = new Random();
		int x, y;
		Candidate one, two;
		for (int i = 0; i < 100; ++i) {
			x = r.nextInt(size);
			y = r.nextInt(size);
			one = l.get(x);
			two = l.get(y);
			l.set(x, two);
			l.set(y, one);
		}
	}
}
