package service;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Game;
import domain.game.Player;

public class GameService implements IGameService {
	private Game game;

	private static final String PLAYER_HAND_EMPTY_EXCEPTION = "Player has no cards to steal";
	private static final String INVALID_PLAYER_INDEX_EXCEPTION = "Invalid player index.";
	private static final String NO_PLAYERS_EXCEPTION = "No players to select from.";
	private static final String OUT_OF_BOUNDS_PLAYER_INDEX_EXCEPTION =
			"playerIndex out of Bounds";
	private static final String PLAYER_DEAD_EXCEPTION = "Player is dead";
	private static final String CARD_INDEX_OUT_OF_BOUNDS_EXCEPTION = "cardIndex out of Bounds";
	private static final String CARD_TYPE_NOT_FOUND_EXCEPTION =
			"Player does not have the card type to steal";
	private static final String NUMBER_OF_TURNS_OUT_OF_BOUNDS_EXCEPTION =
			"Number of turns must be between 1 and 6.";

	public GameService(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public void swapTopAndBottom() {
		if (checkDeckHasOneCardOrLess()) {
			return;
		}
		Card bottomCard = game.getDeck().drawCardFromBottom();
		Card topCard = game.drawCard();
		game.getDeck().insertCard(bottomCard.getCardType(), 1, false);
		game.getDeck().insertCard(topCard.getCardType(), 1, true);
	}

	@Override
	public Card stealRandomCard(int playerToStealFrom) {
		Player player = game.getPlayerAtIndex(playerToStealFrom);
		if (checkPlayerHandEmpty(player)) {
			throw new IllegalArgumentException(PLAYER_HAND_EMPTY_EXCEPTION);
		}

		int randomCardIndex = game.getRandom().nextInt(player.getHandSize());
		Card stealedCard = player.getCardAt(randomCardIndex);
		player.removeCardFromHand(randomCardIndex);
		addCardToHand(stealedCard);
		return stealedCard;
	}

	@Override
	public void stealTypeCard(CardType cardType, int playerToStealFrom) {
		Player player = game.getPlayerAtIndex(playerToStealFrom);
		try {
			int cardIndex = getIndexOfCardFromHand(playerToStealFrom, cardType);
			Card stealedCard = player.getCardAt(cardIndex);
			player.removeCardFromHand(cardIndex);
			game.getPlayerAtIndex(game.getPlayerTurn()).addCardToHand(stealedCard);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(CARD_TYPE_NOT_FOUND_EXCEPTION);
		}
	}

	@Override
	public void startAttackPhase() {
		final int attackedAttackThreshold = 4;
		game.setAttackCounter(game.getPlayerTurn());
		game.setNumberOfAttacks(0);
		while (!isAttackQueueEmpty()) {
			int attack = removeAttackQueue();
			if (attack <= attackedAttackThreshold) {
				playTargetedAttack(attack);
			} else {
				playAttack();
			}
		}
		int[] turnTracker = game.getTurnTracker();
		int attackCounter = game.getAttackCounter();
		if (turnTracker[attackCounter] == 1) {
			turnTracker[attackCounter] = 0;
		}
		if (attackCounter == game.getPlayerTurn()) {
			game.setCurrentPlayerNumberOfTurns(game.getNumberOfTurns() + game.getNumberOfAttacks());
			turnTracker[attackCounter] = 1;
			game.setAttacked(false);
		} else {
			turnTracker[attackCounter] += game.getNumberOfAttacks();
		}
		decrementNumberOfTurns();
		if (checkIfNumberOfTurnsIsZero()) {
			incrementPlayerTurn();
		}
	}

	@Override
	public void playAttack() {
		incrementAttackCounter();
		addAttacks();
		game.setAttacked(true);
	}

	@Override
	public void playTargetedAttack(int attackedPlayerIndex) {
		setAttackCounter(attackedPlayerIndex);
		addAttacks();
		game.setAttacked(true);
	}

	@Override
	public boolean playExplodingKitten(int playerIndex) {
		if (checkUserOutOfBounds(playerIndex)) {
			throw new UnsupportedOperationException(INVALID_PLAYER_INDEX_EXCEPTION);
		}
		if (checkIfPlayerHasCard(playerIndex, CardType.DEFUSE)) {
			return false;
		}
		game.getPlayerAtIndex(playerIndex).setIsDead();
		if (playerIndex == game.getPlayerTurn()) {
			setCurrentPlayerNumberOfTurns(0);
		}
		return true;
	}

	@Override
	public void playImplodingKitten() {
		setCurrentPlayerNumberOfTurns(0);
		game.getPlayerAtIndex(game.getPlayerTurn()).setIsDead();
	}

	@Override
	public void playDefuse(int idxToInsertExplodingKitten, int playerIndex) {
		if (checkUserOutOfBounds(playerIndex)) {
			throw new UnsupportedOperationException(INVALID_PLAYER_INDEX_EXCEPTION);
		}
		Player currentPlayer = game.getPlayerAtIndex(playerIndex);
		try {
			game.getDeck().insertExplodingKittenAtIndex(idxToInsertExplodingKitten);
		} catch (UnsupportedOperationException e) {
			throw e;
		}
		int defuseIdx = currentPlayer.getIndexOfCard(CardType.DEFUSE);
		currentPlayer.removeCardFromHand(defuseIdx);

		if (playerIndex == game.getPlayerTurn()) {
			setCurrentPlayerNumberOfTurns(0);
		}
	}

	@Override
	public Card drawFromBottom() {
		return game.getDeck().drawCardFromBottom();
	}

	@Override
	public void playCatomicBomb() {
		int numberOfBombs = game.getDeck().removeBombs();
		game.getDeck().insertCard(CardType.EXPLODING_KITTEN, numberOfBombs, false);
		decrementNumberOfTurns();
		if (checkIfNumberOfTurnsIsZero()) {
			incrementPlayerTurn();
		}
	}

	@Override
	public void playReverse() {
		int startPointer = 0;
		int endPointer = game.getNumberOfPlayers() - 1;
		game.setReversed(!game.getIsReversed());
		Player[] players = game.getPlayers();
		while (startPointer < endPointer) {
			Player temporaryPlayerOne = players[startPointer];
			Player temporaryPlayerTwo = players[endPointer];
			players[startPointer] = temporaryPlayerTwo;
			players[endPointer] = temporaryPlayerOne;
			startPointer++;
			endPointer--;
		}
		game.setCurrentPlayerTurn(game.getNumberOfPlayers() - game.getPlayerTurn() - 1);
		decrementNumberOfTurns();
		if (checkIfNumberOfTurnsIsZero()) {
			incrementPlayerTurn();
		}
	}

	@Override
	public Player selectRandomPlayer() {
		int randomPlayerIndex = game.getRandom().nextInt(game.getNumberOfPlayers());
		if (hasZeroPlayers()) {
			throw new UnsupportedOperationException(NO_PLAYERS_EXCEPTION);
		}
		return game.getPlayerAtIndex(randomPlayerIndex);
	}

	@Override
	public void playShuffle(int numberOfShuffles) {
		for (int i = 0; i < numberOfShuffles; i++) {
			game.getDeck().shuffleDeck();
		}
	}

	@Override
	public int playSkip(boolean superSkip) {
		if (checkIfNumberOfTurnsOutOfBounds()) {
			throw new UnsupportedOperationException(NUMBER_OF_TURNS_OUT_OF_BOUNDS_EXCEPTION);
		}
		if (superSkip) {
			setCurrentPlayerNumberOfTurns(0);
		} else {
			game.setCurrentPlayerNumberOfTurns(game.getNumberOfTurns() - 1);
		}
		if (checkIfNumberOfTurnsIsZero()) {
			incrementPlayerTurn();
		}
		return game.getNumberOfTurns();
	}

	@Override
	public void playGarbageCollection(CardType cardToDiscard) {
		game.getDeck().insertCard(cardToDiscard, 1, false);
		game.getDeck().shuffleDeck();
	}

	@Override
	public void incrementPlayerTurn() {
		do {
			game.setCurrentPlayerTurn((game.getPlayerTurn() + 1) % game.getNumberOfPlayers());
		} while (checkIfPlayerDead(game.getPlayerTurn()));
	}

	@Override
	public void incrementAttackCounter() {
		do {
			game.setAttackCounter((game.getAttackCounter() + 1) % game.getNumberOfPlayers());
		} while (checkIfPlayerDead(game.getAttackCounter()));
	}

	@Override
	public void setAttackCounter(int playerIndex) {
		game.setAttackCounter(playerIndex);
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
	public int checkNumberOfAlivePlayers() {
		int counter = 0;
		for (int playerIndex = 0; playerIndex < game.getNumberOfPlayers(); playerIndex++) {
			Player player = game.getPlayerAtIndex(playerIndex);
			if (!player.getIsDead()) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public Card drawCard() {
		return game.getDeck().drawCard();
	}

	@Override
	public Card getCardAtIndex(int cardIndex) {
		return game.getDeck().getCardAtIndex(cardIndex);
	}

	@Override
	public void addCardToHand(Card card) {
		game.getPlayerAtIndex(game.getPlayerTurn()).addCardToHand(card);
	}

	@Override
	public boolean checkIfPlayerDead(int playerIndex) {
		return game.getPlayerAtIndex(playerIndex).getIsDead();
	}

	@Override
	public boolean checkIfPlayerHasCard(int playerIndex, CardType cardType) {
		return game.getPlayerAtIndex(playerIndex).hasCard(cardType);
	}

	@Override
	public CardType getCardType(int playerIndex, int cardIndex) {
		return game.getPlayerAtIndex(playerIndex).getCardAt(cardIndex).getCardType();
	}

	@Override
	public int getHandSize(int playerIndex) {
		return game.getPlayerAtIndex(playerIndex).getHandSize();
	}

	@Override
	public void playMark(int playerIndex, int cardIndex) {
		if (checkUserOutOfBounds(playerIndex)) {
			throw new IllegalArgumentException(OUT_OF_BOUNDS_PLAYER_INDEX_EXCEPTION);
		}
		if (checkIfPlayerDead(playerIndex)) {
			throw new IllegalArgumentException(PLAYER_DEAD_EXCEPTION);
		}
		if (checkCardOutOfBoundsIndexed(cardIndex, playerIndex)) {
			throw new IllegalArgumentException(CARD_INDEX_OUT_OF_BOUNDS_EXCEPTION);
		}
		Card card = game.getPlayerAtIndex(playerIndex).getCardAt(cardIndex);
		card.markCard();
	}

	@Override
	public void addAttackQueue(int attack) {
		game.getAttackQueue().add(attack);
	}

	@Override
	public int removeAttackQueue() {
		return game.getAttackQueue().remove(0);
	}

	@Override
	public boolean isAttackQueueEmpty() {
		return game.getAttackQueue().isEmpty();
	}

	@Override
	public void setPlayerNumberOfTurns() {
		int[] turnTracker = game.getTurnTracker();
		game.setCurrentPlayerNumberOfTurns(turnTracker[game.getPlayerTurn()]);
		turnTracker[game.getPlayerTurn()] = 1;
	}

	@Override
	public void setTurnToTargetedIndexIfAttackOccurred() {
		if (game.getAttacked()) {
			game.setAttacked(false);
			game.setCurrentPlayerTurn(game.getAttackCounter());
			if (checkIfPlayerDead(game.getPlayerTurn())) {
				incrementPlayerTurn();
			}
		}
	}

	// Private helper methods
	private boolean checkDeckHasOneCardOrLess() {
		return game.getDeck().getDeckSize() <= 1;
	}

	private boolean checkPlayerHandEmpty(Player player) {
		return player.getHandSize() == 0;
	}

	private boolean hasZeroPlayers() {
		return game.getNumberOfPlayers() == 0;
	}

	private boolean checkIfNumberOfTurnsOutOfBounds() {
		final int minNumberOfTurnsThreshold = 1;
		final int maxNumberOfTurnsThreshold = 6;
		return game.getNumberOfTurns() < minNumberOfTurnsThreshold
				|| game.getNumberOfTurns() > maxNumberOfTurnsThreshold;
	}

	private boolean checkUserOutOfBounds(int userIndex) {
		return userIndex < 0 || userIndex >= game.getNumberOfPlayers();
	}

	private boolean checkCardOutOfBoundsIndexed(int cardIndex, int playerIndex) {
		return cardIndex > getHandSize(playerIndex) - 1 || cardIndex < 0;
	}

	private int getIndexOfCardFromHand(int playerIndex, CardType cardType) {
		return game.getPlayerAtIndex(playerIndex).getIndexOfCard(cardType);
	}

	private void addAttacks() {
		game.setNumberOfAttacks(game.getNumberOfAttacks() + 2);
	}

	private void setCurrentPlayerNumberOfTurns(int numberOfTurns) {
		game.setCurrentPlayerNumberOfTurns(numberOfTurns);
	}
}

