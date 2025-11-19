package service.action;

public class CatomicBombAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playCatomicBomb();
	}
}

