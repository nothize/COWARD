package coward.big2.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import coward.big2.Hand;
import coward.big2.card.Card;
import coward.big2.card.Rank;
import coward.immutable.ImmutableSet;

/**
 * Generate all possible moves based on a hand.
 * 
 * TODO generate full-house (3-2), 4-1, ordered 5, flower
 */
public class MoveGenerator {

	private ImmutableSet<Card> empty = new ImmutableSet<Card>();

	public List<ImmutableSet<Card>> generate(Hand hand) {
		List<ImmutableSet<Card>> rankedCards = groupSameRanks(hand.getCards());
		List<ImmutableSet<Card>> results = new ArrayList<>();

		// Generate singles/pairs/triples/quadruples
		for (ImmutableSet<Card> cards : rankedCards)
			for (int i = 1; i <= cards.size(); i++)
				permute(cards, i, results);

		return results;
	}

	private List<ImmutableSet<Card>> groupSameRanks(ImmutableSet<Card> cards) {
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
	private void permute(ImmutableSet<Card> cards, int size, List<ImmutableSet<Card>> results) {
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
