package domain.game.visitor;

import domain.game.Card;
import domain.game.CardType;
import service.exception.InvalidCardActionException;

public class CardValidationVisitor implements CardVisitor {

	private boolean hasInvalidCards;
	private int invalidCardCount;

	public CardValidationVisitor() {
		this.hasInvalidCards = false;
		this.invalidCardCount = 0;
	}

	@Override
	public void visitCard(Card card) {
		if (card == null) {
			hasInvalidCards = true;
			invalidCardCount++;
			return;
		}

		if (card.getCardType() == null) {
			hasInvalidCards = true;
			invalidCardCount++;
		}
	}

	public boolean hasInvalidCards() {
		return hasInvalidCards;
	}

	public int getInvalidCardCount() {
		return invalidCardCount;
	}

	public void reset() {
		hasInvalidCards = false;
		invalidCardCount = 0;
	}
}
