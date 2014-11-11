package core.model.gameplay;

import java.util.ArrayList;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private ArrayList<GameObject> gameObjects;
    private Hero hero;
    private HeroManager heroManager;
    private ArrayList<EnemyManager> enemyManagers;

    private World() {
        gameObjects = new ArrayList<GameObject>();

        hero = (new Hero(200, 100, 0, 0.18f));

        gameObjects.add(new Wall(100, 100, Math.PI / 4, 0));
        gameObjects.add(new Wall(300, 300, 0, 0));

        Enemy enemy1 = new Enemy(300, 100, 0, 0.06f);
        Enemy enemy2 = new Enemy(300, 100, 0, 0.03f);

        gameObjects.add(enemy1);
        gameObjects.add(enemy2);

        enemyManagers = new ArrayList<EnemyManager>();
        enemyManagers.add(new EnemyManager(enemy1));
        enemyManagers.add(new EnemyManager(enemy2));

        heroManager = new HeroManager(hero);
    }

    // Singleton pattern method
    public static World getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new World();
        }
        return instance;

    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<EnemyManager> getEnemyManagers() {
        return enemyManagers;
    }

}