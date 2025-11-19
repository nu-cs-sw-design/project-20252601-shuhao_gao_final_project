package service.action;

import domain.game.Card;

public class DrawFromBottomAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		Card card = context.getGameService().drawFromBottom();
		context.getCardService().addCardToHand(card);
	}
}

