package coward.immutable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class I23Tree<T> implements Iterable<T> {

	private int maxSize = 4;
	private int halfSize = maxSize / 2;

	private List<Slot> root;
	private Comparator<T> comparator;

	/**
	 * List<Slot> would be null in leaves. Pivot stores the leaf value.
	 * 
	 * Pivot would be null at the maximum side of a tree. It represents the
	 * guarding key.
	 */
	private class Slot {
		private List<Slot> slots;
		private T pivot;

		private Slot(List<Slot> slots, T pivot) {
			this.slots = slots;
			this.pivot = pivot;
		}
	}

	public I23Tree(Comparator<T> comparator) {
		this.root = Arrays.asList(new Slot(null, null));
		this.comparator = comparator;
	}

	private I23Tree(Comparator<T> comparator, List<Slot> root) {
		this.root = root;
		this.comparator = comparator;
	}

	public Iterator<T> iterator() {
		return iterator(null, null);
	}

	private Iterator<T> iterator(final T start, final T end) {
		return new Iterator<T>() {
			private Deque<List<Slot>> stack = new ArrayDeque<>();
			private T source;

			{
				List<Slot> node = root;

				while (true) {
					int size = node.size();
					Slot slot = null;
					int i = 0;

					while (i < size && start != null && compare((slot = node.get(i)).pivot, start) < 0)
						i++;

					if (slot != null) {
						stack.push(right(node, i + 1));
						node = slot.slots;
					} else {
						stack.push(right(node, i));
						break;
					}
				}

				next();
			}

			public T source() {
				T t = null;
				while (!stack.isEmpty() && (t = push(stack.pop())) == null)
					;
				return compare(t, end) < 0 ? t : null;
			}

			private T push(List<Slot> node) {
				while (!node.isEmpty()) {
					Slot slot0 = node.get(0);
					stack.push(right(node, 1));
					List<Slot> node1 = slot0.slots;

					if (node1 != null)
						node = node1;
					else
						return slot0.pivot;
				}

				return null;
			}

			public boolean hasNext() {
				return source != null;
			}

			public T next() {
				T result = source;
				source = source();
				return result;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public T find(T t) {
		List<Slot> node = root;
		Slot slot = null;
		int c = 1;

		while (node != null) {
			int size = node.size();
			int i = 0;

			while (i < size && (c = compare((slot = node.get(i)).pivot, t)) < 0)
				i++;

			node = slot.slots;
		}

		return c == 0 ? slot.pivot : null;
	}

	public I23Tree<T> add(T t) {
		return add(t, false);
	}

	/**
	 * Replaces a value with another. Mainly for dictionary cases to replace
	 * stored value for the same key.
	 * 
	 * Asserts comparator.compare(<original-value>, t) == 0.
	 */
	public I23Tree<T> replace(T t) {
		return add(t, true);
	}

	public I23Tree<T> remove(T t) {
		return new I23Tree<T>(comparator, createRootNode(remove(root, t)));
	}

	private I23Tree<T> add(T t, boolean isReplace) {
		return new I23Tree<T>(comparator, createRootNode(add(root, t, isReplace)));
	}

	private List<Slot> add(List<Slot> node0, T t, boolean isReplace) {
		Slot slot = null;
		int i = 0, c = 1;

		// Finds appropriate slot
		while ((c = compare((slot = node0.get(i)).pivot, t)) < 0)
			i++;

		// Adds the node into it
		List<Slot> replaceSlots;

		if (slot.slots != null)
			replaceSlots = add(slot.slots, t, isReplace);
		else if (c != 0)
			replaceSlots = Arrays.asList(new Slot(null, t), slot);
		else if (isReplace)
			replaceSlots = Arrays.asList(new Slot(null, t));
		else
			throw new RuntimeException("Duplicate node " + t);

		List<Slot> slots1 = add(left(node0, i), replaceSlots, right(node0, i + 1));
		List<Slot> node1;

		// Checks if need to split
		if (slots1.size() < maxSize)
			node1 = Arrays.asList(slot(slots1));
		else { // Splits into two if reached maximum number of nodes
			List<Slot> leftSlots = left(slots1, halfSize);
			List<Slot> rightSlots = right(slots1, halfSize);
			node1 = Arrays.asList(slot(leftSlots), slot(rightSlots));
		}

		return node1;
	}

	private List<Slot> remove(List<Slot> node0, T t) {
		int size = node0.size();
		Slot slot = null;
		int i = 0, c = 1;

		// Finds appropriate slot
		while ((c = compare((slot = node0.get(i)).pivot, t)) < 0)
			i++;

		// Removes the node from it
		int s0 = i, s1 = i + 1;
		List<Slot> replaceSlots;

		if (c >= 0)
			if (slot.slots != null) {
				List<Slot> slots1 = remove(slot.slots, t);

				// Merges with a neighbor if reached minimum number of nodes
				if (slots1.size() < halfSize)
					if (s0 > 0)
						replaceSlots = merge(node0.get(--s0).slots, slots1);
					else if (s1 < size)
						replaceSlots = merge(slots1, node0.get(s1++).slots);
					else
						replaceSlots = Arrays.asList(slot(slots1));
				else
					replaceSlots = Arrays.asList(slot(slots1));
			} else if (c == 0)
				replaceSlots = Collections.emptyList();
			else
				throw new RuntimeException("List<Slot> not found " + t);
		else
			throw new RuntimeException("List<Slot> not found " + t);

		return add(left(node0, s0), replaceSlots, right(node0, s1));
	}

	private List<Slot> merge(List<Slot> node0, List<Slot> node1) {
		List<Slot> merged;

		if (node0.size() + node1.size() >= maxSize) {
			List<Slot> leftSlots, rightSlots;

			if (node0.size() > halfSize) {
				leftSlots = left(node0, -1);
				rightSlots = add(Arrays.asList(last(node0)), node1);
			} else {
				leftSlots = add(node0, Arrays.asList(first(node1)));
				rightSlots = right(node1, 1);
			}

			merged = Arrays.asList(slot(leftSlots), slot(rightSlots));
		} else
			merged = Arrays.asList(slot(add(node0, node1)));

		return merged;
	}

	private List<Slot> createRootNode(List<Slot> node) {
		List<Slot> node1;
		return node.size() == 1 && (node1 = node.get(0).slots) != null ? node1 : node;
	}

	private Slot slot(List<Slot> slots) {
		return new Slot(slots, last(slots).pivot);
	}

	private int compare(T t0, T t1) {
		boolean b0 = t0 != null;
		boolean b1 = t1 != null;

		if (b0 && b1)
			return comparator.compare(t0, t1);
		else
			return b0 ? -1 : b1 ? 1 : 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		dump(sb, root, "");
		return sb.toString();
	}

	@SafeVarargs
	public final List<Slot> add(List<Slot>... lists) {
		List<Slot> resultList = new ArrayList<>();
		for (List<Slot> list : lists)
			resultList.addAll(list);
		return resultList;
	}

	private List<Slot> left(List<Slot> list, int pos) {
		return list.subList(0, pos);
	}

	private List<Slot> right(List<Slot> list, int pos) {
		return list.subList(pos, list.size());
	}

	public Slot first(Collection<Slot> c) {
		return !c.isEmpty() ? c.iterator().next() : null;
	}

	public Slot last(List<Slot> c) {
		return !c.isEmpty() ? c.get(c.size() - 1) : null;
	}

	private void dump(StringBuilder sb, List<Slot> node, String indent) {
		if (node != null)
			for (Slot slot : node) {
				dump(sb, slot.slots, indent + "  ");
				sb.append(indent + (slot.pivot != null ? slot.pivot : "<infinity>") + "\n");
			}
	}

}
