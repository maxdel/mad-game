package core.view.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.model.gameplay.Enemy;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.Wall;

/*
* Renders game play game state
* */
public class GamePlayRenderer {

    private static GamePlayRenderer instance;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObjectRenderer> gameObjectRenderers;
    private HeroRenderer heroRenderer;
    private View view;

    private Map<Class, Class> renderersMap;

    private GamePlayRenderer() {

    }

    public GamePlayRenderer(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        renderersMap = new HashMap<Class, Class>();
        renderersMap.put(Wall.class, WallRenderer.class);

        this.gameObjects = gameObjects;

        heroRenderer = new HeroRenderer(hero);

        gameObjectRenderers = new ArrayList<GameObjectRenderer>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectRenderers.add(new WallRenderer(gameObject, new Image("/res/Wall.png")));
            }
            else if (gameObject.getClass() == Enemy.class) {
                gameObjectRenderers.add(new EnemyRenderer(gameObject, new Image("/res/Enemy.png")));
            }
        }

        view = new View(gc.getWidth(), gc.getHeight());
    }

    // Singleton pattern method
    public static GamePlayRenderer getInstance(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        if (instance == null) {
            instance = new GamePlayRenderer(gc, gameObjects, hero);
        }
        return instance;
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        view.update(heroRenderer.getHero().getX(), heroRenderer.getHero().getY(), heroRenderer.getHero().getDirection());

        heroRenderer.render(g, view.getX(), view.getY(), view.getDirection(), view.getWidth(), view.getHeight());

        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            gameObjectRenderer.render(g, view.getX(), view.getY(), view.getDirection(), view.getWidth(), view.getHeight());
        }
    }

}