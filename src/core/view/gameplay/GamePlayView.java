package core.view.gameplay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.view.ResourceManager;
import core.model.gameplay.Enemy;
import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.Wall;

/*
* Renders game play game state
* */
public class GamePlayView {

    private List<GameObject> gameObjects;
    private List<GameObjectView> gameObjectViews;
    private Hero hero;
    private Camera camera;
    private ResourceManager resourceManager;

    public GamePlayView(GameContainer gc, List<GameObject> gameObjects, Hero hero, ResourceManager resourceManager)
            throws SlickException {
        this.resourceManager = resourceManager;

        this.gameObjects = gameObjects;

        this.hero = hero;

        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Enemy.class) {
                gameObjectViews.add(new EnemyView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Hero.class) {
                gameObjectViews.add(new HeroView(gameObject, resourceManager));
            }
        }

        camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(gc.getWidth(), gc.getHeight(), hero.getX(), hero.getY(), hero.getDirection());

        updateViews();

        for (GameObjectView gameObjectView : gameObjectViews) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());
        }
    }

    private void updateViews() throws SlickException {
        for (Iterator<GameObjectView> it = gameObjectViews.iterator(); it.hasNext();) {
            GameObjectView gameObjectView = it.next();
            boolean found = false;
            for (GameObject gameObject : gameObjects) {
                if (gameObjectView.gameObject == gameObject) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

        // looking for new gameObjects
        for (GameObject gameObject : gameObjects) {
            boolean found = false;
            for (GameObjectView gameObjectView : gameObjectViews) {
                if (gameObject == gameObjectView.gameObject) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                if (gameObject.getClass() == Wall.class) {
                    gameObjectViews.add(new WallView(gameObject, resourceManager));
                } else if (gameObject.getClass() == Enemy.class) {
                    gameObjectViews.add(new EnemyView(gameObject, resourceManager));
                } else if (gameObject.getClass() == Hero.class) {
                    gameObjectViews.add(new HeroView(gameObject, resourceManager));
                }
            }
        }
    }

}