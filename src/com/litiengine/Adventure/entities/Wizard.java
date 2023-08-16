package com.litiengine.Adventure.entities;

import com.litiengine.Adventure.GameManager;
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
    public static Wizard create() { // create a new instance of the player
        return instance;
    }

    public Wizard() {
        super("wizard");
        addController(new PlayerMovementController(this));
        onDeath(event -> {
            Game.audio().playSound(Resources.sounds().get("audio/wizard-death.mp3"));
            animations().play("wizard-death-right");    
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
        controller.add(new Animation("wizard-hurt-right", false, false));
        controller.add(new Animation("wizard-hurt-left", false, false));
        // setting rules when to play animation
        // add for right side movement

        controller.addRule(x -> Wizard.create().range.isActive() && this.getFacingDirection() == Direction.RIGHT, x -> "wizard-attack-right");
        controller.addRule(x -> Wizard.create().jump.isActive() && this.getFacingDirection() == Direction.RIGHT, x -> "wizard-jump-right");
        controller.addRule(x -> Wizard.create().isHit() && this.getFacingDirection() == Direction.RIGHT, x -> "wizard-hurt-right");
        //add for left side movement
        controller.addRule(x -> Wizard.create().range.isActive() && this.getFacingDirection() == Direction.LEFT, x -> "wizard-attack-left");
        controller.addRule(x -> Wizard.create().jump.isActive() && this.getFacingDirection() == Direction.LEFT, x -> "wizard-jump-left");
        controller.addRule(x -> Wizard.create().isHit() && this.getFacingDirection() == Direction.LEFT, x -> "wizard-hurt-left");
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

        if(!(animations().getCurrent() == null) &&animations().getCurrent().getName().equals("wizard-walk-right")&&isDead()){
            this.setVisible(false);
            Game.world().environment().remove(this);
            // GameManager.respawn();
            GameManager.respawn();
            
        }
        if(isHit() && this.healthLastInstance != this.getHitPoints().get()){
            this.healthLastInstance = this.getHitPoints().get();
        }
        if(cooldown<=50)
            cooldown++;
        // get transition logic from map1 to map2
        if(Game.world().environment().getTrigger("map2") != null){
            if(Game.world().environment().getTrigger("map2").canTrigger(this)){
                GameManager.transition("map2");
            }
        }
        // get transition logic from map2 to map1
        if(Game.world().environment().getTrigger("map2-1") != null){
            if(Game.world().environment().getTrigger("map2-1").canTrigger(this)){
                GameManager.transition("map1");
            }
        }

        // get transition logic from map2 to map3
        if(Game.world().environment().getTrigger("map3") != null){
            if(Game.world().environment().getTrigger("map3").canTrigger(this)){
                GameManager.transition("map3");
            }
        }
        // get transition logic from map3 to map2
        if(Game.world().environment().getTrigger("map3-2") != null){
            if(Game.world().environment().getTrigger("map3-2").canTrigger(this)){
                GameManager.transition("map2");
            }
        }
        // get transition logic from map3 to map4
        if(Game.world().environment().getTrigger("map4") != null){
            if(Game.world().environment().getTrigger("map4").canTrigger(this)){
                GameManager.transition("map4");
            }
        }
        // get transition logic from map4 to map3
        if(Game.world().environment().getTrigger("map4-3") != null){
            if(Game.world().environment().getTrigger("map4-3").canTrigger(this)){
                GameManager.transition("map3");
            }
        }
    }

    public void attack(){
        if(!Game.screens().current().getName().equals("INGAME-SCREEN") || this.isDead())
            return;
        if(range.hasEnded() &&cooldown>50){
            Game.audio().playSound(Resources.sounds().get("audio/fireball.mp3"));
            range.cast();
            cooldown = 0;
        }
        
    }
}
