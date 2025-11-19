package service.action;

public class SwapTopAndBottomAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().swapTopAndBottom();
	}
}

