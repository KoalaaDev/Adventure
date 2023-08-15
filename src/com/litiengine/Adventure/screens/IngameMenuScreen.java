package com.litiengine.Adventure.screens;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;


public class IngameMenuScreen extends Screen  {


    private Menu menu;
    private ImageComponent bkg;
    public IngameMenuScreen() {
        super("ingameMenu");
    }



    @Override
    public void prepare() {
        super.prepare();


    }

    @Override
    public void suspend() {
        super.suspend();

    }

    @Override protected void initializeComponents() {
        super.initializeComponents();

        String[] items = {"Resume","Main menu"};

        ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu.png"));

        final BufferedImage buttonImg = Imaging.scale(Resources.images().get("images/menu_item.png"), .5f);

        Spritesheet button = new Spritesheet(buttonImg, "menu_item", buttonImg.getWidth(), buttonImg.getHeight());

        menu = new Menu((Game.window().getWidth() - buttonImg.getWidth()) / 2d, (Game.window().getHeight() + 500 - buttonImg.getHeight() * items.length) / 2d,
                buttonImg.getWidth(), buttonImg.getHeight() * items.length, button, items);
        bkg = new ImageComponent(0, 0, Resources.images().get("images/menu.png"));
        menu.onChange(index -> {
            if (index == 0) {
                suspend();
                Game.screens().display("INGAME-SCREEN");
            }
            if (index == 1) {
                suspend();
                Game.screens().display("menu");
            }
        });
        for (ImageComponent cell : menu.getCellComponents()) {
            cell.setHoverSound(Resources.sounds().get("audio/sounds_mouse-over.wav"));
        }
        getComponents().add(bkgr);
        getComponents().add(menu);
    }
}