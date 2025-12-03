package service.action;

import domain.game.CardType;

public interface ICardActionFactory {
	CardAction createAction(CardType cardType);
}
