package coward.big2;

import java.util.ArrayList;
import java.util.List;

public class Card {

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

}
