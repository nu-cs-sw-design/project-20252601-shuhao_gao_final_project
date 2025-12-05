package ui;

import domain.game.CardType;
import domain.game.Deck;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Player;
import service.CardService;
import service.GameService;
import service.TurnService;
import service.action.CardActionFactory;
import service.facade.GameFacade;
import service.factory.GameFactory;
import service.factory.GameFactoryProvider;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int playerIDZero = 0;
		final int playerIDOne = 1;
		final int playerIDTwo = 2;
		final int playerIDThree = 3;
		final int playerIDFour = 4;
		final int maxDeckSize = 42;

		// Use Factory Method Pattern: Get factory through provider
		// Initial factory for setup (before user selects game type)
		GameFactory factory = GameFactoryProvider.getFactory(GameType.NONE);

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

		// Create service layer with dependency injection
		GameService gameService = new GameService(game, CardActionFactory.getInstance());
		CardService cardService = new CardService(game);
		TurnService turnService = new TurnService(game);

		// Create facade, view and controller (MVC pattern with facade)
		GameFacade facade = new GameFacade(gameService, cardService, turnService);
		GameView view = new GameView();
		GameController controller = new GameController(facade, view);

		// Initialize game
		controller.chooseLanguage();
		controller.chooseGame();

		// Use Factory Method Pattern: Get the appropriate factory based on selected game type
		// This demonstrates the Open/Closed Principle - no need to modify Main when adding new game types
		GameType selectedGameType = game.getGameType();
		factory = GameFactoryProvider.getFactory(selectedGameType);

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
