package service.factory;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Deck;
import domain.game.GameType;
import domain.game.Player;

import java.util.List;
import java.util.Random;

public interface GameFactory {
	Deck createDeck(GameType gameType, int numberOfPlayers, int maxDeckSize);
	Player createPlayer(int playerID);
	Card createCard(CardType cardType);
	Random createRandom();
	List<Card> createCardList();
}

