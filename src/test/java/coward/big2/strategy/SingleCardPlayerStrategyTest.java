package coward.big2.strategy;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import coward.big2.GameMove;
import coward.big2.GameState;
import coward.big2.Hand;
import coward.big2.SimplePlayerView;
import coward.big2.card.Card;
import coward.big2.card.Rank;
import coward.big2.card.Suit;
import coward.immutable.ImmutableList;
import coward.immutable.ImmutableSet;

public class SingleCardPlayerStrategyTest {
	@Test
	public void testPlay() {
		SingleCardPlayerStrategy singleCardPlayerStrategy = new SingleCardPlayerStrategy();
		int currentPlayer = 0;
		Card d9 = new Card(Suit.DIAMOND, Rank.N9___);
		Card h10 = new Card(Suit.HEART__, Rank.N10__);
		Card djack = new Card(Suit.DIAMOND, Rank.JACK_);
		Hand[] hands = new Hand[] {
			new Hand(new ImmutableSet<Card>().add(djack).add(d9).add(h10)),
		};

		GameState gameState;
		ImmutableSet<Card> cards;
		Card s10 = new Card(Suit.SPADE__, Rank.N10__);
		Card c10 = new Card(Suit.CLUB___, Rank.N10__);
		Card ca = new Card(Suit.CLUB___, Rank.ACE__);

		// Test if last played card < card_n, play card_n
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, new ImmutableSet<>(Arrays
						.asList(c10)))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(h10, cards.iterator().next());

		// Test if last played card is nothing, just play the first card.
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, new ImmutableSet<Card>())));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(d9, cards.iterator().next());

		// Test if card_n < last played card for all n, pass
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, new ImmutableSet<>(Arrays
						.asList(ca)))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(true, cards.isEmpty());

		// Test if card_n < last played card < card_n+1 , play card_n+1 
		gameState = new GameState(hands,
				ImmutableList.asList(new GameMove(0, new ImmutableSet<>(Arrays
						.asList(s10)))));
		cards = singleCardPlayerStrategy.play(new SimplePlayerView(gameState, currentPlayer));
		assertEquals(djack, cards.iterator().next());
	}
}
