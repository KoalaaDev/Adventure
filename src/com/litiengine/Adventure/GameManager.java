package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

import java.util.HashSet;
import java.util.Set;

import com.litiengine.Adventure.entities.IInteractEntity;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;

public final class GameManager {
  public static final Set<IInteractEntity> interactEntities = new HashSet<>();
  private GameManager() {
  }
  
  public static void start(){
    Player player = getCharacterClass("Wizard");
    Game.world().environment().add(player);
    Camera camera = new PositionLockCamera(player);
    camera.setClampToMap(true);
    Game.world().setCamera(camera);
    // zoom the camera in a bit
    Game.world().setCamera(camera);
    Game.world().onLoaded(e -> {

      // spawn the player instance on the spawn point with the name "enter"
      Spawnpoint enter = e.getSpawnpoint("enter");
      player.setLocation(enter.getCenter());
      // print a warning if the spawn point could not be found
      if (enter == null) {
        System.out.println("No spawn point with the name \"spawn\" found.");
      }
      if (enter != null) {
        
      }
    });
  }
  public static Player getCharacterClass(String characterClass){
    switch(characterClass){
      case "Wizard":
        return Wizard.create();
      default:
        return Wizard.create();
    }
    
  }

  public static Set<IInteractEntity> getInteractables(){
    return interactEntities;
  }
}