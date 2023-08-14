package com.litiengine.Adventure.screens;

import com.litiengine.Adventure.entities.Wizard;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.IRenderable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class inGameScreen extends GameScreen implements KeyPressedListener{
  public static final String NAME = "INGAME-SCREEN";

  public inGameScreen() {
    super(NAME);
  }

    public final List<IRenderable> overlayMenus = new ArrayList<>();


    public void addOverlayMenu(IRenderable menu) {
        overlayMenus.add(menu);
    }
    public void removeOverlayMenu(IRenderable menu) {
        overlayMenus.remove(menu);
    }
    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Game.screens().display("ingameMenu");
        }
    }
    public void prepare() {
        super.prepare();
        Game.audio().stopMusic();
        Game.audio().playMusic(Resources.sounds().get("mainmenu.mp3"));

        // Player.getInstance().changeMoney(-100);
        Input.keyboard().onKeyPressed(this);
    }

    @Override
    public void suspend() {
        super.suspend();
        Input.keyboard().removeKeyPressedListener(this);
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