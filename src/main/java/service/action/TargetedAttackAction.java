package service.action;

public class TargetedAttackAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playTargetedAttack(context.getPlayerIndex());
		context.getGameService().startAttackPhase();
	}
}

