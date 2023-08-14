package com.litiengine.Adventure.inputs;

import com.litiengine.Adventure.GameManager;
import com.litiengine.Adventure.abilities.IMeleeAbility;
import com.litiengine.Adventure.entities.Enemy;
import com.litiengine.Adventure.utilities.PathFinderUtilities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.attributes.Attribute;
import de.gurkenlabs.litiengine.attributes.AttributeModifier;
import de.gurkenlabs.litiengine.attributes.Modification;
import de.gurkenlabs.litiengine.entities.behavior.AStarNode;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.EntityNavigator;
import de.gurkenlabs.litiengine.physics.MovementController;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

import java.util.Set;

public class EnemyController extends MovementController<Enemy> {

	private static final int ATTACK_DELAY = 500;
	private static final double P_REST = 0.7;
	private static final int REST_TIME = 1000;
	private static final int WANDER_RANGE = 2;
	private int attack = GameManager.MilliToTicks(ATTACK_DELAY);
	public final EntityNavigator nav;
	private int rest = 0;
	private final AttributeModifier<Float> slowness = new AttributeModifier<>(Modification.DIVIDE, 2);

	public EnemyController(Enemy enemy) {
		super(enemy);

		nav = new EntityNavigator(enemy, new AStarPathFinder(Game.world().environment().getMap()));
	}

	private void attack() {
		nav.stop();
		getEntity().getAttackAbility().cast();
	}

	private void chase() {
		nav.navigate(getEntity().getTarget().getCenter());
	}

	private void idle() {
		if (Math.random() < P_REST) {
			rest();
		} else {
			wanderAround();
		}
	}

	private void rest() {
		rest = GameManager.MilliToTicks(REST_TIME);
	}

	private void runAway() {
		final AStarNode node = PathFinderUtilities.getFurthestNode(((AStarPathFinder) nav.getPathFinder()).getGrid(),
				getEntity().getTarget().getCenter(), getEntity().getCenter(), WANDER_RANGE);

		nav.navigate(node.getLocation());
	}

	private void slowDown() {
		final Attribute<Float> attribute = getEntity().getVelocity();
		if (!attribute.isModifierApplied(slowness)) {
			attribute.addModifier(slowness);
		}
	}

	private void speedUp() {
		final Attribute<Float> attribute = getEntity().getVelocity();
		if (attribute.isModifierApplied(slowness)) {
			attribute.removeModifier(slowness);
		}
	}

	private void turnToTarget() {
		getEntity().setAngle(GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
				getEntity().getTarget().getCenter()));
	}

	@Override
	public void update() {
		super.update();
		// maybe add flee range later
		if (rest > 0) {
			--rest;
		}

		final int dist = (int) getEntity().getCenter().distance(getEntity().getTarget().getCenter()),
				visionRange = getEntity().visionRange;
		final boolean canHit = ((IMeleeAbility) getEntity().getAttackAbility()).canHit(getEntity().getTarget()),
				canSee = dist <= visionRange, isDead = getEntity().isDead(), hasGoal = nav.isNavigating(),
				rests = rest > 0;

		if (!isDead) {
			if (canSee) {
				System.out.println("AI SPOTTED TARGET");
				speedUp();
				turnToTarget();
				if (canHit) {
					if (attack <= 0) {
						System.out.println("AI ATTACKING");
						attack();
					} else {
						--attack;
					}
				} else {
					System.out.println("AI CHASING");
					attack = GameManager.MilliToTicks(ATTACK_DELAY);
					chase();
				}
			} else {
				slowDown();
				if (!rests && !hasGoal) {
					System.out.println("AI IDLE");
					idle();
				}
			}
		}
	}

	private void wanderAround() {
		final Set<AStarNode> nodes = PathFinderUtilities.getNodesAround(
				((AStarPathFinder) nav.getPathFinder()).getGrid(), getEntity().getCenter(), WANDER_RANGE);

		nav.navigate(Game.random().choose(nodes).getLocation());
	}

	// private void isNearCliff(){
	// 	getEntity().getCollisionBox()
	// }
}
