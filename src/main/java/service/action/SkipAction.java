package service.action;

public class SkipAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playSkip(false);
	}
}

