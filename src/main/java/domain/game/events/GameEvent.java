package domain.game.events;

import java.time.Instant;

public abstract class GameEvent {
	public enum EventPriority {
		LOW(0),
		NORMAL(1),
		HIGH(2);

		private final int level;

		EventPriority(int level) {
			this.level = level;
		}

		public int getLevel() {
			return level;
		}
	}

	private final Instant timestamp;
	private final EventPriority priority;

	protected GameEvent() {
		this.timestamp = Instant.now();
		this.priority = EventPriority.NORMAL;
	}

	protected GameEvent(EventPriority priority) {
		this.timestamp = Instant.now();
		this.priority = priority;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public EventPriority getPriority() {
		return priority;
	}
}

