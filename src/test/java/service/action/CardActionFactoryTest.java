package service.action;

import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import domain.game.CardType;
import domain.game.Game;
import domain.game.events.CardPlayedEvent;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardActionFactoryTest {

	@Test
	public void testCreateAttackAction() {
		ICardActionFactory factory = CardActionFactory.getInstance();
		CardAction action = factory.createAction(CardType.ATTACK);
		assertNotNull(action);
	}

	@Test
	public void testCreateSkipAction() {
		ICardActionFactory factory = CardActionFactory.getInstance();
		CardAction action = factory.createAction(CardType.SKIP);
		assertNotNull(action);
	}

	@Test
	public void testCreateShuffleAction() {
		ICardActionFactory factory = CardActionFactory.getInstance();
		CardAction action = factory.createAction(CardType.SHUFFLE);
		assertNotNull(action);
	}

	@Test
	public void testSingletonInstance() {
		ICardActionFactory factory1 = CardActionFactory.getInstance();
		ICardActionFactory factory2 = CardActionFactory.getInstance();
		assertNotNull(factory1);
		assertNotNull(factory2);
	}
}
