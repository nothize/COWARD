package coward.big2.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coward.big2.Hand;
import coward.big2.card.Card;
import coward.big2.card.Rank;
import coward.big2.card.Suit;
import coward.immutable.ImmutableSet;

public class MoveGeneratorTest {

	private MoveGenerator moveGenerator = new MoveGenerator();

	@Test
	public void test() {
		assertEquals(2, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				new Card(Suit.CLUB___, Rank.ACE__) //
				, new Card(Suit.DIAMOND, Rank.N2___) //
				))).size());

		assertEquals(3, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				new Card(Suit.CLUB___, Rank.ACE__) //
				, new Card(Suit.DIAMOND, Rank.ACE__) //
				))).size());

		assertEquals(15, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				new Card(Suit.CLUB___, Rank.ACE__) //
				, new Card(Suit.DIAMOND, Rank.ACE__) //
				, new Card(Suit.HEART__, Rank.ACE__) //
				, new Card(Suit.SPADE__, Rank.ACE__) //
				))).size());
	}
}
