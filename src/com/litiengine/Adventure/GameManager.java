package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.FreeFlightCamera;

public final class GameManager {
  
  private GameManager() {
  }
  
  public static void start(){
    Camera camera = new FreeFlightCamera(0, 0);
    camera.setClampToMap(true);
    Game.world().setCamera(camera);
  }
}