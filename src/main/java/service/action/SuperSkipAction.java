package service.action;

public class SuperSkipAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().playSkip(true);
	}
}

