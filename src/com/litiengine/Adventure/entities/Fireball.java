package com.litiengine.Adventure.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.CombatInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.Prop;


@CollisionInfo(collisionBoxWidth = 30, collisionBoxHeight = 30, collision = true)
@MovementInfo(velocity = 150)
@EntityInfo(width = 80, height = 50)
@CombatInfo(hitpoints = 1)
public class Fireball extends Creature implements IUpdateable {

  private static Fireball instance;

  private int moves;

  private Fireball() {
    super("Fireball"); // Gets the fireball sprite
    this.setScaling(true);
    this.setAngle(Wizard.create().getAngle()); // set fireball direction
    Game.physics().move(this, (int) this.getAngle(), 200); // distance sets length travelled
    moves = 0;
    onCollision(c -> { //collision logic
      c.getInvolvedEntities().forEach(e -> {
        if (e instanceof Prop prop && !prop.isDead()) {
          prop.hit(50);
        } else if (e instanceof Creature creature && !creature.isDead()) {
          creature.hit(50);
        }
      });
      die();
    });
  }

  public static Fireball instance() {
    if (instance == null) {
      instance = new Fireball();
    }
    return instance;
  }

  @Override
  public void die() {
    super.die();
    Game.world().environment().remove(this);
    instance = null;
    moves = 0;
  }

  @Override
  public void update() {
    if (!Game.physics().collides(this) && moves < 50) {
      Game.physics().move(this, getFacingDirection(), 10); // move the fireball forward
      moves++;
    } else {
      die();
    }
  }
}


