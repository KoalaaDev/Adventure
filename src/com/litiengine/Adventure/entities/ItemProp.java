package com.litiengine.Adventure.entities;

import com.litiengine.Adventure.items.Item;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.Animation;

public class ItemProp extends Prop implements IInteractEntity {

	public final Item item;

	public ItemProp(Item item) {
		super("item");
		addListener(new InteractEntityListener());

		this.item = item;
		animations().add(new Animation(item.getSprite(), false, false, 0));
	}

	@Override
	public void interact(Player player) {
		if (player.hotbar.addItem(item)) Game.world().environment().remove(this);
	}
}
