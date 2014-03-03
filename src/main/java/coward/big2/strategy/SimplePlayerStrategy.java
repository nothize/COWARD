package coward.big2.strategy;

import coward.big2.Hand;
import coward.big2.PlayerView;
import coward.big2.card.Card;
import coward.immutable.ImmutableSet;

/**
 * Plays whatever card the player have on hand.
 * 
 * For testing only, not a valid strategy in real play.
 */
public class SimplePlayerStrategy implements PlayerStrategy {

	@Override
	public ImmutableSet<Card> play(PlayerView view) {
		Hand h = view.getHand();
		ImmutableSet<Card> cards = h.getCards();
		return new ImmutableSet<Card>().add(cards.iterator().next());
	}

}
