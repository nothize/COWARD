package coward.big2;


public class SimplePlayerView implements PlayerView {
	private int currentPlayer;
	private GameState gameState;

	public SimplePlayerView(GameState gameState, int currentPlayer) {
		this.gameState = gameState;
		this.currentPlayer = currentPlayer;
	}
	
	@Override
	public int currentPlayerNum() {
		return currentPlayer;
	}

	@Override
	public java.util.List<coward.immutable.ImmutableSet<Card>> getPlayedCards() {
		return gameState.getPlayedCards();
	}

	@Override
	public Hand getHand() {
		return gameState.getHands()[currentPlayer];
	}

	@Override
	public boolean[] isLastCard() {
		boolean[] lastCard = new boolean[Big2Constants.nPlayers];
		Hand[] hands = gameState.getHands();
		int i = 0;
		for (Hand hand : hands) {
			lastCard[i++] = hand.getCards().size() == 1;
		}
		return lastCard;
	}

}
