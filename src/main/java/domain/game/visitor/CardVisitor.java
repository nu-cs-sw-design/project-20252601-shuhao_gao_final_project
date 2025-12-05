package domain.game.visitor;

import domain.game.Card;

public interface CardVisitor {
	void visitCard(Card card);
}
