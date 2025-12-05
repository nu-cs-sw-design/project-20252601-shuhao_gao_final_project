package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import domain.game.Game;
import domain.game.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TurnServiceTest {

	private TurnService turnService;
	private Game mockGame;

	@BeforeEach
	public void setUp() {
		mockGame = EasyMock.createMock(Game.class);
		turnService = new TurnService(mockGame);
	}

	@Test
	public void testGetPlayerTurn() {
		EasyMock.expect(mockGame.getPlayerTurn()).andReturn(0);
		EasyMock.replay(mockGame);

		int result = turnService.getPlayerTurn();
		assertEquals(0, result);

		EasyMock.verify(mockGame);
	}

	@Test
	public void testGetNumberOfTurns() {
		EasyMock.expect(mockGame.getNumberOfTurns()).andReturn(1);
		EasyMock.replay(mockGame);

		int result = turnService.getNumberOfTurns();
		assertEquals(1, result);

		EasyMock.verify(mockGame);
	}

	@Test
	public void testCheckIfNumberOfTurnsIsZero() {
		EasyMock.expect(mockGame.getNumberOfTurns()).andReturn(0);
		EasyMock.replay(mockGame);

		boolean result = turnService.checkIfNumberOfTurnsIsZero();
		assertEquals(true, result);

		EasyMock.verify(mockGame);
	}

	@Test
	public void testCheckIfNumberOfTurnsIsNotZero() {
		EasyMock.expect(mockGame.getNumberOfTurns()).andReturn(1);
		EasyMock.replay(mockGame);

		boolean result = turnService.checkIfNumberOfTurnsIsZero();
		assertFalse(result);

		EasyMock.verify(mockGame);
	}

	@Test
	public void testDecrementNumberOfTurns() {
		mockGame.setCurrentPlayerNumberOfTurns(0);
		EasyMock.expectLastCall();
		EasyMock.expect(mockGame.getNumberOfTurns()).andReturn(1);
		EasyMock.replay(mockGame);

		turnService.decrementNumberOfTurns();

		EasyMock.verify(mockGame);
	}
}
