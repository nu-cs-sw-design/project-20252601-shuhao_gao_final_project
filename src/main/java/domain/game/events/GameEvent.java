package domain.game.events;

import java.time.Instant;

public abstract class GameEvent {
	private final Instant timestamp;

	protected GameEvent() {
		this.timestamp = Instant.now();
	}

	public Instant getTimestamp() {
		return timestamp;
	}
}

