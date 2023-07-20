package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.FreeFlightCamera;

public final class GameManager {
  
  private GameManager() {
  }
  
  public static void start(){
    Camera camera = new FreeFlightCamera(500, 1000);
    camera.setClampToMap(true);
    // zoom the camera in a bit
    camera.setZoom(1.75f, 0);
    Game.world().setCamera(camera);
    


  }
}