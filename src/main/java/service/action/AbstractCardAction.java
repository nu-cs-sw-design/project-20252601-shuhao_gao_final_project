package service.action;

import domain.game.events.CardPlayedEvent;

public abstract class AbstractCardAction implements CardAction {
	@Override
	public final void execute(GameContext context) {
		try {
			beforeValidate(context);
			if (!validate(context)) {
				onValidationFailed(context);
				return;
			}
			beforeExecute(context);
			doExecute(context);
			afterExecute(context);
			updateState(context);
		} catch (Exception e) {
			onError(e, context);
		}
	}

	protected void beforeValidate(GameContext context) {
	}

	protected boolean validate(GameContext context) {
		return true;
	}

	protected void onValidationFailed(GameContext context) {
	}

	protected void beforeExecute(GameContext context) {
	}

	protected abstract void doExecute(GameContext context);

	protected void afterExecute(GameContext context) {
	}

	protected void updateState(GameContext context) {
		CardPlayedEvent event = new CardPlayedEvent(
				context.getPlayerIndex(), context.getCardType());
		context.getGame().notifyObservers(event);
	}

	protected void onError(Exception e, GameContext context) {
		throw new RuntimeException("Error executing action", e);
	}
}

