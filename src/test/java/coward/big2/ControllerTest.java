package coward.big2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterators;

import coward.big2.card.Card;
import coward.big2.strategy.PlayerStrategy;
import coward.big2.strategy.SimplePlayerStrategy;
import coward.immutable.ImmutableSet;

public class ControllerTest {

	private Random random = new Random();
	private Controller controller;

	@Before
	public void init() {
		List<PlayerStrategy> playerStrategies = new ArrayList<>();

		for (int player = 0; player < Big2Constants.nPlayers; player++)
			playerStrategies.add(new SimplePlayerStrategy());

		controller = new Controller(playerStrategies);
	}

	@Test
	public void testGetPreviousPlayer() {
		GameState gs = controller.shuffle();
		assertEquals(0, gs.getPreviousPlayer(1));
		assertEquals(gs.getHands().length - 1, gs.getPreviousPlayer(0));
	}

	@Test
	public void testShuffle() {
		GameState gameState = controller.shuffle();
		System.out.println(gameState.toString());

		for (Hand hand : gameState.getHands())
			assertEquals(13, hand.getCards().size());
	}


	@Test
	public void testStartGame() {
		GameState pgs = null;
		GameState gs = controller.shuffle();
		
		int player = 0;
		pgs = controller.startGame(gs, player);
		
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
		pgs = controller.runGame(pgs);
		hands = pgs.getHands();
		for (int i = 0; i < hands.length; i++) {
			if ( hands[i].getCards().size() == 0 ) {
				assertEquals(player, i);
				b = true;
			}
		}
		assertTrue(b);
	}

	@Test
	public void testPlayCards() {
		GameState gameState = controller.shuffle();

		for (int i = 0; i < 10; i++)
			for (int player = 0; player < Big2Constants.nPlayers; player++) {
				Card[] cards = Iterators.toArray(gameState.getHands()[player].getCards().iterator(), Card.class);
				Card card = cards[random.nextInt(cards.length)];

				gameState = controller.playCards(gameState, new GameMove(player, ImmutableSet.asSet(card)));
			}

		for (int player = 0; player < Big2Constants.nPlayers; player++)
			assertEquals(13 - 10, gameState.getHands()[player].getCards().size());
		assertEquals(10 * Big2Constants.nPlayers, gameState.getPlayedMoves().size());

		System.out.println(gameState.toString());
	}
}
