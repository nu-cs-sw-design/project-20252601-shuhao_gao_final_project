package ui;

import domain.game.Card;
import domain.game.CardType;
import domain.game.events.CardPlayedEvent;
import domain.game.events.GameEvent;
import domain.game.events.GameStateChangedEvent;
import domain.game.events.PlayerTurnChangedEvent;
import domain.game.observer.GameObserver;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class GameView implements GameObserver {
	private ResourceBundle messages;
	private Scanner scanner;

	public GameView() {
		this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public void displayLanguageOptions() {
		final String language = "1. English\n2. 한국어\n";
		final String askLanguage = "Enter the number to choose the language:";
		System.out.println(language);
		System.out.println(askLanguage);
	}

	public void displayGameModeOptions() {
		final String gameModePrompt = messages.getString("gameModePrompt");
		final String gameModeExplodingOption = messages.getString("gameModeExplodingOption");
		final String gameModeImplodingOption = messages.getString("gameModeImplodingOption");
		final String gameModeStreakingOption = messages.getString("gameModeStreakingOption");

		System.out.println(gameModePrompt);
		System.out.println(gameModeExplodingOption);
		System.out.println(gameModeImplodingOption);
		System.out.println(gameModeStreakingOption);
	}

	public void displayPlayerTurn(int currentPlayer, int numberOfTurns, List<Card> hand) {
		final String dividerLine = messages.getString("dividerLine");
		final String currentPlayerTurnMessage = MessageFormat.format(
				messages.getString("currentPlayerTurn"), currentPlayer);
		final String playerTurnsMessage = MessageFormat.format(
				messages.getString("playerTurnsMessage"), numberOfTurns);

		System.out.println(dividerLine);
		System.out.println(currentPlayerTurnMessage);
		System.out.println(playerTurnsMessage);
		displayPlayerHand(currentPlayer, hand);
	}

	public void displayPlayerHand(int playerIndex, List<Card> hand) {
		final StringBuilder handMessage = new StringBuilder(messages.getString("playerHand"));
		for (Card card : hand) {
			handMessage.append(" ").append(getLocalizedCardType(card.getCardType()));
		}
		System.out.println(handMessage);
	}

	public void displayCardDrawn(CardType cardType) {
		final String cardDrawnMessage = MessageFormat.format(
				messages.getString("cardDrawnMessage"),
				getLocalizedCardType(cardType));
		System.out.println(cardDrawnMessage);
	}

	public void displayGameOver() {
		final String gameOverMessage = messages.getString("gameOverMessage");
		System.out.println(gameOverMessage);
	}

	public void displayMessage(String message) {
		System.out.println(message);
	}

	public String promptInput(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public int promptInteger(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(messages.getString("invalidNumber"));
			}
		}
	}

	public String getLocalizedCardType(CardType cardType) {
		String cardTypeKey = "card." + cardType.name();
		return messages.getString(cardTypeKey);
	}

	public ResourceBundle getMessages() {
		return messages;
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (event instanceof PlayerTurnChangedEvent) {
			PlayerTurnChangedEvent turnEvent = (PlayerTurnChangedEvent) event;
			displayPlayerTurn(turnEvent.getPlayerIndex(),
					turnEvent.getNumberOfTurns(),
					turnEvent.getHandSnapshot());
		} else if (event instanceof CardPlayedEvent) {
			CardPlayedEvent cardEvent = (CardPlayedEvent) event;
			final String cardPlayed = MessageFormat.format(
					"Player {0} played {1}",
					cardEvent.getPlayerIndex(),
					getLocalizedCardType(cardEvent.getCardType()));
			displayMessage(cardPlayed);
		} else if (event instanceof GameStateChangedEvent) {
			displayMessage(((GameStateChangedEvent) event).getMessage());
		}
	}
}

