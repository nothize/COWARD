package coward.big2.strategy;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
		assertEquals(2, moveGenerator.generateMoves(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.D2 //
				))).size());

		// 2 single combos
		// 1 double combos
		assertEquals(3, moveGenerator.generateMoves(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.DA //
				))).size());

		// 4 single combos
		// 6 double combos
		// 4 triple combos
		// 1 quadruple combos
		assertEquals(15, moveGenerator.generateMoves(new Hand(ImmutableSet.asSet( //
				Card.CA //
				, Card.DA //
				, Card.HA //
				, Card.SA //
				))).size());
	}

	@Test
	public void testPermute3() throws Exception {
		List<ImmutableSet<Card>> results = new ArrayList<>();
		moveGenerator.combos(ImmutableSet.asSet(Card.SA, Card.HA, Card.CA), 
				3, results::add);
		assertEquals(1, results.size());
		ImmutableSet<Card> cards = results.get(0);
		assertEquals(3, cards.size());
		log.debug(cards);
		assertEquals(ImmutableSet.asSet(Card.SA, Card.HA, Card.CA)
				, cards);
	}
	
	@Test
	public void testFullHouse() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.CA, Card.DA, Card.SA, Card.HA
				, Card.C2, Card.H2
		));
		List<ImmutableSet<Card>> rankedCards = moveGenerator.groupSameRanks(hand.getCards());
		List<ImmutableSet<Card>> results = new ArrayList<>();

		moveGenerator.generateFullHouseCards(rankedCards, results::add);
		log.debug("result: " + results);
		
		assertEquals(4, results.size());

		hand = new Hand(ImmutableSet.asSet(
				Card.CA, Card.DA, Card.SA
				, Card.C2, Card.H2, Card.S2
		));
		rankedCards = moveGenerator.groupSameRanks(hand.getCards());
		results = new ArrayList<>();

		moveGenerator.generateFullHouseCards(rankedCards, results::add);
		log.debug("result: " + results);
		
		assertEquals(6, results.size());
	}
	
	@Test
	public void testFourOne() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.CA, Card.DA, Card.SA, Card.HA
				, Card.C2, Card.H2
		));
		ImmutableSet<Card> cards = hand.getCards();
		List<ImmutableSet<Card>> rankedCards = moveGenerator.groupSameRanks(cards);
		List<ImmutableSet<Card>> results = new ArrayList<>();

		moveGenerator.generateFourOneCards(cards, rankedCards, results::add);
		log.debug("result: " + results);
		
		assertEquals(2, results.size());

		hand = new Hand(ImmutableSet.asSet(
				Card.SA, Card.HA, Card.CA, Card.DA
				, Card.S2, Card.H2, Card.C2, Card.D2
		));
		cards = hand.getCards();
		rankedCards = moveGenerator.groupSameRanks(cards);
		results = new ArrayList<>();

		moveGenerator.generateFourOneCards(cards, rankedCards, results::add);
		log.debug("result: " + results);
		
		assertEquals(8, results.size());
	}
	
	@Test
	public void testStraight() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.C7, Card.H8,
				Card.C3, Card.D4, Card.H4, Card.S5, Card.H6
		));
		ImmutableSet<Card> cards = hand.getCards();
		List<ImmutableSet<Card>> rankedCards = moveGenerator.groupSameRanks(cards);
		List<ImmutableSet<Card>> results = new ArrayList<>();
		moveGenerator.generateStraightCards(cards, rankedCards, results);
		
		ImmutableSet<Card> set1 = ImmutableSet.asSet(
			Card.C3, Card.D4, Card.S5, Card.H6, Card.C7
		);
		ImmutableSet<Card> set2 = ImmutableSet.asSet(
				Card.C3, Card.H4, Card.S5, Card.H6, Card.C7
		);
		ImmutableSet<Card> set3 = ImmutableSet.asSet(
				Card.D4, Card.S5, Card.H6, Card.C7, Card.H8
		);
		ImmutableSet<Card> set4 = ImmutableSet.asSet(
				Card.H4, Card.S5, Card.H6, Card.C7, Card.H8
		);
		List<ImmutableSet<Card>> expected = new ArrayList<>();
		expected.add(set1);
		expected.add(set2);
		expected.add(set3);
		expected.add(set4);
		assertEquals(expected, results);
	}
	
	@Test
	public void testFlush() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.H3, Card.D4, Card.H4, Card.H5, Card.H6, Card.H7, Card.H8
		));
		ImmutableSet<Card> cards = hand.getCards();
		List<ImmutableSet<Card>> results = new ArrayList<>();
		moveGenerator.generateFlush(cards, results::add);
		log.debug("flush: " + results);
		
		List<ImmutableSet<Card>> expected = new ArrayList<>();
		Card[][] sets = new Card[][] {
			{Card.H4, Card.H5, Card.H6, Card.H7, Card.H8},
			{Card.H3, Card.H5, Card.H6, Card.H7, Card.H8},
			{Card.H3, Card.H4, Card.H6, Card.H7, Card.H8},
			{Card.H3, Card.H4, Card.H5, Card.H7, Card.H8},
			{Card.H3, Card.H4, Card.H5, Card.H6, Card.H8},
			{Card.H3, Card.H4, Card.H5, Card.H6, Card.H7}
		};
		for (Card[] cards2 : sets) {
			ImmutableSet<Card> set = new ImmutableSet<>();
			for (Card card : cards2) {
				set = set.add(card);
			}
			expected.add(set);
		}
		assertEquals(expected, results);
	}
	
	@Test
	public void testStraightFlush() throws Exception {
		Hand hand = new Hand(ImmutableSet.asSet(
				Card.H3, Card.D4, Card.H4, Card.H5, Card.H6, Card.H7, Card.H9
		));
		ImmutableSet<Card> cards = hand.getCards();
		
		List<ImmutableSet<Card>> rankedCards = moveGenerator.groupSameRanks(cards);
		List<ImmutableSet<Card>> straight = new ArrayList<>();
		moveGenerator.generateStraightCards(cards, rankedCards, straight);
		
		List<ImmutableSet<Card>> flush = new ArrayList<>();
		moveGenerator.generateFlush(cards, flush::add);
		
		List<ImmutableSet<Card>> straightFlush = moveGenerator.generateStraightFlushCards(straight, flush);
		
		List<ImmutableSet<Card>> expected = new ArrayList<>();
		Card[][] sets = new Card[][] {
			{Card.H3, Card.H4, Card.H5, Card.H6, Card.H7}
		};
		for (Card[] cards2 : sets) {
			ImmutableSet<Card> set = new ImmutableSet<>();
			for (Card card : cards2) {
				set = set.add(card);
			}
			expected.add(set);
		}
		assertEquals(expected, straightFlush);
	}
}
