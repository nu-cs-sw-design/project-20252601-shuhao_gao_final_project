package domain.game.visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.game.Card;
import domain.game.CardType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CardVisitorTest {

	private Card card;

	@BeforeEach
	public void setUp() {
		card = new Card(CardType.ATTACK);
	}

	@Test
	public void testCardAnalysisVisitor() {
		CardAnalysisVisitor visitor = new CardAnalysisVisitor();
		card.accept(visitor);

		assertEquals(1, visitor.getTotalCards());
		assertEquals(0, visitor.getMarkedCards());
		assertEquals(0, visitor.getFacedUpCards());
	}

	@Test
	public void testCardAnalysisVisitorWithMarkedCard() {
		card.markCard();
		CardAnalysisVisitor visitor = new CardAnalysisVisitor();
		card.accept(visitor);

		assertEquals(1, visitor.getTotalCards());
		assertEquals(1, visitor.getMarkedCards());
	}

	@Test
	public void testCardValidationVisitor() {
		CardValidationVisitor visitor = new CardValidationVisitor();
		card.accept(visitor);

		assertFalse(visitor.hasInvalidCards());
		assertEquals(0, visitor.getInvalidCardCount());
	}
}
