package com.litiengine.Adventure.entities;


import com.litiengine.Adventure.GameManager;
import com.litiengine.Adventure.abilities.MeleeAttackAbility;
import com.litiengine.Adventure.inputs.EnemyController;
import com.litiengine.Adventure.inputs.TestController;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;

@EntityInfo(width = 224, height = 192)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 100)
@CollisionInfo(collision = true, collisionBoxWidth = 40, collisionBoxHeight = 70)
public class Grunt extends Enemy implements IUpdateable{
    public Ability attackAbility = new MeleeAttackAbility(this);
    public static Enemy create(){
        return new Grunt();
    }
    public Grunt(){
        super("Grunt");
        this.setMoney(1);
        this.setAttack(attackAbility);
        this.setVisionRange(300);
        onDeath(e->{
            animations().play("Grunt-death-right");
            Game.loop().perform(2000, () -> {
                Game.world().environment().remove(this);
            });
            GameManager.getPlayer().addMoney(moneyloot);
        });
        addController(new TestController(this));
    }
    @Override
    public void update(){
        if (this.isTouchingGround()) {
            this.consecutiveJumps = 0;
        }
        if (this.colllideDeadly()){
            //respawn player
            this.die();
        }
        if(isHit() && this.healthLastInstance != this.getHitPoints().get() && !isDead()){
            this.healthLastInstance = this.getHitPoints().get();
            if(this.getFacingDirection() == Direction.LEFT)
                animations().play("Grunt-hurt-left");
            else
                animations().play("Grunt-hurt-right");
        }

        if(cooldown<=50)
            cooldown++;
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        IEntityAnimationController<?> controller = new CreatureAnimationController<>(this, true);
        // adding animations to the game with left and right sprites
        controller.add(new Animation("Grunt-attack-right", true, false));
        controller.add(new Animation("Grunt-attack-left", true, false));
        controller.add(new Animation("Grunt-jump-right", true, false));
        controller.add(new Animation("Grunt-jump-left", true, false));
        controller.add(new Animation("Grunt-death-right", false, false));
        controller.add(new Animation("Grunt-hurt-right", false, false));
        controller.add(new Animation("Grunt-hurt-left", false, false));
        return controller;
    }
    public void attack(){
        if(this.getTarget() == null || this.getTarget().isDead())
            return;
        if(cooldown>50){
            if(this.getFacingDirection() == Direction.LEFT)
                animations().play("Grunt-attack-left");
             else
                animations().play("Grunt-attack-right");
    
            this.getTarget().hit(20);
            cooldown=0;
        }
        
    }
}
