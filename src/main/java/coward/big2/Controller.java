package coward.big2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coward.immutable.ImmutableSet;

public class Controller {

	public GameState shuffle() {
		List<Card> cards = new ArrayList<>(Card.allCards());
		Random random = new Random();
		int size = cards.size();

		for (int i = 0; i < size; i++) {
			int j = random.nextInt(size);
			Card temp = cards.get(i);
			cards.set(i, cards.get(j));
			cards.set(j, temp);
		}

		Hand[] hands = new Hand[Big2Constants.nPlayers];
		int start = 0;

		for (int player = 0; player < Big2Constants.nPlayers; player++) {
			int end = start + Big2Constants.nCardsPerPlayer;
			hands[player] = new Hand(cards.subList(start, end));
			start = end;
		}

		return new GameState(hands, new ArrayList<ImmutableSet<Card>>());
	}

}
