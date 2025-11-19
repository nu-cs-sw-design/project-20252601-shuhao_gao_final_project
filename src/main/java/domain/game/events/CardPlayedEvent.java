package domain.game.events;

import domain.game.CardType;

public class CardPlayedEvent extends GameEvent {
	private final int playerIndex;
	private final CardType cardType;

	public CardPlayedEvent(int playerIndex, CardType cardType) {
		this.playerIndex = playerIndex;
		this.cardType = cardType;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public CardType getCardType() {
		return cardType;
	}
}

