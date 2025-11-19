package service.action;

import domain.game.Card;

public class DrawFromBottomAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		Card card = context.getGameService().drawFromBottom();
		context.getCardService().addCardToHand(card);
	}
}

