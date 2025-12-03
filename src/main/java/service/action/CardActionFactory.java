package service.action;

import domain.game.CardType;

import java.util.HashMap;
import java.util.Map;

public class CardActionFactory implements ICardActionFactory {
	private static volatile CardActionFactory instance;
	private Map<CardType, CardAction> actionMap;

	private CardActionFactory() {
		this.actionMap = new HashMap<>();
		initializeActions();
	}

	public static CardActionFactory getInstance() {
		if (instance == null) {
			synchronized (CardActionFactory.class) {
				if (instance == null) {
					instance = new CardActionFactory();
				}
			}
		}
		return instance;
	}

	private void initializeActions() {
		actionMap.put(CardType.ATTACK, new AttackAction());
		actionMap.put(CardType.SKIP, new SkipAction());
		actionMap.put(CardType.SUPER_SKIP, new SuperSkipAction());
		actionMap.put(CardType.SHUFFLE, new ShuffleAction());
		actionMap.put(CardType.SWAP_TOP_AND_BOTTOM, new SwapTopAndBottomAction());
		actionMap.put(CardType.REVERSE, new ReverseAction());
		actionMap.put(CardType.DRAW_FROM_THE_BOTTOM, new DrawFromBottomAction());
		actionMap.put(CardType.TARGETED_ATTACK, new TargetedAttackAction());
		actionMap.put(CardType.CATOMIC_BOMB, new CatomicBombAction());
		actionMap.put(CardType.NOPE, new NopeAction());

		CardAction defaultAction = new DefaultCardAction();
		for (CardType cardType : CardType.values()) {
			if (!actionMap.containsKey(cardType)) {
				actionMap.put(cardType, defaultAction);
			}
		}
	}

	@Override
	public CardAction createAction(CardType cardType) {
		CardAction action = actionMap.get(cardType);
		if (action == null) {
			return new DefaultCardAction();
		}
		return action;
	}

	private static class DefaultCardAction extends AbstractCardAction {
		@Override
		protected void doExecute(GameContext context) {
		}
	}
}
