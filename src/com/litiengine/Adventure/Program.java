package com.litiengine.Adventure;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {

  public static void main(String[] args) {
    Game.audio().playMusic(Resources.sounds().get("audio/looping-overcome-atmanan.mp3")); // background music
    
    Game.init(args);
    Game.start();
  }
}
