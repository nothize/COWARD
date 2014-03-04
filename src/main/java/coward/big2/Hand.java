package coward.big2;

import coward.big2.card.Card;
import coward.immutable.ImmutableSet;

public class Hand {

	private ImmutableSet<Card> cards;

	public Hand(ImmutableSet<Card> cards) {
		this.cards = cards;
	}

	public ImmutableSet<Card> getCards() {
		return cards;
	}
	
	@Override
	public String toString() {
		return toString(cards);
	}
	
	public static String toString(ImmutableSet<Card> cards) {
		StringBuilder sb = new StringBuilder();
		for (Card card : cards) {
			sb.append(card.toString());
			sb.append(" ");
		}
		return sb.toString();
	}
}
