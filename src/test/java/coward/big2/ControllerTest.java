package coward.big2;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterators;

import coward.immutable.ImmutableSet;

public class ControllerTest {

	private Random random = new Random();
	private Controller controller = new Controller();

	@Before
	public void init() {
		controller = new Controller();
	}
	
	@Test
	public void testShuffle() {
		GameState gameState = controller.shuffle();
		System.out.println(gameState.toString());

		for (Hand hand : gameState.getHands())
			assertEquals(13, hand.getCards().size());
	}

	@Test
	public void testPlayCards() {
		GameState gameState = controller.shuffle();

		for (int i = 0; i < 10; i++)
			for (int player = 0; player < Big2Constants.nPlayers; player++) {
				Card[] cards = Iterators.toArray(gameState.getHands()[player].getCards().iterator(), Card.class);
				Card card = cards[random.nextInt(cards.length)];

				gameState = controller.playCards(gameState, player, new ImmutableSet<>(Arrays.asList(card)));
			}

		for (int player = 0; player < Big2Constants.nPlayers; player++)
			assertEquals(13 - 10, gameState.getHands()[player].getCards().size());
		assertEquals(10 * Big2Constants.nPlayers, gameState.getPlayedCards().size());

		System.out.println(gameState.toString());
	}
}
