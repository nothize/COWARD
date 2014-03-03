package coward.big2.strategy;

import coward.big2.Card;
import coward.big2.Hand;
import coward.big2.PlayerView;
import coward.immutable.ImmutableSet;

public class SimplePlayerStrategy implements PlayerStrategy {

	@Override
	public ImmutableSet<Card> play(PlayerView view) {
		Hand h = view.getHand();
		ImmutableSet<Card> cards = h.getCards();
		return new ImmutableSet<Card>().add(cards.iterator().next());
	}

}
