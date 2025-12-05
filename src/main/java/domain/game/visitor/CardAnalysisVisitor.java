package domain.game.visitor;

import domain.game.Card;
import domain.game.CardType;

public class CardAnalysisVisitor implements CardVisitor {

	private int totalCards;
	private int markedCards;
	private int facedUpCards;

	public CardAnalysisVisitor() {
		this.totalCards = 0;
		this.markedCards = 0;
		this.facedUpCards = 0;
	}

	@Override
	public void visitCard(Card card) {
		totalCards++;
		if (card.checkIfMarked()) {
			markedCards++;
		}
		if (card.checkIfFacedUp()) {
			facedUpCards++;
		}
	}

	public int getTotalCards() {
		return totalCards;
	}

	public int getMarkedCards() {
		return markedCards;
	}

	public int getFacedUpCards() {
		return facedUpCards;
	}

	public void reset() {
		totalCards = 0;
		markedCards = 0;
		facedUpCards = 0;
	}
}
