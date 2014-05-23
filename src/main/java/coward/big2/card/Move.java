	package coward.big2.card;

import java.util.function.Function;

import coward.immutable.ImmutableSet;

public class Move implements Comparable<Move> {

	public enum MoveType {
		SINGLE, DOUBLE, TRIPLE, QUADRUPLE, STRAIGHT, FLUSH, FULLHOUSE, FOURONE, STRAIGHTFLUSH;
	}

	private MoveType moveType;
	private ImmutableSet<Card> cards;

	public Move(MoveType moveType, ImmutableSet<Card> cards) {
		this.moveType = moveType;
		this.cards = cards;
	}

	@Override
	public int compareTo(Move move) {
		Function<ImmutableSet<Card>, Card> maxFun = cards_ -> {
			Card max = Card.D3;
			for (Card card : cards_)
				if (card.compareTo(max) > 0)
					max = card;
			return max;
		};

		int c = moveType.compareTo(move.moveType);
		if (c == 0) {
			Card max0 = maxFun.apply(cards), max1 = maxFun.apply(move.cards);
			return max0.compareTo(max1);
		} else
			return c;
	}

	public int hashCode() {
		return moveType.hashCode() ^ cards.hashCode();
	}

	public boolean equals(Object object) {
		if (object.getClass() == Move.class) {
			Move move = (Move) object;
			return moveType == move.moveType && cards.equals(move.cards);
		} else
			return false;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public ImmutableSet<Card> getCards() {
		return cards;
	}

}
