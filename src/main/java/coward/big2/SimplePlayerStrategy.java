package coward.big2;

import java.util.HashSet;
import java.util.Set;

import coward.immutable.ImmutableSet;

public class SimplePlayerStrategy implements PlayerStrategy {

	public SimplePlayerStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Card> play(PlayerView view) {
		Hand h = view.getHand();
		ImmutableSet<Card> cards = h.getCards();
		
		HashSet<Card> play = new HashSet<Card>();
		play.add(cards.iterator().next());
		return play;
	}

}
