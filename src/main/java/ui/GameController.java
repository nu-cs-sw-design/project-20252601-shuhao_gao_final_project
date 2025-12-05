package ui;

import domain.game.Card;
import domain.game.CardType;
import domain.game.GameType;
import domain.game.Player;
import domain.game.config.GameConfiguration;
import service.action.GameContext;
import service.facade.GameFacade;

import java.util.List;
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
		int currentPlayer = facade.getPlayerTurn();
		
		facade.prepareTurn();

		int numberOfTurns = facade.getNumberOfTurns();

		// If player has no turns, they must draw a card and end their turn
		if (numberOfTurns == 0) {
			handleDrawCard(currentPlayer);
			facade.incrementPlayerTurn();
			return;
		}

		final String invalidCardSelection =
				view.getMessages().getString("invalidCardSelection");
		
		// Process all turns for this player
		while (facade.getNumberOfTurns() > 0) {
			List<Card> playerHand =
					facade.getPlayerHandSnapshot(currentPlayer);
			numberOfTurns = facade.getNumberOfTurns();

			// If player has no cards, they must draw and end turn
			if (playerHand.isEmpty()) {
				view.displayMessage("\nPlayer " + currentPlayer + " has no cards. Must draw a card to end turn.");
				handleDrawCard(currentPlayer);
				facade.setCurrentPlayerNumberOfTurns(numberOfTurns - 1);
				continue; // Continue to next turn if they have more
			}

			view.displayPlayerTurn(currentPlayer, numberOfTurns, playerHand);

			String prompt = "Play a card (1-" + playerHand.size() + ") or press ENTER to end turn:";
			String userInput = view.promptInput(prompt);

			// If player presses ENTER, end this turn (draw card and decrement turns)
			if (userInput == null || userInput.trim().isEmpty()) {
				handleDrawCard(currentPlayer);
				facade.setCurrentPlayerNumberOfTurns(numberOfTurns - 1);
				// Loop continues if still have turns
				continue;
			}

			try {
				int cardIndex = Integer.parseInt(userInput) - 1;
				if (cardIndex < 0 || cardIndex >= playerHand.size()) {
					view.displayMessage(invalidCardSelection);
					continue;
				}

				CardType cardType = facade.getGame()
						.getPlayerAtIndex(currentPlayer)
						.getCardAt(cardIndex).getCardType();
				
				// Handle cards with special behavior
				if (cardType == CardType.ATTACK) {
					// ATTACK: Remove card, next player gets +1 turn (2 total), current turn ends
					facade.getGame().getPlayerAtIndex(currentPlayer).removeCardFromHand(cardIndex);
					view.displayMessage("Player " + currentPlayer + " played ATTACK");
					
					// Get turnTracker and next player
					int[] turnTracker = facade.getGame().getTurnTracker();
					int nextPlayer = (currentPlayer + 1) % facade.getGame().getNumberOfPlayers();
					
					// Add 1 turn to next player (1 base + 1 attack = 2 total turns)
					turnTracker[nextPlayer] += 1;
					
					// End ALL remaining turns for current player
					facade.setCurrentPlayerNumberOfTurns(0);
					break; // Exit while loop
					
				} else if (cardType == CardType.SKIP) {
					// SKIP: Ends one turn without drawing
					facade.getGame().getPlayerAtIndex(currentPlayer).removeCardFromHand(cardIndex);
					view.displayMessage("Player " + currentPlayer + " played SKIP");
					
					// Decrement turns by 1
					facade.setCurrentPlayerNumberOfTurns(numberOfTurns - 1);
					// Loop continues if still have turns left
					
				} else if (cardType == CardType.SUPER_SKIP) {
					// SUPER_SKIP: Ends ALL turns without drawing
					facade.getGame().getPlayerAtIndex(currentPlayer).removeCardFromHand(cardIndex);
					view.displayMessage("Player " + currentPlayer + " played SUPER_SKIP");
					
					// End all turns
					facade.setCurrentPlayerNumberOfTurns(0);
					break; // Exit while loop
					
				} else if (cardType == CardType.SEE_THE_FUTURE) {
					// SEE_THE_FUTURE: Show top 3 cards, continue turn
					displayTopThreeCards();
					facade.getGame().getPlayerAtIndex(currentPlayer).removeCardFromHand(cardIndex);
					view.displayMessage("Player " + currentPlayer + " played SEE_THE_FUTURE");
					// Don't decrement turns, player can continue
					
				} else {
					// Normal cards (SHUFFLE, CAT, NOPE, etc.) - just remove from hand
					facade.getGame().getPlayerAtIndex(currentPlayer).removeCardFromHand(cardIndex);
					view.displayMessage("Player " + currentPlayer + " played " + cardType);
					// After playing a normal card, continue - player can play more cards OR press ENTER
					// Don't decrement turns here - only decrement when pressing ENTER or when hand is empty
				}

			} catch (NumberFormatException e) {
				view.displayMessage(invalidCardSelection);
			}
		}

		// Switch to next player after all turns are done
		if (facade.getPlayerTurn() == currentPlayer) {
			// Only increment if we're still on the same player
			// (ATTACK might have already switched players)
			facade.incrementPlayerTurn();
		}
	}

	private void displayTopThreeCards() {
		view.displayMessage("\n🔮 Looking at the top 3 cards of the deck:");
		for (int i = 0; i < 3 && i < facade.getGame().getDeck().getDeckSize(); i++) {
			Card card = facade.getGame().getDeck().getCardAtIndex(i);
			view.displayMessage("  " + (i + 1) + ". " + card.getCardType());
		}
		view.displayMessage("");
	}

	private void handleDrawCard(int playerIndex) {
		view.displayMessage("\nPlayer " + playerIndex + " draws a card...");
		Card drawnCard = facade.drawCard();
		Player player = facade.getGame().getPlayerAtIndex(playerIndex);
		
		if (drawnCard.getCardType() == CardType.EXPLODING_KITTEN) {
			view.displayMessage("💣 BOOM! Player " + playerIndex + " drew an EXPLODING KITTEN!");
			
			if (player.hasCard(CardType.DEFUSE)) {
				view.displayMessage("✓ Player " + playerIndex + " used a DEFUSE card and survived!");
				int defuseIndex = player.getIndexOfCard(CardType.DEFUSE);
				player.removeCardFromHand(defuseIndex);
				// Put exploding kitten back in deck (simplified: put at bottom)
				facade.getGame().getDeck().insertCard(CardType.EXPLODING_KITTEN, 1, true);
				view.displayMessage("Exploding Kitten returned to bottom of deck.");
			} else {
				view.displayMessage("✗ Player " + playerIndex + " has NO DEFUSE and is eliminated!");
				player.setIsDead();
			}
		} else if (drawnCard.getCardType() == CardType.IMPLODING_KITTEN) {
			view.displayMessage("💣 IMPLODING KITTEN! Player " + playerIndex + " drew an IMPLODING KITTEN!");
			view.displayMessage("✗ Player " + playerIndex + " is eliminated!");
			player.setIsDead();
		} else {
			// Normal card - add to player's hand
			player.addCardToHand(drawnCard);
			view.displayMessage("Player " + playerIndex + " drew: " + drawnCard.getCardType());
		}
	}

	public void endGame() {
		view.displayGameOver();
	}

	public boolean checkIfGameOver() {
		return facade.checkIfGameOver();
	}
}

