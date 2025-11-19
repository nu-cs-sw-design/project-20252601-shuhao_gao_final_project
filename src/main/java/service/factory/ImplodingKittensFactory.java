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

public class ImplodingKittensFactory implements GameFactory {
	private Instantiator instantiator;

	public ImplodingKittensFactory() {
		this.instantiator = new Instantiator();
	}

	@Override
	public Deck createDeck(GameType gameType, int numberOfPlayers, int maxDeckSize) {
		return new Deck(new ArrayList<>(), createRandom(), gameType, numberOfPlayers, maxDeckSize, instantiator);
	}

	@Override
	public Player createPlayer(int playerID) {
		return new Player(playerID, instantiator);
	}

	@Override
	public Card createCard(CardType cardType) {
		return instantiator.createCard(cardType);
	}

	@Override
	public Random createRandom() {
		return instantiator.createRandom();
	}

	@Override
	public List<Card> createCardList() {
		return instantiator.createCardList();
	}
}

