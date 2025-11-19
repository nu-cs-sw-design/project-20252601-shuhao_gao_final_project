package service.action;

public class AttackAction implements CardAction {
	@Override
	public void execute(GameContext context) {
		context.getGameService().playAttack();
		context.getGameService().startAttackPhase();
	}
}

