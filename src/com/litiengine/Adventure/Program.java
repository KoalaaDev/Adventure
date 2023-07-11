package com.litiengine.Adventure;

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
    Game.audio().playMusic(Resources.sounds().get("audio/looping-overcome-atmanan.mp3")); // background music

    Game.init(args);
    Game.start();
  }
}
