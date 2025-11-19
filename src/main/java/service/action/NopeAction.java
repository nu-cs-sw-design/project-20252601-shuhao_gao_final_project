package service.action;

public class NopeAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		// NOPE interrupts other actions; concrete handling occurs elsewhere
	}

	@Override
	protected void updateState(GameContext context) {
		// Override to suppress default card played event
	}
}

