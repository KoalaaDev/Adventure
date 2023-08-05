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
@MovementInfo(velocity = 150)
@CollisionInfo(collision = true, collisionBoxWidth = 60, collisionBoxHeight = 80)
public final class Wizard extends Player implements IUpdateable{

    public static Wizard create() {
        
        return new Wizard();
    }

    private Wizard() {
        super("wizard");
        Game.world().onLoaded(e -> {
            Point2D spawnpoint = this.getSpawnPointPos();
            this.setLocation(spawnpoint.getX(), spawnpoint.getY());
        });
        onDeath(event -> {
            setVisible(false);
            // remove the player from the world
            Point2D spawnpoint = this.getSpawnPointPos();
            setLocation(spawnpoint);
            // respawn the player at the spawnpoint enter
            resurrect();
            setVisible(true);
        });
    }
    

    @Override
    public void update() {
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
        if (this.colllideDeadly()){
            //respawn player
            this.die();
        }
    }
    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new PlatformingMovementController<>(this);
  }

}
