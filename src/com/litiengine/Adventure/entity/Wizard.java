package com.litiengine.Adventure.entity;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.IMovementController;

public final class Wizard extends Player implements IUpdateable {
    public static Wizard create() {
        return new Wizard(100, 100, 100);
    }

    private Wizard(int maxHealth, int maxMana, int maxStamina) {
        super(maxHealth, maxMana, maxStamina, "wizard");
    }
    

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
    @Override
    protected IMovementController createMovementController() {
        // setup movement controller
        return new PlatformingMovementController<>(this);
  }
}
