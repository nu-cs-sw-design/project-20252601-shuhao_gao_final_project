package service.action;

public class ShuffleAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playShuffle(1);
	}
}

