package com.litiengine.Adventure.entities;
import com.litiengine.Adventure.abilities.Jump;
import com.litiengine.Adventure.hotbar.Hotbar;
import com.litiengine.Adventure.hp.PlayerHealthBar;
import com.litiengine.Adventure.inputs.PlayerMovementController;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.GravityForce;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.geom.Rectangle2D;


public abstract class Player extends Creature {
    // create a base player class which can serve as a base for future implementation of new heroes
    public String name;
    protected final Jump jump;
    protected int consecutiveJumps;
    private int money = 0, lvl = 1;
    public static final int MAX_ADDITIONAL_JUMPS = 1;
    public final Hotbar hotbar = new Hotbar(this);
    public final PlayerHealthBar healthBar = new PlayerHealthBar(this);
    public int updatetimer = 0;
    public int healthLastInstance = 100;
    public int cooldown = 0;
    protected Player(String spriteName) {
        super(spriteName);
        GravityForce force = new GravityForce(this, 100, Direction.DOWN);
        force.setCancelOnCollision(true);
        onMoved(e -> {
            if (updatetimer == 0) {
				Game.audio().playSound(Resources.sounds().get("audio/step.wav"));
				updatetimer = 20;
			}
            if(!this.isTouchingGround()){ // if player isnt on the ground, apply a force
                force.setStrength(100);
                this.movement().apply(force);
            }
			// print all effects applied to the player
            System.out.println(force.hasEnded());
            System.out.println(this.movement().getActiveForces());

            
        });
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
        Rectangle2D groundCheck = new Rectangle2D.Double(this.getCollisionBox().getX(), this.getCollisionBox().getY(), this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 5);

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
    public abstract void attack();

    public final Spawnpoint getSpawnPointPos(){
        return Game.world().environment().getSpawnpoints("player").iterator().next();
    }

    public final int getMoney(){
        return money;
    }

    public final int getLvl(){
        return lvl;
    }


}

