package coward.big2;

public class Big2Game {
	public static void main(String[] args) {
		Big2Game game = new Big2Game();
		
		game.run();
	}

	public void run() {
		Controller c = new Controller();
		GameState pgs = null;
		do {
			pgs = runGame(c, pgs);
		} while ( c.playAgain() );
	}

	public GameState runGame(Controller c, GameState pgs) {
		GameState gs = c.shuffle();
		return startGame(c, gs, c.findFirstPlayer(gs, pgs), new SimplePlayerStrategy());
	}

	public GameState startGame(Controller c, GameState gs, int player,
			PlayerStrategy ps) {
		do {
			gs = c.playCards(gs, player, ps.play(new SimplePlayerView(gs, player)));
			player = gs.getNextPlayer(player);
		} while ( !c.endGame(gs) );
		player = gs.getPreviousPlayer(player);
		return gs;
	}
}
