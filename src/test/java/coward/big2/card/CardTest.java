package coward.big2.card;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTest {

	@Test
	public void testCompareTo() {
		Card d4 = Card.D4;
		Card s3 = Card.S3;
		Card h3 = Card.H3;
		Card c3 = Card.C3;
		Card d3 = Card.D3;

		// Test rank comparison
		assertTrue(d3.compareTo(d4) < 0);
		assertTrue(d4.compareTo(d3) > 0);

		// Test suit comparison
		assertTrue(0 == s3.compareTo(s3));
		assertTrue(s3.compareTo(h3) > 0);
		assertTrue(h3.compareTo(c3) > 0);
		assertTrue(c3.compareTo(d3) > 0);
		
		// Hybrid test
		assertTrue(d4.compareTo(s3) > 0);
	}

}
