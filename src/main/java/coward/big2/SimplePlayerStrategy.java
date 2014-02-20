package coward.big2;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import coward.immutable.ImmutableSet;

public class SimplePlayerStrategy implements PlayerStrategy {

	public SimplePlayerStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<Card> play(PlayerView view) {
		Hand h = view.getHand();
		List<Card> cards = h.getCards();
		
		Set<Card> play = new TreeSet<Card>();
		play.add(cards.remove(0));
		return play;
	}

}
