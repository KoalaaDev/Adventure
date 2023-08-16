package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinScreen extends Screen {

    private ImageComponent backgroundImage;

    public WinScreen() {
        super("WinningScreen");
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
        backgroundImage = new ImageComponent(0, 0, Resources.images().get("images/wp6030002-desktop-gamers-wallpapers.jpg"));
        getComponents().add(backgroundImage);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render the background image
        backgroundImage.render(g);

        // Render the winning message
        Font font = new Font("Arial", Font.BOLD, 36);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String message = "Congratulations, you win!";
        int messageWidth = g.getFontMetrics().stringWidth(message);
        int messageX = (Game.window().getWidth() - messageWidth) / 2;
        int messageY = Game.window().getHeight() / 2;
        g.drawString(message, messageX, messageY);
    }
}
