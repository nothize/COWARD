package coward.big2;

import java.util.ArrayDeque;
import java.util.Deque;

import coward.immutable.ImmutableList;
import coward.immutable.ImmutableSet;

public class GameState {

	private Hand[] hands;
	private ImmutableList<GameMove> playedMoves = ImmutableList.empty();
	private GameState previous;

	public GameState(Hand[] hands, ImmutableList<GameMove> playedMoves) {
		this(hands, playedMoves, null);
	}

	public GameState(Hand[] hands, ImmutableList<GameMove> playedMoves, GameState previous) {
		this.hands = hands;
		this.playedMoves = playedMoves;
		this.previous = previous;
	}

	public Hand[] getHands() {
		return hands;
	}

	public ImmutableList<GameMove> getPlayedMoves() {
		return playedMoves;
	}

	public GameState getPrevious() {
		return previous;
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
		for (GameMove gameMove : playedMoves)
			// reverse order
			deque.addFirst(gameMove.getPlayedCards());

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
		int previousPlayer = player - 1;
		if (previousPlayer < 0)
			previousPlayer += hands.length;
		return previousPlayer;
	}

}
