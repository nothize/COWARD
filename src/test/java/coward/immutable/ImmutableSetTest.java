package coward.immutable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ImmutableSetTest {

	@Test
	public void test() {
		ImmutableSet<Integer> immutableSet = new ImmutableSet<>();

		assertTrue(immutableSet.isEmpty());
		assertFalse(immutableSet.add(1).add(2).remove(1).isEmpty());
		assertTrue(immutableSet.add(1).add(2).remove(1).remove(2).isEmpty());
	}

}
