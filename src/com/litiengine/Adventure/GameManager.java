package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.awt.geom.Point2D;

import com.litiengine.Adventure.entities.CatfishWarrior;
import com.litiengine.Adventure.entities.Guardian;
import com.litiengine.Adventure.entities.Enemy;
import com.litiengine.Adventure.entities.IInteractEntity;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;
import com.litiengine.Adventure.utilities.GeometryUtilities;

import de.gurkenlabs.litiengine.resources.Resources;

public final class GameManager {
  public static final Set<IInteractEntity> interactEntities = new HashSet<>();
  public static Font minecraft = Resources.fonts().get("images/Minecraft.ttf");
  private static Player player = getCharacterClass("Wizard");
  public enum EnemyType{
    CATFISHWARRIOR,
    GUARDIAN
  }
  private void Gamemanager(){
  }
  
  public static void start(){
    spawnEnemy(EnemyType.CATFISHWARRIOR, 1, 0);
    Game.world().environment().add(player);
    Spawnpoint spawnpoint = player.getSpawnPointPos();
    GeometryUtilities.setCenter(player, spawnpoint.getCenter());
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

  public static Player setCharacterClass(String characterClass){
    return getCharacterClass(characterClass);
  }

  public static Player getPlayer(){
    return player;
  }

  public static Set<IInteractEntity> getInteractables(){
    return interactEntities;
  }

  public static void transition(String map){
    if(Game.world().environment().getEntities(Enemy.class).isEmpty()&& !Game.world().environment().getMap().getName().equals(map)){
      Game.world().environment().unload();
      Game.world().loadEnvironment(map);
      Spawnpoint spawn = Game.world().environment().getSpawnpoint("enter");
      if(Game.world().environment().getMap().getName().equals("map1"))
        GameManager.spawnEnemy(GameManager.EnemyType.CATFISHWARRIOR, 1, 200);
      if(Game.world().environment().getMap().getName().equals("map2"))
        GameManager.spawnEnemy(GameManager.EnemyType.GUARDIAN, 1, 200);
      spawn.spawn(player);
    }
    
  }

  public static int MillisToTicks(int millis) {
    // variable tick rate conversion
    return Game.loop().getTickRate() * millis / 1000;
  }

  public static void respawn(){
    Game.world().environment().unload();
    Game.world().loadEnvironment(Game.world().environment().getMap().getName());
    Spawnpoint spawn = Game.world().environment().getSpawnpoint("enter");
    if(player.isDead()){
      player.resurrect();
      player.setVisible(true);
    }
    
    spawn.spawn(player);
  }

  public static void spawnEnemy(EnemyType cls, int waves, int delay){
    if(Game.world().environment().getSpawnpoints("enemy").isEmpty()){
      System.out.println("No enemy spawns found!");
      return;
    }
    for(int i = 0; i < waves; i++){
      Game.loop().perform(delay, () -> {
        SpawnWave(cls);
      });
    }
  }

  public static void SpawnWave(EnemyType cls){
    Collection<Spawnpoint> spawns = Game.world().environment().getSpawnpoints("enemy");
    for(Spawnpoint spawn : spawns){
      Enemy enemy = null;
      if (cls == EnemyType.CATFISHWARRIOR){
        enemy = new CatfishWarrior();
      }
      else if (cls == EnemyType.GUARDIAN){
        enemy = new Guardian();
      }
      else{
        System.out.println("Invalid enemy type!");
        return;
      }
      
      Point2D point = spawn.getLocation();
      point.setLocation(point.getX(), point.getY() - 50);
      enemy.setScaling(true);
      Game.world().environment().add(enemy);
      GeometryUtilities.setCenter(enemy, point);
    }
  }

}