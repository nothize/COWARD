package coward.immutable;

import java.util.Comparator;

public class ImmutableSet<T extends Comparable<? super T>> extends I23Tree<T> {

	public ImmutableSet() {
		super(new Comparator<T>() {
			public int compare(T t0, T t1) {
				if (t0 == null ^ t1 == null)
					return t0 != null ? 1 : -1;
				else
					return t0 != null ? t0.compareTo(t1) : 0;
			}
		});
	}

}
