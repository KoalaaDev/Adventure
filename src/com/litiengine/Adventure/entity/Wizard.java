package com.litiengine.Adventure.entity;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;




@EntityInfo(width = 60, height = 60) 
@MovementInfo(velocity = 70)
@CollisionInfo(collision = true, collisionBoxWidth = 60, collisionBoxHeight = 80)
public final class Wizard extends Player implements IUpdateable {

    public static Wizard create() {
        return new Wizard();
    }

    private Wizard() {
        super("wizard");
    }
    

    @Override
    public void update() {
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
    }
    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new PlatformingMovementController<>(this);
  }
  
}
