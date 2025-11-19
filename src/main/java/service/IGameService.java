package service;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Game;
import domain.game.Player;

public interface IGameService {
	void swapTopAndBottom();
	Card stealRandomCard(int playerToStealFrom);
	void stealTypeCard(CardType cardType, int playerToStealFrom);
	void startAttackPhase();
	void playAttack();
	void playTargetedAttack(int attackedPlayerIndex);
	boolean playExplodingKitten(int playerIndex);
	void playImplodingKitten();
	void playDefuse(int idxToInsertExplodingKitten, int playerIndex);
	Card drawFromBottom();
	void playCatomicBomb();
	void playReverse();
	Player selectRandomPlayer();
	void playShuffle(int numberOfShuffles);
	int playSkip(boolean superSkip);
	void playGarbageCollection(CardType cardToDiscard);
	void incrementPlayerTurn();
	void incrementAttackCounter();
	void setAttackCounter(int playerIndex);
	void decrementNumberOfTurns();
	boolean checkIfNumberOfTurnsIsZero();
	int checkNumberOfAlivePlayers();
	Card drawCard();
	Card getCardAtIndex(int cardIndex);
	void addCardToHand(Card card);
	boolean checkIfPlayerDead(int playerIndex);
	boolean checkIfPlayerHasCard(int playerIndex, CardType cardType);
	CardType getCardType(int playerIndex, int cardIndex);
	int getHandSize(int playerIndex);
	void playMark(int playerIndex, int cardIndex);
	void addAttackQueue(int attack);
	int removeAttackQueue();
	boolean isAttackQueueEmpty();
	void setPlayerNumberOfTurns();
	void setTurnToTargetedIndexIfAttackOccurred();
	Game getGame();
}

