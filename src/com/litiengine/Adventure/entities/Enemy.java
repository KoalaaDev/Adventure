package com.litiengine.Adventure.entities;

import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.physics.GravityForce;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.physics.Collision;

import java.awt.geom.Rectangle2D;

import com.litiengine.Adventure.GameManager;


public abstract class Enemy extends Creature {
    public int moneyloot, strength, visionRange;
    private Ability attackAbility;
    protected Enemy(String spriteName) {
        super(spriteName);
        this.setTarget(GameManager.getPlayer());
        onMoved(e -> {
            GravityForce force = new GravityForce(this, 300, Direction.DOWN);
            force.setIdentifier("Gravity"); // add an identifier for later
            if(!this.isTouchingGround() && this.movement().getForce("Gravity") == null){
                this.movement().apply(force);

            }
        });
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
    public final void setStrength(int strength){
        this.strength = strength;
    }

    public final int getStrength(){
        return this.strength;
    }

    public final void setMoney(int money){
        this.moneyloot = money;
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

    public Ability getAttackAbility(){
        return this.attackAbility;
    }

    protected void setAttack(Ability ability){
        this.attackAbility = ability;
    }
}
