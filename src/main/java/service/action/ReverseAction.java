package service.action;

public class ReverseAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playReverse();
	}
}

