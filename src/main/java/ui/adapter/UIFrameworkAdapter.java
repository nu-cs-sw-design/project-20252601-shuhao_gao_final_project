package ui.adapter;

import domain.game.events.GameEvent;
import domain.game.observer.GameObserver;

public interface UIFrameworkAdapter {
	void displayMessage(String message);

	void displayPlayerTurn(int playerIndex, int numberOfTurns);

	void displayGameOver();

	void handleGameEvent(GameEvent event);
}
