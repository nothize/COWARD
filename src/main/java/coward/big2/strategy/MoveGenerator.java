package coward.big2.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import coward.big2.Hand;
import coward.big2.card.Card;
import coward.big2.card.Move;
import coward.big2.card.Move.MoveType;
import coward.big2.card.Rank;
import coward.big2.card.Suit;
import coward.immutable.ImmutableSet;

/**
 * Generate all possible moves based on a hand.
 * 
 */
public class MoveGenerator {
	private static final Log log = LogFactory.getLog(MoveGenerator.class);

	private final MoveType sameRankMoveTypes[] = { null, MoveType.SINGLE, MoveType.DOUBLE, MoveType.TRIPLE, MoveType.QUADRUPLE };

	private ImmutableSet<Card> empty = new ImmutableSet<Card>();

	public List<Move> generateMoves(Hand hand) {
		List<ImmutableSet<Card>> rankedCards = groupSameRanks(hand.getCards());
		List<Move> results = new ArrayList<>();

		generateSameRankMoves(rankedCards, results::add);
		generateFiveCardMoves(hand, rankedCards, results::add);

		return results;
	}
	
	public List<Move> generateMoves(Hand hand, int n) {
		List<ImmutableSet<Card>> rankedCards = groupSameRanks(hand.getCards());
		List<Move> results = new ArrayList<>();

		if (n != 5)
			generateSameRankMoves(rankedCards, n, results::add);
		else
			generateFiveCardMoves(hand, rankedCards, results::add);

		return results;
	}

	private void generateFiveCardMoves(Hand hand, List<ImmutableSet<Card>> rankedCards, Consumer<Move> consumer) {
		generateFullHouseCards(rankedCards, cards -> consumer.accept(new Move(MoveType.FULLHOUSE, cards)));
		generateFourOneCards(hand.getCards(), rankedCards, cards -> consumer.accept(new Move(MoveType.FOURONE, cards)));
		generateStraightOrFlushMoves(hand, rankedCards, consumer);
	}
	
	void generateStraightOrFlushMoves(Hand hand, List<ImmutableSet<Card>> rankedCards, Consumer<Move> consumer) {
		List<ImmutableSet<Card>> flush = new ArrayList<>();
		List<ImmutableSet<Card>> straight = new ArrayList<>();
		generateStraightCards(hand.getCards(), rankedCards, straight);
		generateFlush(hand.getCards(), flush::add);
		
		List<ImmutableSet<Card>> straightFlush = generateStraightFlushCards(straight, flush);

		straight.stream().forEach(cards -> consumer.accept(new Move(MoveType.STRAIGHT, cards)));
		flush.stream().forEach(cards -> consumer.accept(new Move(MoveType.FLUSH, cards)));
		straightFlush.stream().forEach(cards -> consumer.accept(new Move(MoveType.STRAIGHTFLUSH, cards)));
	}

	/**
	 *  Straight flush is an intersect set between straight and flush.
	 *  
	 * @param straight	The straight flush set will be removed.
	 * @param flush	The straight flush set will be removed.
	 * @return Straight flush set
	 */
	List<ImmutableSet<Card>> generateStraightFlushCards(List<ImmutableSet<Card>> straight, List<ImmutableSet<Card>> flush) {
		// Minus straightFlush from straight and flush
		List<ImmutableSet<Card>> straightFlush = new ArrayList<>();
		straightFlush.addAll(straight);
		straightFlush.retainAll(flush);
		
		straight.removeAll(straightFlush);
		flush.removeAll(straightFlush);
		
		log.debug("straight flush: " + straightFlush);
		return straightFlush;
	}
	
	/**
	 * Five cards in same suit. eg. 1H,3H,7H,8H,9H
	 * 
	 * @param cards
	 * @param consumer
	 */
	void generateFlush(ImmutableSet<Card> cards, Consumer<ImmutableSet<Card>> consumer) {
		List<ImmutableSet<Card>> suitedCards = groupSameSuits(cards);
		for (ImmutableSet<Card> sameSuits : suitedCards) {
			if ( sameSuits.size() >= 5 ) {
				log.debug("Flush combo: " + sameSuits);
				combos(sameSuits, 5, consumer);
			}
		}
	}

	/**
	 * Straight is 5 continuous ranks. eg. 1,2,3,4,5 J,Q,K,A,2
	 * 
	 * @param cards
	 * @param results
	 * @param results
	 */
	public void generateStraightCards(ImmutableSet<Card> allCards, List<ImmutableSet<Card>> rankedCards, List<ImmutableSet<Card>> results) {
		ImmutableSet<Card> first;
		ImmutableSet<Card> last;
		log.debug(rankedCards);
		for (int i = 0; i + 4 < rankedCards.size(); i++) {
			first = rankedCards.get(i);
			last = rankedCards.get(i + 4);
			// Found
			if (getRank(first).getValue() + 4 == getRank(last).getValue()) {
				log.debug("Found potential straight at " + first);
				generateOneStraightCards(rankedCards.subList(i, i + 5), results);
			}
		}
	}

	private void generateOneStraightCards(List<ImmutableSet<Card>> subList, List<ImmutableSet<Card>> results) {
		results.addAll(combos(subList));
	}

	public void generateFourOneCards(ImmutableSet<Card> allCards, List<ImmutableSet<Card>> rankedCards, Consumer<ImmutableSet<Card>> consumer) {
		generateMn(1, 4, rankedCards, consumer);
	}

	public void generateFullHouseCards(List<ImmutableSet<Card>> rankedCards, Consumer<ImmutableSet<Card>> consumer) {
		generateMn(2, 3, rankedCards, consumer);
	}

	private void generateMn(int m, int n, List<ImmutableSet<Card>> rankedCards, Consumer<ImmutableSet<Card>> consume) {
		// Find ranks with at least 3 pairs and 2 pairs
		List<ImmutableSet<Card>> nss = new ArrayList<>();
		List<ImmutableSet<Card>> mss = new ArrayList<>();
		for (ImmutableSet<Card> cards : rankedCards) {
			if (cards.size() >= m) {
				mss.add(cards);
			}
			if (cards.size() >= n) {
				nss.add(cards);
			}
		}
		log.debug("Threess: " + nss);
		log.debug("Twoss:" + mss);

		// The minimum set of full house must contains minimum 3 pairs and 2
		// pairs thus total 1 threes and 2 twos.
		List<ImmutableSet<Card>> ms = new ArrayList<>();
		List<ImmutableSet<Card>> ns = new ArrayList<>();
		// Generate the permutation set of all twos
		for (ImmutableSet<Card> cards : mss) {
			combos(cards, m, ms::add);
		}
		// Generate the permutation set of all threes
		for (ImmutableSet<Card> cards : nss) {
			combos(cards, n, ns::add);
		}
		log.debug("threes: " + ns);
		log.debug("twos: " + ms);
		// For each three, join with all two
		for (ImmutableSet<Card> ncards : ns) {
			for (ImmutableSet<Card> mcards : ms) {
				if (!isSameRank(ncards, mcards)) {
					consume.accept(ncards.addAll(mcards));
				}
			}
		}
	}

	private boolean isSameRank(ImmutableSet<Card> cards3, ImmutableSet<Card> cards2) {
		return getRank(cards3) == getRank(cards2);
	}

	private Rank getRank(ImmutableSet<Card> cards) {
		return cards.iterator().next().getRank();
	}

	private void generateSameRankMoves(List<ImmutableSet<Card>> rankedCards, Consumer<Move> consumer) {
		// Generate singles/pairs/triples/quadruples
		for (ImmutableSet<Card> cards : rankedCards)
			for (int i = 1; i <= cards.size(); i++)
				generateComboMoves(cards, i, consumer);
	}

	private void generateSameRankMoves(List<ImmutableSet<Card>> rankedCards, int n, Consumer<Move> consumer) {
		// Generate singles/pairs/triples/quadruples
		for (ImmutableSet<Card> cards : rankedCards)
			if (cards.size() == n)
				generateComboMoves(cards, n, consumer);
	}

	private void generateComboMoves(ImmutableSet<Card> cards, int n, Consumer<Move> consumer) {
		combos(cards, n, cards_ -> consumer.accept(new Move(sameRankMoveTypes[n], cards_)));
	}

	List<ImmutableSet<Card>> groupSameRanks(ImmutableSet<Card> cards) {
		Map<Rank, List<Card>> cardsByRank = cards.toOrderedSet().stream() //
				.collect(Collectors.groupingBy(Card::getRank));

		Set<Rank> sorted = new TreeSet<>();
		sorted.addAll(cardsByRank.keySet());
		List<ImmutableSet<Card>> results = new ArrayList<>();
		for (Rank rank : sorted)
			results.add(new ImmutableSet<>(cardsByRank.get(rank)));
		return results;
	}

	List<ImmutableSet<Card>> groupSameSuits(ImmutableSet<Card> cards) {
		Map<Suit, List<Card>> cardsBySuit = cards.toOrderedSet().stream() //
				.collect(Collectors.groupingBy(Card::getSuit));

		return cardsBySuit.keySet().stream() //
				.map(suit -> new ImmutableSet<>(cardsBySuit.get(suit))) //
				.collect(Collectors.toList());
	}

	/**
	 * Given a list of cards (cards 1, cards 2, etc) return all combinations of
	 * (any card in cards 1), (any card of cards 2) (etc).
	 */
	private List<ImmutableSet<Card>> combos(List<ImmutableSet<Card>> cardsList) {
		List<ImmutableSet<Card>> results = Arrays.asList(new ImmutableSet<Card>());

		for (ImmutableSet<Card> cards : cardsList) {
			List<ImmutableSet<Card>> results1 = new ArrayList<>();
			for (ImmutableSet<Card> result : results)
				for (Card card : cards)
					results1.add(result.add(card));
			results = results1;
		}

		return results;
	}

	/**
	 * Pick combinations of 'size' cards from first parameter, and add into
	 * results parameter.
	 */
	void combos(ImmutableSet<Card> cards, int size, Consumer<ImmutableSet<Card>> consumer) {
		int diff = cards.size() - size;
		if (diff >= 0)
			combos(cards, diff, empty, consumer);
	}

	private void combos(ImmutableSet<Card> cards, int diff, ImmutableSet<Card> cards0, Consumer<ImmutableSet<Card>> consumer) {
		Iterator<Card> iter = cards.iterator();
		if (diff > 0) {
			if (iter.hasNext()) {
				Card first = iter.next();
				ImmutableSet<Card> remainingCards = cards.remove(first);
				combos(remainingCards, diff - 1, cards0, consumer);
				combos(remainingCards, diff, cards0.add(first), consumer);
			}
		} else
			consumer.accept(cards.addAll(cards0));
	}

}
