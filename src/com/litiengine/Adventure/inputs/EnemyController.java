package com.litiengine.Adventure.inputs;

import com.litiengine.Adventure.GameManager;
import com.litiengine.Adventure.entities.Enemy;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.entities.behavior.IBehaviorController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;

import java.awt.geom.Rectangle2D;
import java.util.Collection;


public class EnemyController implements IBehaviorController{
	private final Enemy enemy;
	private long directionChanged;
	private long nextDirectionChange;
	private Direction direction;
	private static final int ATTACK_DELAY = 50;
	private static final int ATTACK_RANGE = 80;
	private int attack = GameManager.MillisToTicks(ATTACK_DELAY);
	public EnemyController(Enemy enemy) {
		this.enemy = enemy;
	}

	@Override
	public IEntity getEntity() {
		return this.enemy;
	}

	protected boolean canJump(){
		//detect a collision box that is higher than the enemy
		//if there is one, return true
		//else return false
		Rectangle2D groundCheck = new Rectangle2D.Double(enemy.getCollisionBox().getX(), enemy.getCollisionBox().getY(), enemy.getCollisionBoxWidth()+1, enemy.getCollisionBoxHeight());
		return Game.physics().collides(groundCheck, Collision.STATIC);
	}

	protected boolean isNearCliff(){
		/*
		 * We detect if the entity collides with a predefined map Trigger called cliff
		 * If Yes, We return true
		 */
        Collection<Trigger> cliffs = Game.world().environment().getTriggers("cliff");
        for (Trigger cliff : cliffs) {
            if (cliff == null) {
                return false;
            }
            if (cliff.canTrigger(enemy)) {
                return true;
            }
        }
        return false;
    }

	protected boolean canSee(){
		// detect the player if he is in the enemy's vision range
		// if he is, return true
		// else return false
		final int dist = (int) enemy.getCenter().distance(enemy.getTarget().getCenter()),
				visionRange = enemy.visionRange;
		
		return dist <= visionRange;
	}

	private void turnToTarget(){
		double Angle = GeometricUtilities.calcRotationAngleInDegrees(getEntity().getCenter(),
				enemy.getTarget().getCenter());
		// if the player is to the right of the enemy, turn right
		if(Angle > 0 && Angle < 180){
			direction = Direction.RIGHT;
		}
		// if the player is to the left of the enemy, turn left
		else if(Angle > 180 && Angle < 360){
			direction = Direction.LEFT;
		}

	}
	@Override
	public void update() {
		if (this.enemy.isDead()) {
			return;
		}
		final long timeSinceDirectionChange = Game.time().since(this.directionChanged);
		// random idle walking
		if (timeSinceDirectionChange > this.nextDirectionChange) {
			direction = this.direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
			this.directionChanged = Game.time().now();
			this.nextDirectionChange = Game.random().nextInt(1000, 2000);
		}
		// if the enemy is near a cliff, turn around
		if(isNearCliff()){
			direction = this.direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
			this.directionChanged = Game.time().now();
			this.nextDirectionChange = 10000;
		}
		// if the enemy is near a wall, that might be just a small bump, jump
		if(canJump()){
			this.enemy.jump.cast();
		}
		// if the enemy can see the player, turn to him and attack
		if(canSee() && !isNearCliff()){
			turnToTarget();
			if((int) enemy.getCenter().distance(enemy.getTarget().getCenter())<ATTACK_RANGE){
				if (attack <= 0){
					enemy.attack();
				}
				else{
					--attack;
				}
				
			}
			else{
				attack = GameManager.MillisToTicks(ATTACK_DELAY);
			}

				
		}
		// based on above logic, move the enemy
		this.getEntity().setAngle(this.direction.toAngle());
		Game.physics().move(this.enemy, this.enemy.getTickVelocity());
		
	}

}
