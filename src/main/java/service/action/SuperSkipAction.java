package service.action;

public class SuperSkipAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playSkip(true);
	}
}

