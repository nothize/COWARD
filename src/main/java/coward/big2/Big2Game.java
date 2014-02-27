package coward.big2;

public class Big2Game {
	public static void main(String[] args) {
		Controller c = new Controller();
		GameState gs = c.shuffle();
		
		int player = c.findFirstPlayer();
		PlayerStrategy ps = new SimplePlayerStrategy();
		while ( !c.endGame() ) {
			c.playCards(gs, player, ps.play(new SimplePlayerView(gs, player)));
		}
	}
}
