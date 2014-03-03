package coward.big2;

import coward.big2.card.Card;
import coward.immutable.ImmutableSet;

public class GameMove {

	private int player;
	private ImmutableSet<Card> playedCards;

	public GameMove(int player, ImmutableSet<Card> playedCards) {
		this.player = player;
		this.playedCards = playedCards;
	}

	public int getPlayer() {
		return player;
	}

	public ImmutableSet<Card> getPlayedCards() {
		return playedCards;
	}

}
