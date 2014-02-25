package coward.big2;

import java.util.Comparator;

public enum Rank {

	ACE__("A", 1), //
	N2___("2", 2), //
	N3___("3", 3), //
	N4___("4", 4), //
	N5___("5", 5), //
	N6___("6", 6), //
	N7___("7", 7), //
	N8___("8", 8), //
	N9___("9", 9), //
	N10__("T", 10), //
	JACK_("J", 11), //
	QUEEN("Q", 12), //
	KING_("K", 13), //
	;

	public static final Comparator<Rank> big2Comparator = new Comparator<Rank>() {
		public int compare(Rank rank0, Rank rank1) {
			return order(rank0) - order(rank1);
		}

		private int order(Rank rank) {
			switch (rank) {
			case N2___:
				return 15;
			case ACE__:
				return 14;
			default:
				return rank.value;
			}
		}
	};

	private String display;
	private int value;

	private Rank(String display, int value) {
		this.display = display;
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + display;
	}

}
