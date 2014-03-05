package coward.big2.card;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTest {

	@Test
	public void testCompareTo() {
		Card d4 = new Card(Suit.DIAMOND, Rank.N4___);
		Card s3 = new Card(Suit.SPADE__, Rank.N3___);
		Card h3 = new Card(Suit.HEART__, Rank.N3___);
		Card c3 = new Card(Suit.CLUB___, Rank.N3___);
		Card d3 = new Card(Suit.DIAMOND, Rank.N3___);

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
