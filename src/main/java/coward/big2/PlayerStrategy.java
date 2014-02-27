package coward.big2;

import coward.immutable.ImmutableSet;

/**
 * How a player plays.
 * TODO implements the interface
 * 
 * @author ywsing
 */
public interface PlayerStrategy {

	/**
	 * @return the player's decision (what card to play) when given a view to a
	 *         game.
	 */
	public ImmutableSet<Card> play(PlayerView view);

}
