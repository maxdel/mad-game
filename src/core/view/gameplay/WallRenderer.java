package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.model.gameplay.GameObject;

public class WallRenderer extends GameObjectRenderer {

    Image wallImage;

    public WallRenderer(GameObject wall, Image wallImage) {
        super(wall);
        this.wallImage = wallImage;
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final double viewDirection,
                       final int viewWidth, final int viewHeight) {
        float viewDegreeAngle = (float) (viewDirection / Math.PI * 180);

        //Rotate around view center to set position on the View
        g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
        //Rotate around gameObject coordinates to set direction of gameObject
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float)(gameObject.getDirection() / Math.PI * 180));
        // Coordinates to draw image according to position on the View
        wallImage.draw((float) (gameObject.getX() - viewX - wallImage.getWidth() / 2),
                (float) (gameObject.getY() - viewY - wallImage.getHeight() / 2));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                -(float) (gameObject.getDirection() / Math.PI * 180));
        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf(gameObject.getX()) + ";" + String.valueOf(gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - viewDegreeAngle);
        // ----- END -----
        g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);
    }

}