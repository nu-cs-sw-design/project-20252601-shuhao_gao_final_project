package service.action;

public class ShuffleAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		// Default shuffle count is 1
		context.getGameService().playShuffle(1);
	}
}

