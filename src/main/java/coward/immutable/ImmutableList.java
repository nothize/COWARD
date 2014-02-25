package coward.immutable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Immutable linked list.
 * 
 * @author ywsing
 */
public class ImmutableList<T> implements Iterable<T> {

	private final static ImmutableList<?> end = new ImmutableList<Object>(null, null);

	private T head;
	private ImmutableList<T> tail;

	public ImmutableList(T head, ImmutableList<T> tail) {
		this.head = head;
		this.tail = tail;
	}

	public static <T> ImmutableList<T> empty() {
		@SuppressWarnings("unchecked")
		ImmutableList<T> end = (ImmutableList<T>) ImmutableList.end;
		return end;
	}

	@SafeVarargs
	public static <T> ImmutableList<T> asList(T... ts) {
		ImmutableList<T> list = ImmutableList.<T> empty();
		for (T t : ts)
			list = cons(t, list);
		return list;
	}

	public static <T> ImmutableList<T> cons(T t, ImmutableList<T> list) {
		return new ImmutableList<>(t, list);
	}

	public boolean isEmpty() {
		return this == end;
	}

	public int size() {
		int size = 0;
		ImmutableList<T> node = this;
		while (node != end) {
			size++;
			node = node.tail;
		}
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private ImmutableList<T> current = ImmutableList.this;

			public boolean hasNext() {
				return current != end;
			}

			public T next() {
				T t = current.head;
				current = current.tail;
				return t;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		ImmutableList<T> node = this;
		while (!node.isEmpty()) {
			sb.append(node.getHead() + ", ");
			node = node.getTail();
		}
		return sb.toString();
	}

	public Deque<T> reverse() {
		Deque<T> deque = new ArrayDeque<>();
		for (T t : this)
			deque.addFirst(t);
		return deque;
	}

	public T getHead() {
		return head;
	}

	public ImmutableList<T> getTail() {
		return tail;
	}

}
