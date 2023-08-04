package com.litiengine.Adventure.graphics;

import com.litiengine.Adventure.entities.Player;

import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

public class PlayerCamera extends PositionLockCamera {

	public static final int STD_DELAY = 500;
	public static final float STD_ZOOM = 2f;

	public PlayerCamera(Player player) {
		super(player);
		setZoom(STD_ZOOM, STD_DELAY);
	}
}
