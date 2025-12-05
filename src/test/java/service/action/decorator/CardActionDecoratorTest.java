package service.action.decorator;

import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import service.action.CardAction;
import service.action.GameContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CardActionDecoratorTest {

	@Test
	public void testLoggingDecorator() {
		CardAction mockAction = EasyMock.createMock(CardAction.class);
		mockAction.execute(EasyMock.anyObject(GameContext.class));
		EasyMock.expectLastCall();
		EasyMock.replay(mockAction);

		CardActionDecorator decorator =
				new LoggingCardActionDecorator(mockAction);
		assertNotNull(decorator);

		EasyMock.verify(mockAction);
	}

	@Test
	public void testValidationDecorator() {
		CardAction mockAction = EasyMock.createMock(CardAction.class);
		CardActionDecorator decorator =
				new ValidationCardActionDecorator(mockAction);
		assertNotNull(decorator);
	}

	@Test
	public void testExceptionHandlingDecorator() {
		CardAction mockAction = EasyMock.createMock(CardAction.class);
		CardActionDecorator decorator =
				new ExceptionHandlingCardActionDecorator(mockAction);
		assertNotNull(decorator);
	}
}
