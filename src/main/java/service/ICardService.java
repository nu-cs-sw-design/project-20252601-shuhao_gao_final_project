package service;

import domain.game.Card;
import domain.game.CardType;

public interface ICardService {
	CardType getCardType(int playerIndex, int cardIndex);
	int getHandSize(int playerIndex);
	boolean checkIfPlayerHasCard(int playerIndex, CardType cardType);
	int getIndexOfCardFromHand(int playerIndex, CardType cardType);
	void removeCardFromHand(int playerIndex, CardType cardType);
	void addCardToHand(Card card);
}

