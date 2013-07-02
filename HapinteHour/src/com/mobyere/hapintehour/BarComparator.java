package com.mobyere.hapintehour;

import java.util.Comparator;

// Tri les bars par distance (tri par défaut)
public class BarComparator implements Comparator<Bar> {

	@Override
	public int compare(Bar bar1, Bar bar2) {
//		int estHHCompare = Boolean.valueOf(bar2.isBarHH()).compareTo(
//				Boolean.valueOf(bar1.isBarHH()));
//		int distanceCompare = Float.valueOf(bar1.getBarDistance()).compareTo(
//				Float.valueOf(bar2.getBarDistance()));
		
//		int compared = estHHCompare;
//		if (compared == 0) {
//			compared = distanceCompare;
//		}
//		return compared;
		return Float.valueOf(bar1.getBarDistance()).compareTo(
				Float.valueOf(bar2.getBarDistance()));
	}
	
}
