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
import com.litiengine.Adventure.entities.Grunt;
import com.litiengine.Adventure.entities.Player;
import com.litiengine.Adventure.entities.Wizard;
import com.litiengine.Adventure.utilities.GeometryUtilities;

import de.gurkenlabs.litiengine.resources.Resources;

public final class GameManager {
  public static Font minecraft = Resources.fonts().get("images/Minecraft.ttf");
  private static Player player = getCharacterClass("Wizard");
  public enum EnemyType{
    CATFISHWARRIOR,
    GUARDIAN,
    GRUNT
  }
  private void Gamemanager(){
  }
  
  public static void start(){
    spawnEnemy(EnemyType.CATFISHWARRIOR, 1, 0);
    Game.world().environment().add(player);
    Spawnpoint spawnpoint = player.getSpawnPointPos();
    // GeometryUtilities.setCenter(player, spawnpoint.getCenter());
    spawnpoint.spawn(player);
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


  public static void transition(String map){
    // this allows us to transition between maps only if there are no enemies left
    if(Game.world().environment().getEntities(Enemy.class).isEmpty()&& !Game.world().environment().getMap().getName().equals(map)){
      Game.world().environment().unload();
      Game.world().loadEnvironment(map);
      Spawnpoint spawn = Game.world().environment().getSpawnpoint("enter");
      // based on the map we are transitioning to, we spawn enemies specific to that map
      if(Game.world().environment().getMap().getName().equals("map1"))
        GameManager.spawnEnemy(GameManager.EnemyType.CATFISHWARRIOR, 1, 200);
      if(Game.world().environment().getMap().getName().equals("map2"))
        GameManager.spawnEnemy(GameManager.EnemyType.GUARDIAN, 1, 200);
      if(Game.world().environment().getMap().getName().equals("map3"))
        GameManager.spawnEnemy(GameManager.EnemyType.GRUNT, 1, 200);
      if(Game.world().environment().getMap().getName().equals("map4"))
        GameManager.spawnEnemy(GameManager.EnemyType.GRUNT, 1, 200);
      spawn.spawn(player);
    }
    
  }

  public static int MillisToTicks(int millis) {
    // variable tick rate conversion
    return Game.loop().getTickRate() * millis / 1000;
  }

  public static void respawn(){
    // reload the current map
    Game.world().environment().unload();
    Game.world().loadEnvironment(Game.world().environment().getMap().getName());
    Spawnpoint spawn = Game.world().environment().getSpawnpoint("enter");
    if(player.isDead()){ // if the player is dead, we resurrect them
      player.resurrect();
      player.setVisible(true);
    }
    // spawn back at the spawnpoint
    spawn.spawn(player);
  }

  public static void spawnEnemy(EnemyType cls, int waves, int delay){
    if(Game.world().environment().getSpawnpoints("enemy").isEmpty()){
      System.out.println("No enemy spawns found!");
      return;
    }
    for(int i = 0; i < waves; i++){ // for each wave, we spawn enemies based on the delay
      Game.loop().perform(delay, () -> {
        SpawnWave(cls);
      });
    }
  }

  public static void SpawnWave(EnemyType cls){
    Collection<Spawnpoint> spawns = Game.world().environment().getSpawnpoints("enemy");
    for(Spawnpoint spawn : spawns){ // we spawn an enemy at each spawnpoint
      Enemy enemy = null;
      // we create an enemy based on the type specified
      if (cls == EnemyType.CATFISHWARRIOR){
        enemy = new CatfishWarrior();
      }
      else if (cls == EnemyType.GUARDIAN){
        enemy = new Guardian();
      }
      else if (cls == EnemyType.GRUNT){
        enemy = new Grunt();
        if(Game.world().environment().getMap().getName().equals("map4"))
          enemy.setSize(352, 352);
          enemy.setCollisionBoxHeight(300);
          enemy.setCollisionBoxWidth(200);
      }
      else{
        System.out.println("Invalid enemy type!");
        return;
      }
      // spawn at the spawnpoint tagged "enemy"
      Point2D point = spawn.getLocation();
      point.setLocation(point.getX(), point.getY() - 50);
      enemy.setScaling(true);
      Game.world().environment().add(enemy);
      GeometryUtilities.setCenter(enemy, point);
    }
  }

}