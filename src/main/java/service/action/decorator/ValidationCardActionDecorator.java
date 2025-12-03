package service.action.decorator;

import service.action.GameContext;
import service.action.CardAction;
import service.exception.InvalidCardActionException;

public class ValidationCardActionDecorator extends CardActionDecorator {

	public ValidationCardActionDecorator(CardAction wrappedAction) {
		super(wrappedAction);
	}

	@Override
	public void execute(GameContext context) {
		validateContext(context);
		wrappedAction.execute(context);
	}

	private void validateContext(GameContext context) {
		if (context == null) {
			throw new InvalidCardActionException("GameContext cannot be null");
		}
		if (context.getCardType() == null) {
			throw new InvalidCardActionException("CardType cannot be null");
		}
		if (context.getPlayerIndex() < 0) {
			throw new InvalidCardActionException("Player index must be non-negative");
		}
		if (context.getGameService() == null) {
			throw new InvalidCardActionException(
					"GameService cannot be null in context");
		}
	}
}
