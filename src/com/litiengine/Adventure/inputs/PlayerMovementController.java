package com.litiengine.Adventure.inputs;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.litiengine.Adventure.entities.Player;

import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.util.ListUtilities;

public class PlayerMovementController extends PlatformingMovementController<Player>{
    private List<Integer> attackKeys = new ArrayList<>();
    private List<Integer> interactKeys = new ArrayList<>();
    private boolean canInteract = true;
    private final Player player;

	public PlayerMovementController(Player player) {
		this(player, KeyEvent.VK_ENTER, KeyEvent.VK_F);
	}

	public PlayerMovementController(Player player, int attack, int interact) {
		super(player);
		this.player = player;
		addAttackKey(attack);
		addInteractKey(interact);

		Input.keyboard().onKeyReleased(this::handleReleasedKey);
	}

    public void addAttackKey(int keyCode) {
		if (!attackKeys.contains(keyCode)) attackKeys.add(keyCode);
	}

    public void addInteractKey(int keyCode) {
		if (!interactKeys.contains(keyCode)) interactKeys.add(keyCode);
	}

    public List<Integer> getAttackKeys() {
		return attackKeys;
	}

    public List<Integer> getInteractKeys() {
		return interactKeys;
	}

    @Override
	public void handlePressedKey(KeyEvent event) {
		super.handlePressedKey(event);
		if (attackKeys.contains(event.getKeyCode())) 
            player.attack();
	}
    

    public void handleReleasedKey(KeyEvent event) {
		if (interactKeys.contains(event.getKeyCode())) canInteract = true;
	}

    public void setAttackKeys(int... attack) {
		attackKeys = ListUtilities.getIntList(attack);
	}
}
