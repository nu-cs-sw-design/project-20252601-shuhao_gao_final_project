package service.facade;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Player;
import domain.game.events.GameStateChangedEvent;
import domain.game.observer.GameObserver;
import service.ICardService;
import service.IGameService;
import service.ITurnService;
import service.action.GameContext;

import java.util.ArrayList;
import java.util.List;

public class GameFacade {
	private final IGameService gameService;
	private final ICardService cardService;
	private final ITurnService turnService;

	public GameFacade(IGameService gameService, ICardService cardService, ITurnService turnService) {
		this.gameService = gameService;
		this.cardService = cardService;
		this.turnService = turnService;
	}

	public Game getGame() {
		return gameService.getGame();
	}

	public void setGameType(GameType gameType) {
		gameService.getGame().setGameType(gameType);
		publishStateMessage("Game type set to " + gameType);
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		gameService.getGame().setNumberOfPlayers(numberOfPlayers);
		publishStateMessage("Number of players: " + numberOfPlayers);
	}

	public void addObserver(GameObserver observer) {
		gameService.getGame().addObserver(observer);
	}

	public void removeObserver(GameObserver observer) {
		gameService.getGame().removeObserver(observer);
	}

	public void prepareTurn() {
		if (turnService.checkIfNumberOfTurnsIsZero()) {
			gameService.setTurnToTargetedIndexIfAttackOccurred();
			turnService.setPlayerNumberOfTurns();
		}
	}

	public GameContext createContext(int playerIndex, int cardIndex, CardType cardType) {
		return new GameContext(playerIndex, cardIndex, cardType, gameService, cardService, turnService);
	}

	public void executeCardAction(CardType cardType, GameContext context) {
		gameService.executeCardAction(cardType, context);
	}

	public boolean checkIfGameOver() {
		return gameService.checkNumberOfAlivePlayers() == 1;
	}

	public void publishStateMessage(String message) {
		gameService.getGame().notifyObservers(new GameStateChangedEvent(message));
	}

	public List<Card> getPlayerHandSnapshot(int playerIndex) {
		Player player = gameService.getGame().getPlayerAtIndex(playerIndex);
		return new ArrayList<Card>(player.getHandSnapshot());
	}

	public int getPlayerTurn() {
		return turnService.getPlayerTurn();
	}

	public int getNumberOfTurns() {
		return turnService.getNumberOfTurns();
	}

	public Card drawCard() {
		return gameService.drawCard();
	}

	public void incrementPlayerTurn() {
		turnService.incrementPlayerTurn();
	}

	public void setCurrentPlayerNumberOfTurns(int turns) {
		turnService.setCurrentPlayerNumberOfTurns(turns);
	}

	public void playAttack() {
		gameService.playAttack();
		gameService.startAttackPhase();
	}

	public void addAttacksToTargetPlayer() {
		// Process attack queue and add turns to next player
		if (!gameService.isAttackQueueEmpty()) {
			gameService.startAttackPhase();
		}
	}
}

