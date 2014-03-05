package coward.big2.card;

import java.util.ArrayList;
import java.util.List;

public class Card implements Comparable<Card> {

	private Suit suit;
	private Rank rank;
	
	public static final Card SA = new Card(Suit.SPADE__, Rank.ACE__);
	public static final Card HA = new Card(Suit.HEART__, Rank.ACE__);
	public static final Card CA = new Card(Suit.CLUB___, Rank.ACE__);
	public static final Card DA = new Card(Suit.DIAMOND, Rank.ACE__);
	public static final Card S2 = new Card(Suit.SPADE__, Rank.N2___);
	public static final Card H2 = new Card(Suit.HEART__, Rank.N2___);
	public static final Card C2 = new Card(Suit.CLUB___, Rank.N2___);
	public static final Card D2 = new Card(Suit.DIAMOND, Rank.N2___);
	public static final Card S3 = new Card(Suit.SPADE__, Rank.N3___);
	public static final Card H3 = new Card(Suit.HEART__, Rank.N3___);
	public static final Card C3 = new Card(Suit.CLUB___, Rank.N3___);
	public static final Card D3 = new Card(Suit.DIAMOND, Rank.N3___);
	public static final Card S4 = new Card(Suit.SPADE__, Rank.N4___);
	public static final Card H4 = new Card(Suit.HEART__, Rank.N4___);
	public static final Card C4 = new Card(Suit.CLUB___, Rank.N4___);
	public static final Card D4 = new Card(Suit.DIAMOND, Rank.N4___);
	public static final Card S5 = new Card(Suit.SPADE__, Rank.N5___);
	public static final Card H5 = new Card(Suit.HEART__, Rank.N5___);
	public static final Card C5 = new Card(Suit.CLUB___, Rank.N5___);
	public static final Card D5 = new Card(Suit.DIAMOND, Rank.N5___);
	public static final Card S6 = new Card(Suit.SPADE__, Rank.N6___);
	public static final Card H6 = new Card(Suit.HEART__, Rank.N6___);
	public static final Card C6 = new Card(Suit.CLUB___, Rank.N6___);
	public static final Card D6 = new Card(Suit.DIAMOND, Rank.N6___);
	public static final Card S7 = new Card(Suit.SPADE__, Rank.N7___);
	public static final Card H7 = new Card(Suit.HEART__, Rank.N7___);
	public static final Card C7 = new Card(Suit.CLUB___, Rank.N7___);
	public static final Card D7 = new Card(Suit.DIAMOND, Rank.N7___);
	public static final Card S8 = new Card(Suit.SPADE__, Rank.N8___);
	public static final Card H8 = new Card(Suit.HEART__, Rank.N8___);
	public static final Card C8 = new Card(Suit.CLUB___, Rank.N8___);
	public static final Card D8 = new Card(Suit.DIAMOND, Rank.N8___);
	public static final Card S9 = new Card(Suit.SPADE__, Rank.N9___);
	public static final Card H9 = new Card(Suit.HEART__, Rank.N9___);
	public static final Card C9 = new Card(Suit.CLUB___, Rank.N9___);
	public static final Card D9 = new Card(Suit.DIAMOND, Rank.N9___);
	public static final Card ST = new Card(Suit.SPADE__, Rank.N10__);
	public static final Card HT = new Card(Suit.HEART__, Rank.N10__);
	public static final Card CT = new Card(Suit.CLUB___, Rank.N10__);
	public static final Card DT = new Card(Suit.DIAMOND, Rank.N10__);
	public static final Card SJ = new Card(Suit.SPADE__, Rank.JACK_);
	public static final Card HJ = new Card(Suit.HEART__, Rank.JACK_);
	public static final Card CJ = new Card(Suit.CLUB___, Rank.JACK_);
	public static final Card DJ = new Card(Suit.DIAMOND, Rank.JACK_);
	public static final Card SQ = new Card(Suit.SPADE__, Rank.QUEEN);
	public static final Card HQ = new Card(Suit.HEART__, Rank.QUEEN);
	public static final Card CQ = new Card(Suit.CLUB___, Rank.QUEEN);
	public static final Card DQ = new Card(Suit.DIAMOND, Rank.QUEEN);
	public static final Card SK = new Card(Suit.SPADE__, Rank.KING_);
	public static final Card HK = new Card(Suit.HEART__, Rank.KING_);
	public static final Card CK = new Card(Suit.CLUB___, Rank.KING_);
	public static final Card DK = new Card(Suit.DIAMOND, Rank.KING_);
	
	private static List<Card> allCards = new ArrayList<>();

	static {
		for (Suit suit : Suit.values())
			for (Rank rank : Rank.values())
				allCards.add(new Card(suit, rank));
	}

	private Card(Suit suit, Rank rank) {
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
		c = c == 0 ? Rank.big2Comparator.compare(rank, card.rank) : c;
		c = c == 0 ? -(suit.ordinal() - card.suit.ordinal()) : c;
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

	@Override
	public String toString() {
		return rank.toString() + suit.name().charAt(0);
	}

}
