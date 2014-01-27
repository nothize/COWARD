package coward.big2;

import java.util.List;
import java.util.Random;

public class Deck {

	private List<Card> cards;

	public Deck() {
		cards = Card.allCards();
	}

	public void shuffle() {
		Random random = new Random();
		int size = cards.size();

		for (int i = 0; i < size; i++) {
			int j = random.nextInt(size);
			Card temp = cards.get(i);
			cards.set(i, cards.get(j));
			cards.set(j, temp);
		}
	}

}
