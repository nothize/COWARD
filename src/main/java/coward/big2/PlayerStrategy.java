package coward.big2;

import java.util.Set;

/**
 * How a player plays.
 * 
 * @author ywsing
 */
public interface PlayerStrategy {

	/**
	 * @return the player's decision (what card to play) when given a view to a
	 *         game.
	 */
	public Set<Card> play(PlayerView view);

}
