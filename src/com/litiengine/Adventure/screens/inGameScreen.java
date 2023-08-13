package com.litiengine.Adventure.screens;

import com.litiengine.Adventure.entities.Wizard;

import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;


import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class inGameScreen extends GameScreen {
  public static final String NAME = "INGAME-SCREEN";

  public inGameScreen() {
    super(NAME);
  }

 @Override
  public void render(Graphics2D g) {
    super.render(g);
    g.drawImage(Resources.images().get("coin.png"), 10, 10, 50, 50, null);
    g.setColor(Color.YELLOW);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
    g.drawString(String.valueOf(Wizard.create().getMoney()), 80, 50);
    Wizard.create().healthBar.render(g);

  }

  
}