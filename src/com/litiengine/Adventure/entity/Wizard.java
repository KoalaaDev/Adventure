package com.litiengine.Adventure.entity;
import com.litiengine.Adventure.GameManager;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;




@EntityInfo(width = 60, height = 60) 
@MovementInfo(velocity = 110)
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
        if (this.colllideDeadly()){
            //respawn player
            this.die();
            // remove the player from the world
            Game.world().environment().remove(this);
            // respawn the player at the spawnpoint enter
            
        }
    }
    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new PlatformingMovementController<>(this);
  }
  
}
