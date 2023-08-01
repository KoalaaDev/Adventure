package com.litiengine.Adventure.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.resources.Resources;

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


    }














    }

