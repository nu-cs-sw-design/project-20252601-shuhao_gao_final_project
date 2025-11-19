package service;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Game;

public class CardService implements ICardService {
	private Game game;

	public CardService(Game game) {
		this.game = game;
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
	public boolean checkIfPlayerHasCard(int playerIndex, CardType cardType) {
		return game.getPlayerAtIndex(playerIndex).hasCard(cardType);
	}

	@Override
	public int getIndexOfCardFromHand(int playerIndex, CardType cardType) {
		return game.getPlayerAtIndex(playerIndex).getIndexOfCard(cardType);
	}

	@Override
	public void removeCardFromHand(int playerIndex, CardType cardType) {
		int cardIndex = getIndexOfCardFromHand(playerIndex, cardType);
		game.getPlayerAtIndex(playerIndex).removeCardFromHand(cardIndex);
	}

	@Override
	public void addCardToHand(Card card) {
		game.getPlayerAtIndex(game.getPlayerTurn()).addCardToHand(card);
	}
}

