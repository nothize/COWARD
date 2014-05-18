package coward.big2.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import coward.big2.Hand;
import coward.big2.card.Card;
import coward.immutable.ImmutableSet;

public class MoveGeneratorTest {
	private static final Log log = LogFactory.getLog(MoveGeneratorTest.class);

	private MoveGenerator moveGenerator = new MoveGenerator();
	
	@Test
	public void test() {

		// 2 single combos
		assertEquals(2, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.D2 //
				))).size());

		// 2 single combos
		// 1 double combos
		assertEquals(3, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.DA //
				))).size());

		// 4 single combos
		// 6 double combos
		// 4 triple combos
		// 1 quadruple combos
		assertEquals(15, moveGenerator.generate(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.DA //
				, Card.HA //
				, Card.SA //
				))).size());
	}

	@Test
	public void testPermute3() throws Exception {
		List<ImmutableSet<Card>> results = new ArrayList<>();
		moveGenerator.permute(ImmutableSet.asSet(Card.SA, Card.HA, Card.CA), 
				3, results);
		assertEquals(1, results.size());
		ImmutableSet<Card> cards = results.get(0);
		assertEquals(3, cards.size());
		log.debug(cards);
		Set<Card> cardSet = new HashSet<>();
		for (Card card : cards) {
			cardSet.add(card);
		}
		assertEquals(com.google.common.collect.ImmutableSet.of(Card.SA, Card.HA, Card.CA)
				, cardSet);
	}
	
	@Test
	public void testFullHouse() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.CA, Card.DA, Card.SA, Card.HA
				, Card.C2, Card.H2
		));
		List<ImmutableSet<Card>> rankedCards = moveGenerator.groupSameRanks(hand.getCards());
		List<ImmutableSet<Card>> results = new ArrayList<>();

		moveGenerator.generateFullHouse(rankedCards, results);
		log.debug("result: " + results);
		
		assertEquals(4, results.size());

		hand = new Hand(ImmutableSet.asSet(
				Card.CA, Card.DA, Card.SA
				, Card.C2, Card.H2, Card.S2
		));
		rankedCards = moveGenerator.groupSameRanks(hand.getCards());
		results = new ArrayList<>();

		moveGenerator.generateFullHouse(rankedCards, results);
		log.debug("result: " + results);
		
		assertEquals(6, results.size());
	}
}
