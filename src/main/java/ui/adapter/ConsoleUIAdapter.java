package ui.adapter;

import domain.game.events.GameEvent;
import ui.GameView;

public class ConsoleUIAdapter implements UIFrameworkAdapter {

	private final GameView gameView;

	public ConsoleUIAdapter(GameView gameView) {
		this.gameView = gameView;
	}

	@Override
	public void displayMessage(String message) {
		gameView.displayMessage(message);
	}

	@Override
	public void displayPlayerTurn(int playerIndex, int numberOfTurns) {
		gameView.displayPlayerHand(playerIndex, null);
	}

	@Override
	public void displayGameOver() {
		gameView.displayGameOver();
	}

	@Override
	public void handleGameEvent(GameEvent event) {
	}
}
