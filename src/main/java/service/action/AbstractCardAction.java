package service.action;

import domain.game.events.CardPlayedEvent;

public abstract class AbstractCardAction implements CardAction {
	@Override
	public final void execute(GameContext context) {
		if (!validate(context)) {
			return;
		}
		doExecute(context);
		updateState(context);
	}

	protected boolean validate(GameContext context) {
		return true;
	}

	protected void updateState(GameContext context) {
		context.getGame().notifyObservers(
				new CardPlayedEvent(context.getPlayerIndex(), context.getCardType()));
	}

	protected abstract void doExecute(GameContext context);
}

