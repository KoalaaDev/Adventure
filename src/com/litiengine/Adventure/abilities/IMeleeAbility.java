package com.litiengine.Adventure.abilities;

import de.gurkenlabs.litiengine.entities.ICollisionEntity;

public interface IMeleeAbility {
	boolean canHit(ICollisionEntity target);
}