package ui;

import domain.game.Card;
import domain.game.CardType;
import domain.game.Deck;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Player;
import service.CardService;
import service.GameService;
import service.TurnService;
import service.factory.GameFactory;
import service.factory.ExplodingKittensFactory;
import service.factory.StreakingKittensFactory;
import service.factory.ImplodingKittensFactory;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int playerIDZero = 0;
		final int playerIDOne = 1;
		final int playerIDTwo = 2;
		final int playerIDThree = 3;
		final int playerIDFour = 4;
		final int maxDeckSize = 42;

		// Factory will be selected based on game type chosen by user
		// For now, we'll use ExplodingKittensFactory as default
		GameFactory factory = new ExplodingKittensFactory();

		Deck deck = factory.createDeck(GameType.NONE, 0, maxDeckSize);
		Player[] players = {
				factory.createPlayer(playerIDZero),
				factory.createPlayer(playerIDOne),
				factory.createPlayer(playerIDTwo),
				factory.createPlayer(playerIDThree),
				factory.createPlayer(playerIDFour)
		};
		int[] turnTracker = {1, 1, 1, 1, 1};
		Game game = new Game(0, GameType.NONE, deck,
				players, factory.createRandom(),
				new ArrayList<Integer>(), turnTracker);

		// Create service layer
		GameService gameService = new GameService(game);
		CardService cardService = new CardService(game);
		TurnService turnService = new TurnService(game);

		// Create view and controller (MVC pattern)
		GameView view = new GameView();
		GameController controller = new GameController(gameService, cardService, turnService, view);

		// Initialize game
		controller.chooseLanguage();
		controller.chooseGame();

		// Select factory based on game type
		GameType selectedGameType = game.getGameType();
		if (selectedGameType == GameType.STREAKING_KITTENS) {
			factory = new StreakingKittensFactory();
		} else if (selectedGameType == GameType.IMPLODING_KITTENS) {
			factory = new ImplodingKittensFactory();
		} else {
			factory = new ExplodingKittensFactory();
		}

		controller.chooseNumberOfPlayers();

		// Setup game
		for (int playerCounter = 0; playerCounter < game.getNumberOfPlayers(); playerCounter++) {
			game.getPlayerAtIndex(playerCounter).addDefuse(factory.createCard(CardType.DEFUSE));
		}
		game.getDeck().initializeDeck();
		game.getDeck().shuffleDeck();
		final int cardDrawnPerPlayer = 5;
		for (int cardDrawnCounter = 0; cardDrawnCounter < cardDrawnPerPlayer; cardDrawnCounter++) {
			for (int playerCtr = 0; playerCtr < game.getNumberOfPlayers(); playerCtr++) {
				Player current = game.getPlayerAtIndex(playerCtr);
				current.addCardToHand(game.getDeck().drawCard());
			}
		}
		if (game.getGameType() == GameType.STREAKING_KITTENS) {
			game.getDeck().insertCard(CardType.EXPLODING_KITTEN,
					game.getNumberOfPlayers(), false);
		} else {
			game.getDeck().insertCard(CardType.EXPLODING_KITTEN,
					game.getNumberOfPlayers() - 1, false);
		}
		if (game.getGameType() == GameType.IMPLODING_KITTENS) {
			game.getDeck().insertCard(CardType.IMPLODING_KITTEN,
					1, false);
		}

		gameService.playShuffle(1);

		// Game loop
		while (!controller.checkIfGameOver()) {
			controller.startTurn();
		}
		controller.endGame();
	}
}
