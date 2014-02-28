package coward.big2;

import java.util.ArrayDeque;
import java.util.Deque;

import coward.immutable.ImmutableList;
import coward.immutable.ImmutableSet;

public class GameState {

	private Hand[] hands;
	private ImmutableList<ImmutableSet<Card>> playedCards = ImmutableList.empty();

	public GameState(Hand[] hands, ImmutableList<ImmutableSet<Card>> playedCards) {
		this.hands = hands;
		this.playedCards = playedCards;
	}

	public Hand[] getHands() {
		return hands;
	}

	public ImmutableList<ImmutableSet<Card>> getPlayedCards() {
		return playedCards;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("[HANDS]\n");

		for (int i = 0; i < hands.length; i++) {
			sb.append("Player " + i + ":");
			for (Card card : hands[i].getCards())
				sb.append(" " + card);
			sb.append("\n");
		}

		sb.append("[HISTORY]\n");

		Deque<ImmutableSet<Card>> deque = new ArrayDeque<>();
		for (ImmutableSet<Card> cards : playedCards)
			// reverse order
			deque.addFirst(cards);

		for (ImmutableSet<Card> cards : deque) {
			for (Card card : cards)
				sb.append(card + " ");
			sb.append("\n");
		}

		return sb.toString();
	}

	public int getNextPlayer(int player) {
		return (player + 1) % hands.length;
	}
	
	public int getPreviousPlayer(int player) {
		return (player - 1) % hands.length;
	}
}
