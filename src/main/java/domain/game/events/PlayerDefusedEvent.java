package domain.game.events;

import domain.game.CardType;

public class PlayerDefusedEvent extends GameEvent {

	private final int playerIndex;
	private final CardType defuseCardType;

	public PlayerDefusedEvent(int playerIndex, CardType defuseCardType) {
		super(EventPriority.HIGH);
		this.playerIndex = playerIndex;
		this.defuseCardType = defuseCardType;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public CardType getDefuseCardType() {
		return defuseCardType;
	}
}
