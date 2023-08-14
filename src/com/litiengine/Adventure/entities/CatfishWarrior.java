package com.litiengine.Adventure.entities;

import com.litiengine.Adventure.abilities.MeleeAttackAbility;
import com.litiengine.Adventure.inputs.EnemyController;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;

@EntityInfo(width = 256, height = 224)
@CombatInfo(hitpoints = 100)
@MovementInfo(velocity = 100)
@CollisionInfo(collision = true, collisionBoxWidth = 55, collisionBoxHeight = 80)
public class CatfishWarrior extends Enemy{
    private Ability attackAbility = new MeleeAttackAbility(this);
    
    public CatfishWarrior(){
        super("CatfishWarrior");
        this.setMoney(1);
        this.setAttack(attackAbility);
        addController(new EnemyController(this));
    }

    
}
