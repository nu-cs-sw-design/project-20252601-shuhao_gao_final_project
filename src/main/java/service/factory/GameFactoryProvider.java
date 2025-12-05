package service.factory;

import domain.game.GameType;

/**
 * Factory Method Pattern: Provides the appropriate GameFactory based on GameType.
 * This encapsulates factory creation logic and follows the Open/Closed Principle.
 */
public class GameFactoryProvider {
	
	/**
	 * Factory Method: Creates and returns the appropriate factory for the given game type.
	 * 
	 * @param gameType The type of game
	 * @return The corresponding GameFactory implementation
	 * @throws IllegalArgumentException if gameType is null or unknown
	 */
	public static GameFactory getFactory(GameType gameType) {
		if (gameType == null) {
			throw new IllegalArgumentException("GameType cannot be null");
		}
		
		switch (gameType) {
			case EXPLODING_KITTENS:
				return new ExplodingKittensFactory();
			case IMPLODING_KITTENS:
				return new ImplodingKittensFactory();
			case STREAKING_KITTENS:
				return new StreakingKittensFactory();
			case NONE:
				// Default to Exploding Kittens for uninitialized game
				return new ExplodingKittensFactory();
			default:
				throw new IllegalArgumentException("Unknown game type: " + gameType);
		}
	}
}
