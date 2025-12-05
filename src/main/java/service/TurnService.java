package service;

import domain.game.Game;

public class TurnService implements ITurnService {
	private Game game;

	public TurnService(Game game) {
		this.game = game;
	}

	@Override
	public int getPlayerTurn() {
		return game.getPlayerTurn();
	}

	@Override
	public int getNumberOfTurns() {
		return game.getNumberOfTurns();
	}

	@Override
	public void setPlayerNumberOfTurns() {
		// Only restore from turnTracker if numberOfTurns is 0 (new turn starting)
		if (game.getNumberOfTurns() == 0) {
			int[] turnTracker = game.getTurnTracker();
			int currentPlayer = game.getPlayerTurn();
			game.setCurrentPlayerNumberOfTurns(turnTracker[currentPlayer]);
			// Reset turnTracker to 1 for next time
			turnTracker[currentPlayer] = 1;
		}
		// Otherwise, keep the current numberOfTurns value (turn in progress)
	}

	@Override
	public void incrementPlayerTurn() {
		do {
			game.setCurrentPlayerTurn((game.getPlayerTurn() + 1) % game.getNumberOfPlayers());
		} while (game.getPlayerAtIndex(game.getPlayerTurn()).getIsDead());
		// Reset numberOfTurns to 0 so prepareTurn() will restore from turnTracker
		game.setCurrentPlayerNumberOfTurns(0);
	}

	@Override
	public void decrementNumberOfTurns() {
		game.setCurrentPlayerNumberOfTurns(game.getNumberOfTurns() - 1);
	}

	@Override
	public boolean checkIfNumberOfTurnsIsZero() {
		return game.getNumberOfTurns() == 0;
	}

	@Override
	public void setCurrentPlayerNumberOfTurns(int numberOfTurns) {
		game.setCurrentPlayerNumberOfTurns(numberOfTurns);
	}
}

