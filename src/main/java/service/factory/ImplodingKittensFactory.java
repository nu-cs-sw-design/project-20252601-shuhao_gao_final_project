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
 * Concrete Factory for the Imploding Kittens expansion.
 * Extends base game with IMPLODING_KITTEN cards and special mechanics.
 */
public class ImplodingKittensFactory implements GameFactory {
	private final Instantiator instantiator;

	public ImplodingKittensFactory() {
		this.instantiator = new Instantiator();
	}

	@Override
	public Deck createDeck(GameType gameType, int numberOfPlayers, int maxDeckSize) {
		// Imploding expansion: Larger deck to accommodate IMPLODING_KITTEN
		int expandedDeckSize = maxDeckSize + 1; // +1 for IMPLODING_KITTEN
		return new Deck(createCardList(), createRandom(), gameType, numberOfPlayers, expandedDeckSize, instantiator);
	}

	@Override
	public Player createPlayer(int playerID) {
		// Imploding expansion: Players have same structure
		return new Player(playerID, instantiator);
	}

	@Override
	public Card createCard(CardType cardType) {
		// Imploding expansion: Supports IMPLODING_KITTEN in addition to base cards
		// The actual IMPLODING_KITTEN logic is handled in Deck initialization
		return new Card(cardType);
	}

	@Override
	public Random createRandom() {
		// Use secure random for fair gameplay
		return new SecureRandom();
	}

	@Override
	public List<Card> createCardList() {
		// Standard list for card storage
		return new ArrayList<>();
	}
}

