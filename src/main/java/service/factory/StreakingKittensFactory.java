package service.factory;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Deck;
import domain.game.GameType;
import domain.game.Instantiator;
import domain.game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;

/**
 * Concrete Factory for the Streaking Kittens expansion.
 * Extends base game with special streak mechanics and additional card types.
 */
public class StreakingKittensFactory implements GameFactory {
	private final Instantiator instantiator;

	public StreakingKittensFactory() {
		this.instantiator = new Instantiator();
	}

	@Override
	public Deck createDeck(GameType gameType, int numberOfPlayers, int maxDeckSize) {
		// Streaking expansion: Enhanced deck with streak cards
		int expandedDeckSize = maxDeckSize + 5; // +5 for special streak cards
		return new Deck(createCardList(), createRandom(), gameType, numberOfPlayers, expandedDeckSize, instantiator);
	}

	@Override
	public Player createPlayer(int playerID) {
		// Streaking expansion: Players start with standard configuration
		return new Player(playerID, instantiator);
	}

	@Override
	public Card createCard(CardType cardType) {
		// Streaking expansion: Supports all base cards plus streak mechanics
		return new Card(cardType);
	}

	@Override
	public Random createRandom() {
		// Use secure random for unpredictable gameplay
		return new SecureRandom();
	}

	@Override
	public List<Card> createCardList() {
		// Standard list structure
		return new ArrayList<>();
	}
}

