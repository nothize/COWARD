package coward.big2;

import java.util.ArrayList;
import java.util.List;

import coward.immutable.ImmutableSet;

public class GameState {

	private Hand[] hands;
	private List<ImmutableSet<Card>> playedCards = new ArrayList<ImmutableSet<Card>>();

	public GameState(Hand[] hands, List<ImmutableSet<Card>> playedCards) {
		this.hands = hands;
		this.playedCards = playedCards;
	}

	public Hand[] getHands() {
		return hands;
	}

	public List<ImmutableSet<Card>> getPlayedCards() {
		return playedCards;
	}

}
