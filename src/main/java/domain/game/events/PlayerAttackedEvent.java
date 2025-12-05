package domain.game.events;

public class PlayerAttackedEvent extends GameEvent {

	private final int attackerPlayerIndex;
	private final int targetPlayerIndex;

	public PlayerAttackedEvent(int attackerPlayerIndex, int targetPlayerIndex) {
		super(EventPriority.HIGH);
		this.attackerPlayerIndex = attackerPlayerIndex;
		this.targetPlayerIndex = targetPlayerIndex;
	}

	public int getAttackerPlayerIndex() {
		return attackerPlayerIndex;
	}

	public int getTargetPlayerIndex() {
		return targetPlayerIndex;
	}
}
