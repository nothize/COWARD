package coward.big2;

import static org.junit.Assert.*;

import org.junit.Test;

public class Big2GameTest {
	Big2Game game = new Big2Game();

	@Test
	public void testStartGame() {
		Controller c = new Controller();
		GameState pgs = null;
		GameState gs = c.shuffle();
		
		int player = 0;
		pgs = game.startGame(c, gs, player, new SimplePlayerStrategy());
		
		boolean b = false;
		Hand[] hands = pgs.getHands();
		for (int i = 0; i < hands.length; i++) {
			if ( hands[i].getCards().size() == 0 ) {
				assertEquals(player, i);
				b = true;
			}
		}
		assertTrue(b);
		b = false;
		pgs = game.runGame(c, pgs);
		hands = pgs.getHands();
		for (int i = 0; i < hands.length; i++) {
			if ( hands[i].getCards().size() == 0 ) {
				assertEquals(player, i);
				b = true;
			}
		}
		assertTrue(b);
	}

}
