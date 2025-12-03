package service.action.decorator;

import service.action.GameContext;
import service.action.CardAction;

public abstract class CardActionDecorator implements CardAction {
	protected CardAction wrappedAction;

	public CardActionDecorator(CardAction wrappedAction) {
		this.wrappedAction = wrappedAction;
	}

	@Override
	public void execute(GameContext context) {
		wrappedAction.execute(context);
	}
}
