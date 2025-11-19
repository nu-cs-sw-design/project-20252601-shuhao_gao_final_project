package domain.game.observer;

import domain.game.events.GameEvent;

public interface GameObserver {
	void onGameEvent(GameEvent event);
}

