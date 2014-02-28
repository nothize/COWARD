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

	/**
	 * @param currentGameState
	 * @param lastGameState
	 * @return
	 * the player who:
	 * 1. if this is the first round, the one who got Diamond 3
	 * 2. if this is not the first round, the one who won in last round
	 */
	public int findFirstPlayer(GameState currentGameState, GameState lastGameState) {
		if ( lastGameState != null ) {
			Hand[] hands = lastGameState.getHands();
			for (int j = 0; j < hands.length; j++) {
				if ( hands[j].getCards().isEmpty() ) {
					return j;
				}
			}
		}
		Hand[] hands = currentGameState.getHands();
		for (int j = 0; j < hands.length; j++) {
			if ( hands[j].getCards().find(new Card(Suit.DIAMOND, Rank.N3___)) != null ) {
				return j;
			}
		}
		
		return 0;
	}

	public boolean endGame(GameState gs) {
		Hand[] hands = gs.getHands();
		for (Hand hand : hands) {
			if ( hand.getCards().isEmpty() ) {
				return true;
			}
		}
		return false;
	}

	// TODO Determine if the player want to play another game
	public boolean playAgain() {
		return false;
	}

}
