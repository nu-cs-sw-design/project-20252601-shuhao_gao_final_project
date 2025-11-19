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
		int[] turnTracker = game.getTurnTracker();
		game.setCurrentPlayerNumberOfTurns(turnTracker[game.getPlayerTurn()]);
		turnTracker[game.getPlayerTurn()] = 1;
	}

	@Override
	public void incrementPlayerTurn() {
		do {
			game.setCurrentPlayerTurn((game.getPlayerTurn() + 1) % game.getNumberOfPlayers());
		} while (game.getPlayerAtIndex(game.getPlayerTurn()).getIsDead());
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

