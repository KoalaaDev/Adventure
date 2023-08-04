package com.litiengine.Adventure.entities;

import de.gurkenlabs.litiengine.entities.EntityListener;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.environment.Environment;
import com.litiengine.Adventure.GameManager;;

public interface IInteractEntity extends IEntity {

	final class InteractEntityListener implements EntityListener {
		@Override
		public void loaded(IEntity entity, Environment environment) {
			GameManager.getInteractables().add((IInteractEntity) entity);
		}

		@Override
		public void removed(IEntity entity, Environment environment) {
			GameManager.getInteractables().remove(entity);
		}
	}

	void interact(Player player);
}
