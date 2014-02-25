package coward.big2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coward.immutable.ImmutableList;
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
			hands[player] = new Hand(new ImmutableSet<>(cards.subList(start, end)));
			start = end;
		}

		return new GameState(hands, ImmutableList.<ImmutableSet<Card>> empty());
	}

	public GameState playCards(GameState gameState, int player, ImmutableSet<Card> cards) {
		Hand[] hands = gameState.getHands();
		Hand[] hands1 = new Hand[hands.length];

		for (int i = 0; i < hands.length; i++)
			if (i == player)
				hands1[i] = new Hand(hands[i].getCards().removeAll(cards));
			else
				hands1[i] = hands[i];

		ImmutableList<ImmutableSet<Card>> playedCards1 = ImmutableList.cons(cards, gameState.getPlayedCards());

		return new GameState(hands1, playedCards1);
	}

}
