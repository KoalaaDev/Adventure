package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.resources.SpritesheetResource;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainmenuScreen extends Screen{

    private ImageComponent bkgr;
    private Menu menu;

    public  MainmenuScreen(){
        super("menu");

    }
    @Override
    public void prepare(){
        super.prepare();
        Game.audio().playMusic(Resources.sounds().get("audio/looping-overcome-atmanan.mp3"));
    }

    @Override
    protected void initializeComponents(){
        super.initializeComponents();
        final BufferedImage buttonImg = Imaging.scale(Resources.images().get("images/menu_item.png"), .5f);
        final Spritesheet button = new Spritesheet(buttonImg, "menu_item.png", buttonImg.getWidth(), buttonImg.getHeight());
        final String[] items = { "Neww Game", "Quit" };
        bkgr = new ImageComponent(0, 0, Resources.images().get("image/menu.png"));
        menu = new Menu((Game.window().getWidth() - buttonImg.getWidth()) / 2d,
                (Game.window().getHeight() - buttonImg.getHeight() * items.length) / 2d, buttonImg.getWidth(), buttonImg.getHeight() * items.length,
                button, items);

        menu.onChange(index -> {
            if(index == 0){
                Game.world().loadEnvironment("map1");
                Game.start();
            }
            if(index == 1){
                System.exit(0);
            }
        });
        for (ImageComponent cell : menu.getCellComponents()) {


        }
        getComponents().add(bkgr);
        getComponents().add(menu);



    }

}

