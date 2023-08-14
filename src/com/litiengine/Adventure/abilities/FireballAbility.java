package com.litiengine.Adventure.abilities;


import com.litiengine.Adventure.entities.Fireball;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;
import com.litiengine.Adventure.utilities.GeometryUtilities;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityExecution;



public class FireballAbility extends Ability{
	// a Fireball attack for the wizard hero
	private Player player;
	public FireballAbility(Player executor) {
		super(executor);
		this.player = executor;
	}
	
	@Override
	public AbilityExecution cast() {
		Game.world().environment().add(Fireball.instance());
		// spawn the fireball depending on the direction of the player
		if(player.getFacingDirection() == Direction.LEFT)
			Fireball.instance().setLocation(GeometryUtilities.transpose(Wizard.create().getLocation(), -100, 100));
			Fireball.instance().setFacingDirection(null);
		if(player.getFacingDirection() == Direction.RIGHT)
			Fireball.instance().setLocation(GeometryUtilities.transpose(Wizard.create().getLocation(), 200, 100));
		
		return super.cast();
	}

	public boolean hasEnded(){
		return !Fireball.instance().isLoaded();
	}

	@Override
	public boolean isActive(){
		return !hasEnded();
	}
}
