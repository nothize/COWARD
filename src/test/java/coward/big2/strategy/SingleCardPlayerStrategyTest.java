package coward.big2.strategy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coward.big2.GameMove;
import coward.big2.GameState;
import coward.big2.Hand;
import coward.big2.SimplePlayerView;
import coward.big2.card.Card;
import coward.immutable.ImmutableList;
import coward.immutable.ImmutableSet;

public class SingleCardPlayerStrategyTest {
	@Test
	public void testPlay() {
		SingleCardPlayerStrategy singleCardPlayerStrategy = new SingleCardPlayerStrategy();
		int currentPlayer = 0;
		Card d9 = Card.D9;
		Card h10 = Card.HT;
		Card djack = Card.DJ;
		Hand[] hands = new Hand[] {
			new Hand(ImmutableSet.asSet(djack, d9, h10)),
		};

		GameState gameState;
		ImmutableSet<Card> cards;
		Card s10 = Card.ST;
		Card c10 = Card.CT;
		Card ca = Card.CA;

		// Test if last played card < card_n, play card_n
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, ImmutableSet.asSet(c10))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(h10, cards.iterator().next());

		// Test if last played card is nothing, just play the first card.
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, new ImmutableSet<Card>())));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(d9, cards.iterator().next());

		// Test if card_n < last played card for all n, pass
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, ImmutableSet.asSet(ca))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(true, cards.isEmpty());

		// Test if card_n < last played card < card_n+1 , play card_n+1 
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, ImmutableSet.asSet(s10))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(djack, cards.iterator().next());
	}
}
