package domain.game.config;

public class GameConfiguration {

	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 6;

	public static final int INITIAL_CARDS_PER_PLAYER = 5;

	public static final int DEFAULT_MAX_DECK_SIZE = 42;

	public static final int ATTACK_TARGETED_THRESHOLD = 4;

	public static final int EXPLODING_KITTENS_BASE_SIZE = 42;
	public static final int STREAKING_KITTENS_BASE_SIZE = 42;
	public static final int IMPLODING_KITTENS_BASE_SIZE = 42;

	private GameConfiguration() {
	}

	public static int getMaxDeckSize(int numberOfPlayers) {
		return DEFAULT_MAX_DECK_SIZE;
	}

	public static boolean isValidPlayerCount(int numberOfPlayers) {
		return numberOfPlayers >= MIN_PLAYERS && numberOfPlayers <= MAX_PLAYERS;
	}
}
