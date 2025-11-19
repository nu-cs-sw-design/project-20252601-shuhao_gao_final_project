package ui;

import domain.game.GameType;
import service.ICardService;
import service.IGameService;
import service.ITurnService;

import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class GameController {
	private IGameService gameService;
	private ICardService cardService;
	private ITurnService turnService;
	private GameView view;

	public GameController(IGameService gameService, ICardService cardService,
						  ITurnService turnService, GameView view) {
		this.gameService = gameService;
		this.cardService = cardService;
		this.turnService = turnService;
		this.view = view;
	}

	public void chooseLanguage() {
		view.displayLanguageOptions();
		final String invalidChoice = "Invalid choice. Please enter 1 or 2.";

		while (true) {
			String userInput = view.promptInput("");
			switch (userInput) {
				case "1":
					ResourceBundle messages = ResourceBundle.getBundle("message", new Locale("en"));
					view.setMessages(messages);
					final String languageSetEnglish = messages.getString("setLanguage");
					view.displayMessage(languageSetEnglish);
					return;
				case "2":
					messages = ResourceBundle.getBundle("message", new Locale("ko"));
					view.setMessages(messages);
					final String languageSetKorean = messages.getString("setLanguage");
					view.displayMessage(languageSetKorean);
					return;
				default:
					view.displayMessage(invalidChoice);
			}
		}
	}

	public void chooseGame() {
		view.displayGameModeOptions();
		final String gameModeChoicePrompt = view.getMessages().getString("gameModeChoicePrompt");
		final String gameModeInvalid = view.getMessages().getString("gameModeInvalid");

		while (true) {
			String userInput = view.promptInput(gameModeChoicePrompt);
			switch (userInput) {
				case "1":
					gameService.getGame().setGameType(GameType.EXPLODING_KITTENS);
					final String gameModeExploding = view.getMessages().getString("gameModeExploding");
					view.displayMessage(gameModeExploding);
					return;
				case "2":
					gameService.getGame().setGameType(GameType.IMPLODING_KITTENS);
					final String gameModeImploding = view.getMessages().getString("gameModeImploding");
					view.displayMessage(gameModeImploding);
					return;
				case "3":
					gameService.getGame().setGameType(GameType.STREAKING_KITTENS);
					final String gameModeStreaking = view.getMessages().getString("gameModeStreaking");
					view.displayMessage(gameModeStreaking);
					return;
				default:
					view.displayMessage(gameModeInvalid);
			}
		}
	}

	public void chooseNumberOfPlayers() {
		final String numOfPlayersPrompt = view.getMessages().getString("numOfPlayersPrompt");
		final String numOfPlayersTwo = view.getMessages().getString("numOfPlayersTwo");
		final String numOfPlayersThree = view.getMessages().getString("numOfPlayersThree");
		final String numOfPlayersFour = view.getMessages().getString("numOfPlayersFour");
		final String invalidPlayersNum = view.getMessages().getString("invalidPlayersNum");

		view.displayMessage(numOfPlayersPrompt);

		while (true) {
			String userInput = view.promptInput("");
			final int twoPlayers = 2;
			final int threePlayers = 3;
			final int fourPlayers = 4;
			switch (userInput) {
				case "2":
					gameService.getGame().setNumberOfPlayers(twoPlayers);
					view.displayMessage(numOfPlayersTwo);
					return;
				case "3":
					gameService.getGame().setNumberOfPlayers(threePlayers);
					view.displayMessage(numOfPlayersThree);
					return;
				case "4":
					gameService.getGame().setNumberOfPlayers(fourPlayers);
					view.displayMessage(numOfPlayersFour);
					return;
				default:
					view.displayMessage(invalidPlayersNum);
			}
		}
	}

	public void startTurn() {
		// This is a simplified version - the full implementation would need
		// to be migrated from GameUI.startTurn() method
		// For now, this is a placeholder that shows the structure
		if (turnService.checkIfNumberOfTurnsIsZero()) {
			gameService.setTurnToTargetedIndexIfAttackOccurred();
			turnService.setPlayerNumberOfTurns();
		}

		int currentPlayer = turnService.getPlayerTurn();
		int numberOfTurns = turnService.getNumberOfTurns();
		// Get player hand - this would need to be implemented
		// For now, just display the turn
		view.displayMessage(MessageFormat.format(
				view.getMessages().getString("currentPlayerTurn"), currentPlayer));
		view.displayMessage(MessageFormat.format(
				view.getMessages().getString("playerTurnsMessage"), numberOfTurns));
	}

	public void endGame() {
		view.displayGameOver();
	}

	public boolean checkIfGameOver() {
		return gameService.checkNumberOfAlivePlayers() == 1;
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

	public GameView getView() {
		return view;
	}
}

