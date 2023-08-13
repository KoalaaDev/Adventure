package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The TitleScreen class represents the title screen of the game.
 * This screen typically appears when the game is launched and provides options for starting the game or accessing other features.
 */
public class TitleScreen extends Screen {

    // The component representing the "Start Game" button
    private ImageComponent bkgr;
    private Menu TitleMenu;
    // Constructor
    public TitleScreen() {
        super("title"); // Set a unique identifier for this screen
    }

    @Override
    public void prepare(){
        super.prepare();

    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents(); // Initialize components of the base screen
        final BufferedImage buttonImg = Imaging.scale(Resources.images().get("images/menu_item.png"), .3f);
        final Spritesheet button = new Spritesheet(buttonImg, "images/menu_item.png", buttonImg.getWidth(), buttonImg.getHeight());
        final String[] items = {"Start Game"};
        // Load the button image and create the "Start Game" button component
        bkgr = new ImageComponent(-50, 50, Resources.images().get("images/trial2.png"));

        TitleMenu = new Menu((Game.window().getWidth()  - buttonImg.getWidth()) / 2d,
                (Game.window().getHeight() + 1350  - buttonImg.getHeight() * items.length) / 2d, buttonImg.getWidth(), buttonImg.getHeight() * items.length,
                button, items);
        // Add a click event handler to transition to the "menu" screen when the button is clicked
        TitleMenu.onChange(index -> {
            if (index == 0) {
                suspend();
                Game.screens().display("menu");
            }
        });
        // Set a hover sound for the button
        TitleMenu.setHoverSound(Resources.sounds().get("audio/click.mp3"));

        // Add the "Start Game" button component to the screen's components
        getComponents().add(bkgr);
        getComponents().add(TitleMenu);

    }
}


