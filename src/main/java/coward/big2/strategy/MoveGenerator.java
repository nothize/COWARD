package coward.big2.strategy;

import java.util.ArrayList;
import java.util.Arrays;
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
		generateStraight(hand.getCards(), rankedCards, results);

		return results;
	}

	/**
	 * Straight is 5 continuous ranks. eg. 1,2,3,4,5     J,Q,K,A,2
	 * 
	 * @param cards
	 * @param results
	 * @param results 
	 */
	public void generateStraight(ImmutableSet<Card> allCards,
			List<ImmutableSet<Card>> rankedCards, List<ImmutableSet<Card>> results) {
		ImmutableSet<Card> first;
		ImmutableSet<Card> last;
		for (int i = 0; i + 4 < rankedCards.size(); i++) {
			first = rankedCards.get(i);
			last = rankedCards.get(i+4);
			// Found
			if ( getRank(first).getValue() + 4 == getRank(last).getValue() ) {
				generateOneStraight(rankedCards.subList(i, i+5), results);
			}
		}
	}

	private void generateOneStraight(List<ImmutableSet<Card>> subList,
			List<ImmutableSet<Card>> results) {
		results.addAll(combos(subList));
	}

	public void generateFourOne(ImmutableSet<Card> allCards, List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		generateMn(1, 4, rankedCards, results);
	}

	public void generateFullHouse(List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		generateMn(2, 3, rankedCards, results);
	}

	private void generateMn(int m, int n, List<ImmutableSet<Card>> rankedCards, List<ImmutableSet<Card>> results) {
		// Find ranks with at least 3 pairs and 2 pairs
		List<ImmutableSet<Card>> nss = new ArrayList<>();
		List<ImmutableSet<Card>> mss = new ArrayList<>();
		for (ImmutableSet<Card> cards : rankedCards) {
			if ( cards.size() >= m ) {
				mss.add(cards);
			}
			if ( cards.size() >= n ) {
				nss.add(cards);
			}
		}
		log.debug("Threess: " + nss);
		log.debug("Twoss:" + mss);
		
		// The minimum set of full house must contains minimum 3 pairs and 2 pairs thus total 1 threes and 2 twos.
		List<ImmutableSet<Card>> ms = new ArrayList<>();
		List<ImmutableSet<Card>> ns = new ArrayList<>();
		// Generate the permutation set of all twos
		for (ImmutableSet<Card> cards : mss) {
			combos(cards, m, ms);
		}
		// Generate the permutation set of all threes
		for (ImmutableSet<Card> cards : nss) {
			combos(cards, n, ns);
		}
		log.debug("threes: " + ns);
		log.debug("twos: " + ms);
		// For each three, join with all two
		for (ImmutableSet<Card> ncards : ns) {
			for (ImmutableSet<Card> mcards : ms) {
				if ( !isSameRank(ncards, mcards) ) {
					results.add(ncards.addAll(mcards));
				}
			}
		}
	}

	private boolean isSameRank(ImmutableSet<Card> cards3,
			ImmutableSet<Card> cards2) {
		return getRank(cards3) == getRank(cards2);
	}

	private Rank getRank(ImmutableSet<Card> cards) {
		return cards.iterator().next().getRank();
	}

	private void generateSameRank(List<ImmutableSet<Card>> rankedCards,
			List<ImmutableSet<Card>> results) {
		// Generate singles/pairs/triples/quadruples
		for (ImmutableSet<Card> cards : rankedCards)
			for (int i = 1; i <= cards.size(); i++)
				combos(cards, i, results);
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

	private List<ImmutableSet<Card>> combos(List<ImmutableSet<Card>> cardsList) {
		if (!cardsList.isEmpty()) {
			ImmutableSet<Card> head = cardsList.get(0);
			List<ImmutableSet<Card>> tail = cardsList.subList(1, cardsList.size());

			List<ImmutableSet<Card>> results = new ArrayList<>();
			for (ImmutableSet<Card> cards : combos(tail))
				for (Card card : head)
					results.add(cards.add(card));
			return results;
		} else
			return Arrays.asList(new ImmutableSet<>());
	}

	/**
	 * Pick combinations of 'size' cards from first parameter, and add into
	 * results parameter.
	 */
	void combos(ImmutableSet<Card> cards, int size, List<ImmutableSet<Card>> results) {
		int diff = cards.size() - size;
		if (diff >= 0)
			combos(cards, diff, empty, results);
	}

	private void combos(ImmutableSet<Card> cards, int diff, ImmutableSet<Card> cards0, List<ImmutableSet<Card>> results) {
		Iterator<Card> iter = cards.iterator();
		if (diff > 0) {
			if (iter.hasNext()) {
				Card first = iter.next();
				ImmutableSet<Card> remainingCards = cards.remove(first);
				combos(remainingCards, diff - 1, cards0, results);
				combos(remainingCards, diff, cards0.add(first), results);
			}
		} else
			results.add(cards.addAll(cards0));
	}

}
