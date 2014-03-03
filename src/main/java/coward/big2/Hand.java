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

}
