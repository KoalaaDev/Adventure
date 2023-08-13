package com.litiengine.Adventure.entities;

import com.litiengine.Adventure.abilities.FireballAbility;
import com.litiengine.Adventure.inputs.PlayerMovementController;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.resources.Resources;




@EntityInfo(width = 192, height = 192)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 150)
@CollisionInfo(collision = true, collisionBoxWidth = 60, collisionBoxHeight = 80)
public final class Wizard extends Player implements IUpdateable{

    private static final Wizard instance = new Wizard();
    private final FireballAbility range = new FireballAbility(this);
    private boolean deathPlayed;
    public static Wizard create() {
        
        return instance;
    }

    public Wizard() {
        super("wizard");
        addController(new PlayerMovementController(this));
        
        onDeath(event -> {
            animations().play("wizard-death-right");
            try{
                wait(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.setVisible(false);
             
            // remove the player from the world
            // Point2D spawnpoint = this.getSpawnPointPos();
            // instance.setLocation(spawnpoint);
            // // respawn the player at the spawnpoint enter
            // instance.resurrect();
            // instance.setVisible(true);
        });
    }
    
    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        IEntityAnimationController<?> controller = new CreatureAnimationController<>(this, true);
        // adding animations to the game with left and right sprites
        controller.add(new Animation("wizard-attack-right", true, false));
        controller.add(new Animation("wizard-attack-left", true, false));
        controller.add(new Animation("wizard-jump-right", true, false));
        controller.add(new Animation("wizard-jump-left", true, false));
        controller.add(new Animation("wizard-death-right", false, false));
        // setting rules when to play animation
        // add for right side movement
        controller.addRule(x -> Wizard.create().range.isActive() && this.getFacingDirection() == Direction.RIGHT, x -> "wizard-attack-right");
        controller.addRule(x -> Wizard.create().jump.isActive() && this.getFacingDirection() == Direction.RIGHT, x -> "wizard-jump-right");
        //add for left side movement
        controller.addRule(x -> Wizard.create().range.isActive() && this.getFacingDirection() == Direction.LEFT, x -> "wizard-attack-left");
        controller.addRule(x -> Wizard.create().jump.isActive() && this.getFacingDirection() == Direction.LEFT, x -> "wizard-jump-left");
        return controller;
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

    public void attack(){
        if(range.hasEnded()){
            Game.audio().playSound(Resources.sounds().get("audio/fireball.mp3"));
            range.cast();
            cooldown = 0;
        }
        
    }
}
