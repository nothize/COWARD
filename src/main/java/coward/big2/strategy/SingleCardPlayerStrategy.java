package coward.big2.strategy;

import coward.big2.GameMove;
import coward.big2.Hand;
import coward.big2.PlayerView;
import coward.big2.card.Card;
import coward.immutable.ImmutableSet;

/**
 * A player strategy to play the smallest single card if possible.
 * 
 * @author ywsing
 */
public class SingleCardPlayerStrategy implements PlayerStrategy {

	@Override
	public ImmutableSet<Card> play(PlayerView view) {
		ImmutableSet<Card> lastPlayedCards = findLastPlayedCards(view);

		Hand hand = view.getHand();
		ImmutableSet<Card> cards = hand.getCards();

		if (lastPlayedCards == null) // We control the game
			return new ImmutableSet<Card>().add(cards.iterator().next());
		else if (lastPlayedCards.size() == 1) {
			Card lastPlayedCard = lastPlayedCards.iterator().next();

			// Have to follow with a single card, choose any card larger than
			// his
			for (Card card : cards)
				if (card.compareTo(lastPlayedCard) > 0)
					return new ImmutableSet<Card>().add(card);

			// Our cards are too small to play anything
			return new ImmutableSet<Card>();
		} else
			return new ImmutableSet<Card>(); // Pass
	}

	/**
	 * @return the last move by any player, excluding those who passed. If
	 *         current player controls the game (i.e. he do not need to follow
	 *         the previous move), lastPlayedCards would be null.
	 */
	private ImmutableSet<Card> findLastPlayedCards(PlayerView view) {
		ImmutableSet<Card> lastPlayedCards = null;

		for (GameMove move : view.getPlayedMoves())
			if (!move.getPlayedCards().isEmpty()) {
				lastPlayedCards = move.getPlayedCards();
				break;
			} else if (move.getPlayer() == view.currentPlayerNum())
				break;
		return lastPlayedCards;
	}

}
