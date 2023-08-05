package com.litiengine.Adventure.entities;
import com.litiengine.Adventure.abilities.Jump;
import com.litiengine.Adventure.hotbar.Hotbar;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.physics.Collision;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public abstract class Player extends Creature {
    // create a player class for a dungeon crawler
    protected final Jump jump;
    protected int consecutiveJumps;
    public static final int MAX_ADDITIONAL_JUMPS = 1;
    public final Hotbar hotbar = new Hotbar(this);
    public int healthLastInstance = 100;
    
    protected Player(String spriteName) {
        super(spriteName);
        this.jump = new Jump(this);
    }

    @Action(description = "This performs the jump ability for the player's entity.")
    public void jump() {
        if (this.consecutiveJumps >= MAX_ADDITIONAL_JUMPS || !this.jump.canCast()) {
            return;
        }

        this.jump.cast();
        this.consecutiveJumps++;
    }

    protected boolean isTouchingGround() {
        // the idea of this ground check is to extend the current collision box by
        // one pixel and see if
        // a) it collides with any static collision box
        Rectangle2D groundCheck = new Rectangle2D.Double(this.getCollisionBox().getX(), this.getCollisionBox().getY(), this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 1);

        // b) it collides with the map's boundaries
        if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxY()) {
        return true;
        }

        return Game.physics().collides(groundCheck, Collision.STATIC);
    }

    protected boolean colllideDeadly(){
        Rectangle2D deadlyCheck = new Rectangle2D.Double(this.getCollisionBox().getX(), this.getCollisionBox().getY(), this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 1);
        java.util.Collection<CollisionBox> deadly = Game.world().environment().getCollisionBoxes("deadly");
        for (CollisionBox box : deadly) {
            if (deadlyCheck.intersects(box.getBoundingBox())) {
                return true;
            }
        }
        return false;
    }

    public final Point2D getSpawnPointPos(){
        return this.getEnvironment().getSpawnpoints("player").iterator().next().getCenter();
    }
    

}

