package coward.big2;

import java.util.ArrayList;
import java.util.List;

public class Card implements Comparable<Card> {

	private Suit suit;
	private Rank rank;

	private static List<Card> allCards = new ArrayList<>();

	static {
		for (Suit suit : Suit.values())
			for (Rank rank : Rank.values())
				allCards.add(new Card(suit, rank));
	}

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public static List<Card> allCards() {
		return allCards;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	@Override
	public int compareTo(Card card) {
		int c = 0;
		c = c == 0 ? -(suit.ordinal() - card.suit.ordinal()) : c;
		c = c == 0 ? Rank.big2Comparator.compare(rank, card.rank) : c;
		return c;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + (rank != null ? rank.hashCode() : 0);
		result = 31 * result + (suit != null ? suit.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Card) {
			Card card = (Card) object;
			return card.suit == suit && card.rank == rank;
		} else
			return false;
	}

}
