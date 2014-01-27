package coward.big2;

import java.util.HashSet;
import java.util.Set;

public class GameState {

	private Hand[] hands;
	private Set<Card> playedCards = new HashSet<>();

	public GameState(Hand[] hands, Set<Card> playedCards) {
		this.hands = hands;
		this.playedCards = playedCards;
	}

	public Hand[] getHands() {
		return hands;
	}

	public Set<Card> getPlayedCards() {
		return playedCards;
	}

}
