package com.litiengine.Adventure.entities;
import java.awt.geom.Point2D;

import com.litiengine.Adventure.GameManager;
import com.litiengine.Adventure.abilities.FireballAbility;
import com.litiengine.Adventure.inputs.PlayerMovementController;
import com.litiengine.Adventure.animations.EntityAnimationController;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.GravityForce;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.ICombatEntity;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.CreatureAnimationController;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;




@EntityInfo(width = 192, height = 192)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 150)
@CollisionInfo(collision = true, collisionBoxWidth = 60, collisionBoxHeight = 80)
public final class Wizard extends Player implements IUpdateable{

    private static final Wizard instance = new Wizard();
    private final FireballAbility range = new FireballAbility(this);
    
    public static Wizard create() {
        
        return instance;
    }

    public Wizard() {
        super("wizard");
        addController(new PlayerMovementController(this));
        this.name = "Wizard";
        
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
    protected IEntityAnimationController<?> createAnimationController() {
        IEntityAnimationController<?> controller = new CreatureAnimationController<>(this, true);
        // Animation test = new Animation("wizard-walk-right", true, true)
        controller.add(new Animation("wizard-walk-right", true, true));
        controller.add(new Animation("wizard-idle-right", true, true));

        controller.add(new Animation("wizard-attack-right", true, false));
        controller.add(new Animation("wizard-jump-right", true, false));
        controller.add(new Animation("wizard-death-right", true, true));
        controller.addRule(x -> Wizard.create().isIdle(), x -> "wizard-idle-right");
        controller.addRule(x -> !Wizard.create().isIdle(), x -> "wizard-walk-right");
        controller.addRule(x -> Wizard.create().isDead(), x -> "wizard-death-right");
        controller.addRule(x -> Wizard.create().range.isActive(), x -> "wizard-attack-right");
        controller.addRule(x -> Wizard.create().jump.isActive(), x -> "wizard-jump-right");
        
        return controller;
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
        // System.out.println(animations().getCurrentAnimationName());
    }

    public void attack(){
        if(range.hasEnded()){
            range.cast();
            cooldown = 0;
        }
        
    }
}
