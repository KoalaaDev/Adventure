package com.litiengine.Adventure.abilities;

import java.awt.geom.Line2D;

import com.litiengine.Adventure.GameManager;
import com.litiengine.Adventure.entities.Fireball;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;
import com.litiengine.Adventure.utilities.GeometryUtilities;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityExecution;
import de.gurkenlabs.litiengine.resources.Resources;


public class FireballAbility extends Ability{
	// a general attack class which can be implemented by any entity
	private Player player;
	public FireballAbility(Player executor) {
		super(executor);
		this.player = executor;
	}

	@Override
	public AbilityExecution cast() {
		Game.world().environment().add(Fireball.instance());
		Game.audio().playSound(Resources.sounds().get("audio/fireball.mp3"), Fireball.instance());
		// spawn the fireball depending on the direction of the player
		if(player.getFacingDirection() == Direction.LEFT)
			Fireball.instance().setLocation(GeometryUtilities.transpose(Wizard.create().getLocation(), -100, 20));
		if(player.getFacingDirection() == Direction.RIGHT)
			Fireball.instance().setLocation(GeometryUtilities.transpose(Wizard.create().getLocation(), 100, 20));
		
		return super.cast();
	}

	public boolean hasEnded(){
		return !Fireball.instance().isLoaded();
	}


}
