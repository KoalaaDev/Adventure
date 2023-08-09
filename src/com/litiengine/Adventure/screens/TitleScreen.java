package com.litiengine.Adventure.screens;


import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class TitleScreen extends Screen {

private ImageComponent startGame;

public TitleScreen(){
    super("title");
}

@Override
    public void render(Graphics2D g){

    super.render(g);
    //g.drawImage(Resources.images().get("images/map.png"),0,0,Game.window().getWidth(), Game.window().getHeight(),null);
    startGame.render(g);
}

    @Override protected void initializeComponents() {
        super.initializeComponents();
        BufferedImage button = Resources.images().get("images/Screenshot 2023-08-07 at 22.05.28.png");
        startGame = new ImageComponent((Game.window().getWidth() + 100) , (Game.window().getHeight() - 100 ) , button);
        startGame.setText("Start Game");
        startGame.onClicked(event -> {
            suspend();
            Game.screens().display("menu");
        });
        startGame.setHoverSound(Resources.sounds().get("audio/click.mp3"));
        getComponents().add(startGame);
    }
}


