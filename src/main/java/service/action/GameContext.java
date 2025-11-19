package service.action;

import domain.game.CardType;
import service.ICardService;
import service.IGameService;
import service.ITurnService;

public class GameContext {
	private int playerIndex;
	private int cardIndex;
	private CardType cardType;
	private IGameService gameService;
	private ICardService cardService;
	private ITurnService turnService;

	public GameContext(int playerIndex, int cardIndex, CardType cardType,
					   IGameService gameService, ICardService cardService,
					   ITurnService turnService) {
		this.playerIndex = playerIndex;
		this.cardIndex = cardIndex;
		this.cardType = cardType;
		this.gameService = gameService;
		this.cardService = cardService;
		this.turnService = turnService;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public int getCardIndex() {
		return cardIndex;
	}

	public CardType getCardType() {
		return cardType;
	}

	public IGameService getGameService() {
		return gameService;
	}

	public ICardService getCardService() {
		return cardService;
	}

	public ITurnService getTurnService() {
		return turnService;
	}
}

