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
 * Concrete Factory for the base Exploding Kittens game.
 * Creates the standard product family without expansion cards.
 */
public class ExplodingKittensFactory implements GameFactory {
	private final Instantiator instantiator;

	public ExplodingKittensFactory() {
		this.instantiator = new Instantiator();
	}

	@Override
	public Deck createDeck(GameType gameType, int numberOfPlayers, int maxDeckSize) {
		// Base game: Standard deck configuration
		return new Deck(createCardList(), createRandom(), gameType, numberOfPlayers, maxDeckSize, instantiator);
	}

	@Override
	public Player createPlayer(int playerID) {
		// Base game: Standard player
		return new Player(playerID, instantiator);
	}

	@Override
	public Card createCard(CardType cardType) {
		// Base game: All standard card types supported
		return new Card(cardType);
	}

	@Override
	public Random createRandom() {
		// Use secure random for better randomness
		return new SecureRandom();
	}

	@Override
	public List<Card> createCardList() {
		// Base game: Standard ArrayList
		return new ArrayList<>();
	}
}

