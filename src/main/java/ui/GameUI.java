package ui;

public class GameUI {
	private final GameController controller;

	public GameUI(GameController controller) {
		this.controller = controller;
	}

	public void run() {
		controller.chooseLanguage();
		controller.chooseGame();
		controller.chooseNumberOfPlayers();

		while (!controller.checkIfGameOver()) {
			controller.startTurn();
		}
		controller.endGame();
	}
}

