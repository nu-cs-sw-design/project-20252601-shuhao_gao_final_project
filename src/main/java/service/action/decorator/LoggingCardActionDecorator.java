package service.action.decorator;

import service.action.GameContext;
import service.action.CardAction;

public class LoggingCardActionDecorator extends CardActionDecorator {

	public LoggingCardActionDecorator(CardAction wrappedAction) {
		super(wrappedAction);
	}

	@Override
	public void execute(GameContext context) {
		logBefore(context);
		wrappedAction.execute(context);
		logAfter(context);
	}

	private void logBefore(GameContext context) {
		System.out.println("[LOG] Executing action for card: " + context.getCardType() +
				" by player: " + context.getPlayerIndex());
	}

	private void logAfter(GameContext context) {
		System.out.println("[LOG] Completed action for card: " + context.getCardType());
	}
}
