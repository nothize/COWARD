package coward.big2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameStateTest {
	@Test
	public void testGetPreviousPlayer() {
		Controller c = new Controller();
		GameState gs = c.shuffle();
		assertEquals(0, gs.getPreviousPlayer(1));
		assertEquals(gs.getHands().length-1, gs.getPreviousPlayer(0));
	}

}
