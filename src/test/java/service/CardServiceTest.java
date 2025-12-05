package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import domain.game.Card;
import domain.game.CardType;
import domain.game.Deck;
import domain.game.Game;
import domain.game.GameType;
import domain.game.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardServiceTest {

	private CardService cardService;
	private Game mockGame;

	@BeforeEach
	public void setUp() {
		mockGame = EasyMock.createMock(Game.class);
		cardService = new CardService(mockGame);
	}

	@Test
	public void testGetCardType() {
		Card mockCard = EasyMock.createMock(Card.class);
		Player mockPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(mockGame.getPlayerAtIndex(0)).andReturn(mockPlayer);
		EasyMock.expect(mockPlayer.getCardAt(0)).andReturn(mockCard);
		EasyMock.expect(mockCard.getCardType()).andReturn(CardType.ATTACK);
		EasyMock.replay(mockGame, mockPlayer, mockCard);

		CardType result = cardService.getCardType(0, 0);
		assertEquals(CardType.ATTACK, result);

		EasyMock.verify(mockGame, mockPlayer, mockCard);
	}

	@Test
	public void testGetHandSize() {
		Player mockPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(mockGame.getPlayerAtIndex(0)).andReturn(mockPlayer);
		EasyMock.expect(mockPlayer.getHandSize()).andReturn(5);
		EasyMock.replay(mockGame, mockPlayer);

		int result = cardService.getHandSize(0);
		assertEquals(5, result);

		EasyMock.verify(mockGame, mockPlayer);
	}

	@Test
	public void testCheckIfPlayerHasCard() {
		Player mockPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(mockGame.getPlayerAtIndex(0))
				.andReturn(mockPlayer);
		EasyMock.expect(mockPlayer.hasCard(CardType.DEFUSE))
				.andReturn(true);
		EasyMock.replay(mockGame, mockPlayer);

		boolean result = cardService.checkIfPlayerHasCard(0, CardType.DEFUSE);
		assertEquals(true, result);

		EasyMock.verify(mockGame, mockPlayer);
	}
}
