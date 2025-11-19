package service.action;

public class SkipAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().playSkip(false);
	}
}

