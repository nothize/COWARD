package coward.big2.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import coward.big2.Hand;
import coward.big2.card.Card;
import coward.big2.card.Rank;
import coward.immutable.ImmutableSet;

/**
 * Generate all possible moves based on a hand.
 * 
 * TODO generate full-house (3-2), four of a kind (4-1), straight, flush, straight flush
 */
public class MoveGenerator {
	private static final Log log = LogFactory.getLog(MoveGenerator.class);

	private ImmutableSet<Card> empty = new ImmutableSet<Card>();

	public List<ImmutableSet<Card>> generate(Hand hand) {
		List<ImmutableSet<Card>> rankedCards = groupSameRanks(hand.getCards());
		List<ImmutableSet<Card>> results = new ArrayList<>();

		generateSameRank(rankedCards, results);
		generateFullHouse(rankedCards, results);
		generateFourOne(hand.getCards(), rankedCards, results);

		return results;
	}

	public void generateFourOne(ImmutableSet<Card> allCards, List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		// Find ranks with at least 4 pairs
		List<ImmutableSet<Card>> fourss = new ArrayList<>();
		for (ImmutableSet<Card> cards : rankedCards) {
			if ( cards.size() >= 4 ) {
				fourss.add(cards);
			}
		}
		// 4 join 1
		for (ImmutableSet<Card> cards4 : fourss) {
			for (Card card : allCards) {
				if ( cards4.iterator().next().getRank() != card.getRank() ) {
					results.add(cards4.add(card));
				}
			}
		}
	}

	public void generateFullHouse(List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		// Find ranks with at least 3 pairs and 2 pairs
		List<ImmutableSet<Card>> threess = new ArrayList<>();
		List<ImmutableSet<Card>> twoss = new ArrayList<>();
		for (ImmutableSet<Card> cards : rankedCards) {
			if ( cards.size() >= 2 ) {
				twoss.add(cards);
				if ( cards.size() >= 3 ) {
					threess.add(cards);
				}
			}
		}
		log.debug("Threess: " + threess);
		log.debug("Twoss:" + twoss);
		
		// The minimum set of full house must contains minimum 3 pairs and 2 pairs thus total 1 threes and 2 twos.
		if ( threess.size() >= 1 && twoss.size() >= 2 ) {
			List<ImmutableSet<Card>> threes = new ArrayList<>();
			// Generate the permutation set of all threes
			for (ImmutableSet<Card> cards : threess) {
				permute(cards, 3, threes);
			}
			log.debug("threes: " + threes);
			List<ImmutableSet<Card>> twos = new ArrayList<>();
			// Generate the permutation set of all twos
			for (ImmutableSet<Card> cards : twoss) {
				permute(cards, 2, twos);
			}
			log.debug("twos: " + twos);
			// For each three, join with all two
			for (ImmutableSet<Card> cards3 : threes) {
				for (ImmutableSet<Card> cards2 : twos) {
					if ( !sameRank(cards3, cards2) ) {
						results.add(cards3.addAll(cards2));
					}
				}
			}
		}
	}

	private boolean sameRank(ImmutableSet<Card> cards3,
			ImmutableSet<Card> cards2) {
		return cards3.iterator().next().getRank() == cards2.iterator().next().getRank();
	}

	private void generateSameRank(List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		// Generate singles/pairs/triples/quadruples
		for (ImmutableSet<Card> cards : rankedCards)
			for (int i = 1; i <= cards.size(); i++)
				permute(cards, i, results);
	}

	List<ImmutableSet<Card>> groupSameRanks(ImmutableSet<Card> cards) {
		Multimap<Rank, Card> cardsByRank = ArrayListMultimap.create();

		for (Card card : cards)
			cardsByRank.put(card.getRank(), card);

		List<ImmutableSet<Card>> results = new ArrayList<>();
		for (Rank rank : cardsByRank.keySet())
			results.add(new ImmutableSet<>(cardsByRank.get(rank)));
		return results;
	}

	/**
	 * Pick combinations of 'size' cards from first parameter, and add into
	 * results parameter.
	 */
	void permute(ImmutableSet<Card> cards, int size, List<ImmutableSet<Card>> results) {
		int diff = cards.size() - size;
		if (diff >= 0)
			permute(cards, diff, empty, results);
	}

	private void permute(ImmutableSet<Card> cards, int diff, ImmutableSet<Card> cards0, List<ImmutableSet<Card>> results) {
		Iterator<Card> iter = cards.iterator();
		if (diff > 0) {
			if (iter.hasNext()) {
				Card first = iter.next();
				ImmutableSet<Card> remainingCards = cards.remove(first);
				permute(remainingCards, diff - 1, cards0, results);
				permute(remainingCards, diff, cards0.add(first), results);
			}
		} else
			results.add(cards.addAll(cards0));
	}

}
