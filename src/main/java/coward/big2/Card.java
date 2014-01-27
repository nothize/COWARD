package coward.big2;

import java.util.ArrayList;
import java.util.List;

public class Card {

	private Suit suit;
	private FaceValue faceValue;

	private static List<Card> allCards = new ArrayList<>();

	static {
		for (Suit suit : Suit.values())
			for (FaceValue faceValue : FaceValue.values())
				allCards.add(new Card(suit, faceValue));
	}

	public Card(Suit suit, FaceValue faceValue) {
		this.suit = suit;
		this.faceValue = faceValue;
	}

	public static List<Card> allCards() {
		return allCards;
	}

	public Suit getSuit() {
		return suit;
	}

	public FaceValue getFaceValue() {
		return faceValue;
	}

}
