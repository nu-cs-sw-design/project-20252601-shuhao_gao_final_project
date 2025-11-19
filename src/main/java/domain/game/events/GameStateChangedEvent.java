package domain.game.events;

public class GameStateChangedEvent extends GameEvent {
	private final String message;

	public GameStateChangedEvent(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}

