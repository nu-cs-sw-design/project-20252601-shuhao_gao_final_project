package service.action;

public class AttackAction extends AbstractCardAction {
	@Override
	protected void doExecute(GameContext context) {
		context.getGameService().playAttack();
		context.getGameService().startAttackPhase();
	}
}
