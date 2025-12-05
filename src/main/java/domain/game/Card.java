package domain.game;

import domain.game.visitor.CardVisitor;

public class Card {
	private CardType cardType;
	private boolean isMarked;
	private boolean isFacedUp;

	public Card(domain.game.CardType cardType) {
		this.isMarked = false;
		this.cardType = cardType;
		this.isFacedUp = false;
	}

	public domain.game.CardType getCardType() {
		return cardType;
	}

	public void markCard() {
		isMarked = true;
	}

	public boolean checkIfMarked() {
		return isMarked;
	}

	public void setFacedUp() {
		isFacedUp = true;
	}

	public boolean checkIfFacedUp() {
		return isFacedUp;
	}

	public void accept(CardVisitor visitor) {
		visitor.visitCard(this);
	}
}

