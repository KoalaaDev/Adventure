package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
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
    camera.setZoom(1.2f, 0);
    Game.world().setCamera(camera);

    Game.world().setGravity(120);
    Game.world().onLoaded(e -> {

      // spawn the player instance on the spawn point with the name "enter"
      Spawnpoint enter = e.getSpawnpoint("enter");
      if (enter != null) {
        enter.spawn(Wizard.create());
      }
    });

  }
}