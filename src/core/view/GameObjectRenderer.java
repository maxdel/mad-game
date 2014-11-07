package core.view;

import core.model.GameObject;
import org.newdawn.slick.Graphics;

public abstract class GameObjectRenderer {

    protected GameObject gameObject;

    public GameObjectRenderer(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void render(Graphics g);

    public abstract void render(Graphics g, final double offsetX, final double offsetY, final double direction);

}