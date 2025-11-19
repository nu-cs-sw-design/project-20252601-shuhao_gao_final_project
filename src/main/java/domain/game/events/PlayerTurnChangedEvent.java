package domain.game.events;

import domain.game.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerTurnChangedEvent extends GameEvent {
	private final int playerIndex;
	private final int numberOfTurns;
	private final List<Card> handSnapshot;

	public PlayerTurnChangedEvent(int playerIndex, int numberOfTurns, List<Card> handSnapshot) {
		this.playerIndex = playerIndex;
		this.numberOfTurns = numberOfTurns;
		this.handSnapshot = Collections.unmodifiableList(new ArrayList<>(handSnapshot));
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public List<Card> getHandSnapshot() {
		return handSnapshot;
	}
}

