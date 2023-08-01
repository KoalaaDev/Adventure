package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import com.litiengine.Adventure.entity.Wizard;

public final class GameManager {
  
  private GameManager() {
  }
  
  public static void start(){
    Camera camera = new PositionLockCamera(Wizard.create());
    camera.setClampToMap(true);
    Game.world().setCamera(camera);
    // zoom the camera in a bit
    Game.world().setCamera(camera);

    Game.world().onLoaded(e -> {

      // spawn the player instance on the spawn point with the name "enter"
      Spawnpoint enter = e.getSpawnpoint("enter");
      // print a warning if the spawn point could not be found
      if (enter == null) {
        System.out.println("No spawn point with the name \"spawn\" found.");
      }
      if (enter != null) {
        enter.spawn(Wizard.create());
      }
    });
  }
}