package domain.game;

import java.util.List;
import java.util.Random;

public class Game {
	private int numberOfPlayers;
	private GameType gameType;
	private Deck deck;
	private Player[] players;
	private Random rand;

	private int currentPlayerTurn;
	private int currentPlayerNumberOfTurns;
	private boolean isReversed;
	private List<Integer> attackQueue;
	private int attackCounter;
	private int numberOfAttacks;
	private int[] turnTracker;
	private boolean attacked;

	public Game(int numberOfPlayers, GameType gameType,
				Deck deck, Player[] players, Random rand,
				List<Integer> attackQueue,
				int[] turnTracker) {
		this.numberOfPlayers = numberOfPlayers;
		this.gameType = gameType;
		this.deck = deck;
		this.players = players;
		this.rand = rand;
		this.currentPlayerTurn = 0;
		this.currentPlayerNumberOfTurns = 0;
		this.attackQueue = attackQueue;
		isReversed = false;
		this.turnTracker = turnTracker;
		this.attackCounter = 0;
		this.numberOfAttacks = 0;
		this.attacked = false;
	}

	// Getters
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public GameType getGameType() {
		return gameType;
	}

	public Deck getDeck() {
		return deck;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Random getRandom() {
		return rand;
	}

	public int getPlayerTurn() {
		return currentPlayerTurn;
	}

	public int getNumberOfTurns() {
		return currentPlayerNumberOfTurns;
	}

	public boolean getIsReversed() {
		return isReversed;
	}

	public List<Integer> getAttackQueue() {
		return attackQueue;
	}

	public int getAttackCounter() {
		return attackCounter;
	}

	public int getNumberOfAttacks() {
		return numberOfAttacks;
	}

	public int[] getTurnTracker() {
		return turnTracker;
	}

	public boolean getAttacked() {
		return attacked;
	}

	public int getTurnCountOfPlayer(int playerIndex) {
		return turnTracker[playerIndex];
	}

	public Player getPlayerAtIndex(int playerIndex) {
		return players[playerIndex];
	}

	public int getDeckSize() {
		return deck.getDeckSize();
	}

	public Card drawCard() {
		return deck.drawCard();
	}

	public Card getCardAtIndex(int cardIndex) {
		return deck.getCardAtIndex(cardIndex);
	}

	public CardType getDeckCardType(int deckIndex) {
		return getCardAtIndex(deckIndex).getCardType();
	}

	// Setters
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		if (deck != null) {
			deck.setNumberOfPlayers(numberOfPlayers);
		}
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
		if (deck != null) {
			deck.chooseGameType(gameType);
		}
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public void setCurrentPlayerTurn(int turn) {
		this.currentPlayerTurn = turn;
	}

	public void setCurrentPlayerNumberOfTurns(int numberOfTurns) {
		this.currentPlayerNumberOfTurns = numberOfTurns;
	}

	public void setReversed(boolean isReversed) {
		this.isReversed = isReversed;
	}

	public void setAttackCounter(int attackCounter) {
		this.attackCounter = attackCounter;
	}

	public void setNumberOfAttacks(int numberOfAttacks) {
		this.numberOfAttacks = numberOfAttacks;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	// For testing
	GameType getGameTypeForTesting() {
		return gameType;
	}
}
