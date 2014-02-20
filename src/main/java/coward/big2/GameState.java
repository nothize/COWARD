package coward.big2;

import coward.immutable.ImmutableSet;

public class GameState {

	private Hand[] hands;
	private ImmutableSet<Card> playedCards = new ImmutableSet<>();

	public GameState(Hand[] hands, ImmutableSet<Card> playedCards) {
		this.hands = hands;
		this.playedCards = playedCards;
	}

	public Hand[] getHands() {
		return hands;
	}

	public ImmutableSet<Card> getPlayedCards() {
		return playedCards;
	}

}
