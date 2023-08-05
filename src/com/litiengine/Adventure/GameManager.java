package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.awt.geom.Point2D;

import com.litiengine.Adventure.entities.IInteractEntity;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;
import de.gurkenlabs.litiengine.resources.Resources;

public final class GameManager {
  public static final Set<IInteractEntity> interactEntities = new HashSet<>();
  public static Font minecraft = Resources.fonts().get("images/Minecraft.ttf");
  private void Gamemanager(){
  }
  
  public static void start(){
    Player player = getCharacterClass("Wizard");
    Game.world().setGravity(200);
    Game.world().environment().add(player);
    Point2D spawnpoint = player.getSpawnPointPos();
    player.setLocation(spawnpoint.getX(), spawnpoint.getY()-20);
    player.setScaling(true);

    Camera camera = new PositionLockCamera(player);
    camera.setClampToMap(true);
    Game.world().setCamera(camera);
    // zoom the camera in a bit
    Game.world().setCamera(camera);

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