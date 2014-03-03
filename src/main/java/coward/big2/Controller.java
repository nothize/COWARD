package coward.big2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import coward.immutable.ImmutableList;
import coward.immutable.ImmutableSet;

public class Controller {

	private List<PlayerStrategy> playerStrategies;

	public Controller(List<PlayerStrategy> playerStrategies) {
		this.playerStrategies = playerStrategies;
	}

	public GameState runGame(GameState pgs) {
		GameState gs = shuffle();
		return startGame(gs, findFirstPlayer(gs, pgs));
	}

	public GameState startGame( GameState gs, int player) {
		do {
			// Finds the current player, shows him the table view, let him play his cards
			PlayerStrategy currentPlayerStrategy = playerStrategies.get(player);
			SimplePlayerView currentPlayerView = new SimplePlayerView(gs, player);
			ImmutableSet<Card> playCards = currentPlayerStrategy.play(currentPlayerView);
			gs = playCards(gs, new GameMove(player, playCards));
			player = gs.getNextPlayer(player);
		} while (!endGame(gs));
		player = gs.getPreviousPlayer(player);
		return gs;
	}

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

		return new GameState(hands, ImmutableList.<GameMove> empty());
	}

	public GameState playCards(GameState gameState, GameMove move) {
		int player = move.getPlayer();
		ImmutableSet<Card> cards = move.getPlayedCards();

		Hand[] hands = gameState.getHands();
		Hand[] hands1 = new Hand[hands.length];

		for (int i = 0; i < hands.length; i++)
			if (i == player)
				hands1[i] = new Hand(hands[i].getCards().removeAll(cards));
			else
				hands1[i] = hands[i];

		ImmutableList<GameMove> playedMoves1 = ImmutableList.cons(move, gameState.getPlayedMoves());

		return new GameState(hands1, playedMoves1, gameState);
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

}
