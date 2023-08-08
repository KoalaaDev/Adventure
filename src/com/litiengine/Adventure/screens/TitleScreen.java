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
    g.drawImage(Resources.images().get("images/title.jpg.png"),0,0,Game.window().getWidth(), Game.window().getHeight(),null);
    startGame.render(g);
}

    @Override protected void initializeComponents() {
        super.initializeComponents();
        BufferedImage button = Resources.images().get("images/title.jpg.png");
        startGame = new ImageComponent((Game.window().getWidth()) , (Game.window().getHeight()) , button);
        startGame.setText("Start Game");
        startGame.onClicked(event -> {
            suspend();
            Game.screens().display("menu");
        });
        //startGame.setHoverSound(Resources.sounds().get("mouse-over.wav"));
        getComponents().add(startGame);
    }
}


