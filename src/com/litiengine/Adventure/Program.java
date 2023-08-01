package com.litiengine.Adventure;

import com.litiengine.Adventure.screens.inGameScreen;
import com.litiengine.Adventure.screens.MainmenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {

  public static void main(String[] args) {
    //meta data
    Game.info().setName("Adventure Legends");
    Game.info().setSubTitle("An RPG Adventure created by Group 1");
    Game.info().setVersion("v0.0.1");
    Game.info().setWebsite("https://github.com/KoalaaDev/Adventure");
    Game.info().setDescription("A 2D RPG Adventure Game created by Group 1 for the OOP course");
    
    
    //Game.audio().playMusic(Resources.sounds().get("audio/looping-overcome-atmanan.mp3")); // background music

    Game.init(args);
    GameManager.start();
    //set the icon
    Game.window().setIcon(Resources.images().get("images/windowlogo.png"));
    Game.graphics().setBaseRenderScale(1f);
    Resources.load("maps/save.litidata");

    // add the screens that will help you organize the different states of your game
    Game.screens().add(new inGameScreen());
    Game.screens().add(new MainmenuScreen());

    // load the first level (resources for the map were implicitly loaded from the game file)
    //Game.world().loadEnvironment("map1");
    
    //Game.start();
  }
}
