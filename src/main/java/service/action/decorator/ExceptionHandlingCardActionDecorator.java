package service.action.decorator;

import service.action.GameContext;
import service.action.CardAction;

public class ExceptionHandlingCardActionDecorator extends CardActionDecorator {

	public ExceptionHandlingCardActionDecorator(CardAction wrappedAction) {
		super(wrappedAction);
	}

	@Override
	public void execute(GameContext context) {
		try {
			wrappedAction.execute(context);
		} catch (IllegalArgumentException e) {
			handleError(e, context);
		} catch (UnsupportedOperationException e) {
			handleError(e, context);
		} catch (Exception e) {
			handleUnexpectedError(e, context);
		}
	}

	private void handleError(Exception e, GameContext context) {
		System.err.println("[ERROR] Action failed for card: " + context.getCardType() +
				" - " + e.getMessage());
	}

	private void handleUnexpectedError(Exception e, GameContext context) {
		System.err.println("[FATAL] Unexpected error during action: "
				+ context.getCardType());
		e.printStackTrace();
	}
}
