package service;

public interface ITurnService {
	int getPlayerTurn();
	int getNumberOfTurns();
	void setPlayerNumberOfTurns();
	void incrementPlayerTurn();
	void decrementNumberOfTurns();
	boolean checkIfNumberOfTurnsIsZero();
	void setCurrentPlayerNumberOfTurns(int numberOfTurns);
}

