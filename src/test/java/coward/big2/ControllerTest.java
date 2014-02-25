package coward.big2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ControllerTest {
	Controller c;
	
	@Before
	public void init() {
		c = new Controller();
	}
	
	@Test
	public void testShuffle() throws Exception {
		GameState gs = c.shuffle();
		Hand[] hands = gs.getHands();
		for (Hand h : hands) {
			List<Card> cards = h.getCards();
			System.out.println(cards);
		}
	}
}
