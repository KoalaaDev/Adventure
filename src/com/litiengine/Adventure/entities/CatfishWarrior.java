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

@EntityInfo(width = 256, height = 224)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 100)
@CollisionInfo(collision = true, collisionBoxWidth = 55, collisionBoxHeight = 80)
public class CatfishWarrior extends Enemy implements IUpdateable{
    public Ability attackAbility = new MeleeAttackAbility(this);
    public static Enemy create(){
        return new CatfishWarrior();
    }
    public CatfishWarrior(){
        super("CatfishWarrior");
        this.setMoney(1);
        this.setAttack(attackAbility);
        onDeath(e->{
            animations().play("CatfishWarrior-death-right");
            Game.loop().perform(2000, () -> {
                Game.world().environment().remove(this);
            });
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
        if(isHit() && this.healthLastInstance != this.getHitPoints().get()){
            this.healthLastInstance = this.getHitPoints().get();
        }
    }

    @Override
    protected IEntityAnimationController<?> createAnimationController() {
        IEntityAnimationController<?> controller = new CreatureAnimationController<>(this, true);
        // adding animations to the game with left and right sprites
        controller.add(new Animation("CatfishWarrior-attack-right", true, false));
        controller.add(new Animation("CatfishWarrior-attack-left", true, false));
        controller.add(new Animation("CatfishWarrior-jump-right", true, false));
        controller.add(new Animation("CatfishWarrior-jump-left", true, false));
        controller.add(new Animation("CatfishWarrior-death-right", false, false));
        controller.add(new Animation("CatfishWarrior-hurt-right", false, false));
        controller.add(new Animation("CatfishWarrior-hurt-left", false, false));
        return controller;
    }
    
}
