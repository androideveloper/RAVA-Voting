package com.rau.evoting.utils;

import java.util.ArrayList;
import java.util.List;

public class StringHelper {

	public static String convertIntArrayToString(int[] array) {
		String delimiter = "|";

		StringBuilder stringBuilder = new StringBuilder();
		if (array.length > 0) {
			stringBuilder.append(array[0]);
			for (int i = 1; i < array.length; ++i) {
				stringBuilder.append(delimiter);
				stringBuilder.append(array[i]);
			}
		}
		return stringBuilder.toString();
	}

	public static String converInttListToString(List<Integer> list) {

		String delimiter = "|";

		StringBuilder stringBuilder = new StringBuilder();
		if (!list.isEmpty()) {
			stringBuilder.append(list.get(0));
			for (int i = 1; i < list.size(); ++i) {
				stringBuilder.append(delimiter);
				stringBuilder.append(list.get(i));
			}
		}
		return stringBuilder.toString();
	}

	public static List<Integer> converStringToInttList(String s) {

		List<Integer> list = new ArrayList<Integer>();
		String delimiter = "|";

		String[] res = s.split(delimiter);
		for (int i = 0; i < res.length; ++i) {
			Integer x;
			try {
				x = Integer.parseInt(res[i]);
			} catch (NumberFormatException ex) {
				continue;
			}
			list.add(x);
		}

		return list;
	}

}
