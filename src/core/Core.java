package core;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.controller.Controller;
import core.model.World;
import core.view.Renderer;

public class Core extends BasicGame {

    World world;
    Renderer renderer;
    Controller controller;

    public Core(String gameName) {
        super(gameName);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        gc.setVSync(true);

        world = new World();
        renderer = new Renderer(world.getGameObjects());
        controller = new Controller(world, renderer);
    }

    @Override
    public void update(GameContainer gc, final int delta) throws SlickException {
        controller.update(gc, delta);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        renderer.render(g);
    }

}