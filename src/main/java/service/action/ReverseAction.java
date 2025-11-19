package service.action;

public class ReverseAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().playReverse();
	}
}

