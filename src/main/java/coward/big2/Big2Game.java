package coward.big2;

public class Big2Game {
	public static void main(String[] args) {
		Controller c = new Controller();
		GameState pgs = null;
		do {
			GameState gs = c.shuffle();
			
			int player = c.findFirstPlayer(gs, pgs);
			PlayerStrategy ps = new SimplePlayerStrategy();
			while ( !c.endGame(gs) ) {
				c.playCards(gs, player, ps.play(new SimplePlayerView(gs, player)));
			}
			pgs = gs;
		} while ( c.playAgain() );
	}
}
