package com.litiengine.Adventure.inputs;

import com.litiengine.Adventure.entities.Enemy;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.behavior.IBehaviorController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.util.MathUtilities;

import java.awt.geom.Rectangle2D;

public class TestController implements IBehaviorController{
	private final Enemy enemy;
	private long directionChanged;
	private long nextDirectionChange;
	private Direction direction;
	
	public TestController(Enemy enemy) {
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
	@Override
	public void update() {
		if (this.enemy.isDead()) {
			return;
		}
		final long timeSinceDirectionChange = Game.time().since(this.directionChanged);
		if (timeSinceDirectionChange > this.nextDirectionChange) {
			direction = this.direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
			this.directionChanged = Game.time().now();
			this.nextDirectionChange = Game.random().nextInt(1000, 2000);
		}
		if(enemy.isNearCliff()){
			direction = this.direction == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
			this.directionChanged = Game.time().now();
			this.nextDirectionChange = 1500;
		}
		if(canJump()){
			this.enemy.jump.cast();
		}
		this.getEntity().setAngle(this.direction.toAngle());
		Game.physics().move(this.enemy, this.enemy.getTickVelocity());
		
	}

}
