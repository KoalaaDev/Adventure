package com.litiengine.Adventure.abilities;

import de.gurkenlabs.litiengine.entities.ICollisionEntity;

public interface IHitAbility {
	boolean canHit(ICollisionEntity target);
}
