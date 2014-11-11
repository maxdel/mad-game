package core;

import core.model.menu.StartMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.menu.StartMenuController;
import core.view.menu.MenuRenderer;

/*
* Execute start menu
* */
public class StartMenuState extends BasicGameState {

    private static StartMenuState instance;

    private final int STATE_ID = 1;

    private StartMenuController startMenuController;
    private MenuRenderer menuRenderer;

    private StartMenuState() {
    }

    public static StartMenuState getInstance() {
        if (instance == null) {
            instance = new StartMenuState();
        }
        return instance;
    }

    @Override
    public int getID() {
        return STATE_ID;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics graphics) throws SlickException {
        menuRenderer.render(gc);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        startMenuController.update(gc, game);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.getInput().clearKeyPressedRecord();
        startMenuController = StartMenuController.getInstance();
        menuRenderer = new MenuRenderer(StartMenu.getInstance());
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        startMenuController = null;
        menuRenderer = null;
        System.gc();
    }
}

