package com.litiengine.Adventure.screens;

import com.litiengine.Adventure.entities.Wizard;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CharacterSelectScreen extends Screen {

    private ImageComponent bkgr;
    private Menu characterMenu;

    public CharacterSelectScreen() {
        super("CharacterScreen");
    }

    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        final BufferedImage buttonImg = Imaging.scale(Resources.images().get("images/menu_item.png"), .5f);
        final Spritesheet button = new Spritesheet(buttonImg, "images/menu_item.png", buttonImg.getWidth(), buttonImg.getHeight());
        final String[] characters = { "Wizard", "Knight" };
        bkgr = new ImageComponent(0, 0, Resources.images().get("images/CharacterMenu.jpg"));

        double menuY = (Game.window().getHeight() + 500 - buttonImg.getHeight() * characters.length) / 2d;
        characterMenu = new Menu((Game.window().getWidth()  - buttonImg.getWidth()) / 2d, menuY, buttonImg.getWidth(), buttonImg.getHeight() * characters.length,
                button, characters);

        characterMenu.onChange(index -> {
            if (index == 0) {
                suspend();
               // Wizard wizard = new Wizard();
               // Game.world().environment().add(wizard);
                Game.screens().display("INGAME-SCREEN");
            }
            // Add conditions for other character options if needed
        });

        getComponents().add(bkgr);
        getComponents().add(characterMenu);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        // Render the main title "Select Character"
        Font titleFont = new Font("Arial", Font.BOLD, 36);
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        String title = "Select Character";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        int titleX = (Game.window().getWidth() - titleWidth) / 2;
        int titleY = 100;
        g.drawString(title, titleX, titleY);
    }
}
