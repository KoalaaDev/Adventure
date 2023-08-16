package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleScreen extends Screen {

    // The component representing the "Start Game" button
    private Menu titleMenu;

    // Constructor
    public TitleScreen() {
        super("title"); // Set a unique identifier for this screen
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();

        Game.window().getRenderComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Display the main menu screen when the screen is clicked
                Game.screens().display("menu");
            }
        });

        // Load and initialize the background image
        ImageComponent backgroundImage = new ImageComponent(0, 0, Resources.images().get("images/Screenshot 2023-08-07 at 22.05.28.png"));
        backgroundImage.setWidth(Game.window().getWidth()-50 );
        backgroundImage.setHeight(Game.window().getHeight()+600);
        getComponents().add(backgroundImage);

        // Initialize the title menu here if needed
        // titleMenu = ...
    }


}


