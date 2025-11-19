package service.action;

public class SwapTopAndBottomAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().swapTopAndBottom();
	}
}

