package domain.game.events;

public class PlayerEleminatedEvent extends GameEvent {

	private final int playerIndex;
	private final String eliminationReason;

	public PlayerEleminatedEvent(int playerIndex, String eliminationReason) {
		super(EventPriority.HIGH);
		this.playerIndex = playerIndex;
		this.eliminationReason = eliminationReason;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public String getEliminationReason() {
		return eliminationReason;
	}
}
