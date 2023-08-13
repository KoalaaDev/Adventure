package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The TitleScreen class represents the title screen of the game.
 * This screen typically appears when the game is launched and provides options for starting the game or accessing other features.
 */
public class TitleScreen extends Screen {

    // The component representing the "Start Game" button
    private ImageComponent startGame;

    // Constructor
    public TitleScreen() {
        super("title"); // Set a unique identifier for this screen
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g); // Render the base screen
        startGame.render(g); // Render the "Start Game" button component
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents(); // Initialize components of the base screen

        // Load the button image and create the "Start Game" button component
        BufferedImage button = Resources.images().get("images/Screenshot 2023-08-07 at 22.05.28.png");
        startGame = new ImageComponent(Game.window().getWidth() + 100, Game.window().getHeight() - 100, button);
        startGame.setText("Start Game");

        // Add a click event handler to transition to the "menu" screen when the button is clicked
        startGame.onClicked(event -> {
            suspend();
            Game.screens().display("menu");
        });

        // Set a hover sound for the button
        startGame.setHoverSound(Resources.sounds().get("audio/click.mp3"));

        // Add the "Start Game" button component to the screen's components
        getComponents().add(startGame);
    }
}


