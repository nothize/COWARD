package coward.big2;

import coward.immutable.ImmutableList;

/**
 * What a player sees when he is the current player.
 * TODO implements the interface
 *
 * @author ywsing
 */
public interface PlayerView {

	/**
	 * @return the order of the current player, 0-3.
	 */
	public int currentPlayerNum();

	/**
	 * @return history of previously played cards.
	 */
	public ImmutableList<GameMove> getPlayedCards();

	/**
	 * @return the player's own hand.
	 */
	public Hand getHand();

	/**
	 * @return a boolean array with 4 elements that indicate whether that player
	 *         announced he only got a single card ("last card") on hand.
	 */
	public boolean[] isLastCard();

}
