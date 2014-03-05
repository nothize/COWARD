package coward.big2.card;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Card implements Comparable<Card> {

	private Suit suit;
	private Rank rank;

	private static EnumMap<Suit, EnumMap<Rank, Card>> map = new EnumMap<>(Suit.class);
	private static List<Card> allCards = new ArrayList<>();

	static {
		for (Suit suit : Suit.values()) {
			EnumMap<Rank, Card> cardsInRank = new EnumMap<>(Rank.class);
			map.put(suit, cardsInRank);
			for (Rank rank : Rank.values()) {
				Card card = new Card(suit, rank);
				cardsInRank.put(rank, card);
				allCards.add(card);
			}
		}
	}

	public static final Card SA = lookup(Suit.SPADE__, Rank.ACE__);
	public static final Card HA = lookup(Suit.HEART__, Rank.ACE__);
	public static final Card CA = lookup(Suit.CLUB___, Rank.ACE__);
	public static final Card DA = lookup(Suit.DIAMOND, Rank.ACE__);
	public static final Card S2 = lookup(Suit.SPADE__, Rank.N2___);
	public static final Card H2 = lookup(Suit.HEART__, Rank.N2___);
	public static final Card C2 = lookup(Suit.CLUB___, Rank.N2___);
	public static final Card D2 = lookup(Suit.DIAMOND, Rank.N2___);
	public static final Card S3 = lookup(Suit.SPADE__, Rank.N3___);
	public static final Card H3 = lookup(Suit.HEART__, Rank.N3___);
	public static final Card C3 = lookup(Suit.CLUB___, Rank.N3___);
	public static final Card D3 = lookup(Suit.DIAMOND, Rank.N3___);
	public static final Card S4 = lookup(Suit.SPADE__, Rank.N4___);
	public static final Card H4 = lookup(Suit.HEART__, Rank.N4___);
	public static final Card C4 = lookup(Suit.CLUB___, Rank.N4___);
	public static final Card D4 = lookup(Suit.DIAMOND, Rank.N4___);
	public static final Card S5 = lookup(Suit.SPADE__, Rank.N5___);
	public static final Card H5 = lookup(Suit.HEART__, Rank.N5___);
	public static final Card C5 = lookup(Suit.CLUB___, Rank.N5___);
	public static final Card D5 = lookup(Suit.DIAMOND, Rank.N5___);
	public static final Card S6 = lookup(Suit.SPADE__, Rank.N6___);
	public static final Card H6 = lookup(Suit.HEART__, Rank.N6___);
	public static final Card C6 = lookup(Suit.CLUB___, Rank.N6___);
	public static final Card D6 = lookup(Suit.DIAMOND, Rank.N6___);
	public static final Card S7 = lookup(Suit.SPADE__, Rank.N7___);
	public static final Card H7 = lookup(Suit.HEART__, Rank.N7___);
	public static final Card C7 = lookup(Suit.CLUB___, Rank.N7___);
	public static final Card D7 = lookup(Suit.DIAMOND, Rank.N7___);
	public static final Card S8 = lookup(Suit.SPADE__, Rank.N8___);
	public static final Card H8 = lookup(Suit.HEART__, Rank.N8___);
	public static final Card C8 = lookup(Suit.CLUB___, Rank.N8___);
	public static final Card D8 = lookup(Suit.DIAMOND, Rank.N8___);
	public static final Card S9 = lookup(Suit.SPADE__, Rank.N9___);
	public static final Card H9 = lookup(Suit.HEART__, Rank.N9___);
	public static final Card C9 = lookup(Suit.CLUB___, Rank.N9___);
	public static final Card D9 = lookup(Suit.DIAMOND, Rank.N9___);
	public static final Card ST = lookup(Suit.SPADE__, Rank.N10__);
	public static final Card HT = lookup(Suit.HEART__, Rank.N10__);
	public static final Card CT = lookup(Suit.CLUB___, Rank.N10__);
	public static final Card DT = lookup(Suit.DIAMOND, Rank.N10__);
	public static final Card SJ = lookup(Suit.SPADE__, Rank.JACK_);
	public static final Card HJ = lookup(Suit.HEART__, Rank.JACK_);
	public static final Card CJ = lookup(Suit.CLUB___, Rank.JACK_);
	public static final Card DJ = lookup(Suit.DIAMOND, Rank.JACK_);
	public static final Card SQ = lookup(Suit.SPADE__, Rank.QUEEN);
	public static final Card HQ = lookup(Suit.HEART__, Rank.QUEEN);
	public static final Card CQ = lookup(Suit.CLUB___, Rank.QUEEN);
	public static final Card DQ = lookup(Suit.DIAMOND, Rank.QUEEN);
	public static final Card SK = lookup(Suit.SPADE__, Rank.KING_);
	public static final Card HK = lookup(Suit.HEART__, Rank.KING_);
	public static final Card CK = lookup(Suit.CLUB___, Rank.KING_);
	public static final Card DK = lookup(Suit.DIAMOND, Rank.KING_);

	private Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public static Card lookup(Suit suit, Rank rank) {
		return map.get(suit).get(rank);
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
