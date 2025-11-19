package ui;

import domain.game.GameType;
import service.facade.GameFacade;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameController {
	private final GameFacade facade;
	private final GameView view;

	public GameController(GameFacade facade, GameView view) {
		this.facade = facade;
		this.view = view;
		facade.addObserver(view);
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
					facade.setGameType(GameType.EXPLODING_KITTENS);
					final String gameModeExploding = view.getMessages().getString("gameModeExploding");
					view.displayMessage(gameModeExploding);
					return;
				case "2":
					facade.setGameType(GameType.IMPLODING_KITTENS);
					final String gameModeImploding = view.getMessages().getString("gameModeImploding");
					view.displayMessage(gameModeImploding);
					return;
				case "3":
					facade.setGameType(GameType.STREAKING_KITTENS);
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
					facade.setNumberOfPlayers(twoPlayers);
					view.displayMessage(numOfPlayersTwo);
					return;
				case "3":
					facade.setNumberOfPlayers(threePlayers);
					view.displayMessage(numOfPlayersThree);
					return;
				case "4":
					facade.setNumberOfPlayers(fourPlayers);
					view.displayMessage(numOfPlayersFour);
					return;
				default:
					view.displayMessage(invalidPlayersNum);
			}
		}
	}

	public void startTurn() {
		facade.prepareTurn();
	}

	public void endGame() {
		view.displayGameOver();
	}

	public boolean checkIfGameOver() {
		return facade.checkIfGameOver();
	}
}

