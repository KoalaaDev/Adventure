package com.litiengine.Adventure.abilities;

import com.litiengine.Adventure.entities.Enemy;
import com.litiengine.Adventure.entities.Player;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.ICollisionEntity;
import de.gurkenlabs.litiengine.entities.ICombatEntity;

@AbilityInfo(name = "MeleeAttackAbility")

public class MeleeAttackAbility extends Ability implements IMeleeAbility{
	public enum Type {HERO, ENEMY};
	Type team;
	private static class MeleeAttackEffect extends Effect {
		Type team;
		protected MeleeAttackEffect(Ability ability, Type T) {
			super(ability, EffectTarget.FRIENDLY);
			this.team = T;
		}

		@Override
		protected void apply(ICombatEntity entity) {
			super.apply(entity);
			// for different class apply the effect
			if(team.equals(Type.HERO)){
				entity.hit((int) ((Player) getAbility().getExecutor()).getStrength()
					* getAbility().getAttributes().value().get());
			}
			if(team.equals(Type.ENEMY)){
				entity.hit((int) ((Enemy) getAbility().getExecutor()).getStrength()
					* getAbility().getAttributes().value().get());
			}
			
		}
	}

	public MeleeAttackAbility(Player executor) {
		super(executor);
		team = Type.HERO;
		addEffect(new MeleeAttackEffect(this, team));
	}

	public MeleeAttackAbility(Enemy executor) {
		super(executor);
		team = Type.ENEMY;
		addEffect(new MeleeAttackEffect(this, team));
	}

	public boolean canHit(ICollisionEntity target) {
		return calculateImpactArea().intersects(target.getCollisionBox());
	}
}
