package com.litiengine.Adventure.entities;
import java.awt.geom.Point2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.entities.MovementInfo;




@EntityInfo(width = 80, height = 100)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 150)
@CollisionInfo(collision = true, collisionBoxWidth = 60, collisionBoxHeight = 80)
public final class Wizard extends Player implements IUpdateable{

    private static final Wizard instance = new Wizard();
    
    public static Wizard create() {
        
        return instance;
    }

    public Wizard() {
        super("wizard");
        // onDeath(event -> {
        //     instance.setVisible(false);
        //     // remove the player from the world
        //     // Point2D spawnpoint = this.getSpawnPointPos();
        //     // instance.setLocation(spawnpoint);
        //     // // respawn the player at the spawnpoint enter
        //     // instance.resurrect();
        //     // instance.setVisible(true);
        // });
    }
    

    @Override
    public void update() {
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
        if (this.colllideDeadly()){
            //respawn player
            instance.die();
        }
    }
    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new PlatformingMovementController<>(this);
  }

}
