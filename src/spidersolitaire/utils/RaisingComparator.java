package spidersolitaire.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RaisingComparator<T> implements Comparator<T> {
	private T raisingValue;
	
	public void raiseToTheTop(T raisingValue, List<T> list) {
		this.raisingValue = raisingValue;
		Collections.sort(list, this);
	}

	public int compare(final T a, final T b) {
		if (a.equals(raisingValue)) 
			return 1;
		else if (b.equals(raisingValue)) 
			return -1;
		return 0;
	}
}