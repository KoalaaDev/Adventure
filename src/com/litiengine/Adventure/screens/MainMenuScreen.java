package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;
import java.awt.image.BufferedImage;

import com.litiengine.Adventure.GameManager;

public class MainMenuScreen extends Screen{

    private ImageComponent bkgr;
    private Menu menu;

    public  MainMenuScreen(){
        super("menu");

    }
    @Override
    public void prepare(){
        super.prepare();

    }

    @Override
    protected void initializeComponents(){
        super.initializeComponents();
        final BufferedImage buttonImg = Imaging.scale(Resources.images().get("images/menu_item.png"), .5f);
        final Spritesheet button = new Spritesheet(buttonImg, "images/menu_item.png", buttonImg.getWidth(), buttonImg.getHeight());
        final String[] items = { "New Game", "Quit" };
        bkgr = new ImageComponent(0, 0, Resources.images().get("images/menu.png"));
        menu = new Menu((Game.window().getWidth() - buttonImg.getWidth()) / 2d,
                (Game.window().getHeight()+500 - buttonImg.getHeight() * items.length) / 2d, buttonImg.getWidth(), buttonImg.getHeight() * items.length,
                button, items);
        menu.setHoverSound(Resources.sounds().get("audio/click.mp3"));

        menu.onChange(index -> {
            if(index == 0){
               suspend();
               Game.screens().display("INGAME-SCREEN");
                GameManager.start();
                
            }
            if(index == 1){
                System.exit(0);
            }
        });

        getComponents().add(bkgr);
        getComponents().add(menu);



    }

}
