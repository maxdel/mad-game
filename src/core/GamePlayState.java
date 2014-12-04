package core;

import core.model.gameplay.resource_manager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import core.controller.gameplay.GamePlayController;
import core.model.gameplay.World;
import core.view.gameplay.GamePlayView;

/*
* Execute game play
* */
public class GamePlayState extends BasicGameState {

    private static GamePlayState instance;

    private final GameState STATE_ID = GameState.GAMEPLAY;
    private World world;
    private GamePlayView gamePlayView;
    private GamePlayController gamePlayController;

    private GamePlayState() {

    }

    public static GamePlayState getInstance() {
        if (instance == null) {
            instance = new GamePlayState();
        }
        return instance;
    }

    @Override
    public int getID() {
        return STATE_ID.getValue();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        gc.setMouseCursor("res/emptyImage.png", 0, 0);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        gamePlayView.render(gc, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        gamePlayController.update(gc, game);
        world.update(delta);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().load(STATE_ID);

        world = World.getInstance();
        gamePlayView = new GamePlayView(gc, world.getGameObjectSolids(), world.getHero(), world.getLootList(), ResourceManager.getInstance(), world.getTiledMap());
        gamePlayController = new GamePlayController(world, gamePlayView);
    }

    @Override
    public void leave(GameContainer gc, StateBasedGame game) throws SlickException {
        ResourceManager.getInstance().unload();

        World.deleteInstance();
        world = null;
        gamePlayView = null;
        gamePlayController = null;
        System.gc();
    }

}