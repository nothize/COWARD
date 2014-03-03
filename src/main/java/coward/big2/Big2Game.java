package coward.big2;

import java.util.ArrayList;
import java.util.List;

import coward.big2.strategy.PlayerStrategy;
import coward.big2.strategy.SimplePlayerStrategy;
import coward.big2.ui.SilentUserInterface;
import coward.big2.ui.UserInterface;

public class Big2Game {

	private UserInterface userInterface = new SilentUserInterface();

	public static void main(String[] args) {
		new Big2Game().run();
	}

	public void run() {
		List<PlayerStrategy> playerStrategies = new ArrayList<>();

		for (int player = 0; player < Big2Constants.nPlayers; player++)
			playerStrategies.add(new SimplePlayerStrategy());

		Controller c = new Controller(playerStrategies);
		GameState pgs = null;
		do {
			pgs = c.runGame(pgs);
		} while ( userInterface.playAgain() );
	}

}
