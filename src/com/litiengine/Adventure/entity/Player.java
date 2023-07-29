package com.litiengine.Adventure.entity;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;

@EntityInfo(width = 18, height = 18) 
@MovementInfo(velocity = 70)
public abstract class Player extends Creature {
    // create a player class for a dungeon crawler
    private final int maxHealth;
    private int currentHealth;
    private final int maxMana;
    private int currentMana;
    private final int maxStamina;
    private int currentStamina;

    protected Player(int maxHealth, int maxMana, int maxStamina, String spriteName) {
        super(spriteName);
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.maxStamina = maxStamina;
        this.currentStamina = maxStamina;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    protected void Death() {
        Game.world().environment().remove(this);
    }


}
