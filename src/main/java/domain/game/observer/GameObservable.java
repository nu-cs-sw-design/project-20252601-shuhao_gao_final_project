package domain.game.observer;

import domain.game.events.GameEvent;

public interface GameObservable {
	void addObserver(GameObserver observer);
	void removeObserver(GameObserver observer);
	void notifyObservers(GameEvent event);
}

