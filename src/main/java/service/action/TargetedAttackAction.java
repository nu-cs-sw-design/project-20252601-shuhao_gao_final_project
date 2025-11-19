package service.action;

public class TargetedAttackAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		// Targeted attack requires player selection, which should be handled by UI
		// This is a placeholder - actual implementation needs player index
		context.getGameService().playTargetedAttack(context.getPlayerIndex());
		context.getGameService().startAttackPhase();
	}
}

