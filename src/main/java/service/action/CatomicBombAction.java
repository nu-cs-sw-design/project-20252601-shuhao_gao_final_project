package service.action;

public class CatomicBombAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().playCatomicBomb();
	}
}

