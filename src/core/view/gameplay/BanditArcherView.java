package core.view.gameplay;

import core.model.gameplay.units.BanditArcher;
import core.model.gameplay.units.Hero;
import org.newdawn.slick.*;

import core.resource_manager.ResourceManager;
import core.model.gameplay.units.GameObjectSolid;

public class BanditArcherView extends GameObjectView {

    public BanditArcherView(GameObjectSolid enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("banditArcher");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        BanditArcher banditArcher = (BanditArcher) gameObjectSolid;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                (float) (viewDegreeAngle - banditArcher.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(banditArcher.getX() - viewX), (int)(banditArcher.getY() - viewY) - 50, 60, 8, 
                banditArcher.getAttribute().getHP().getCurrent(),
                banditArcher.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, (int) (banditArcher.getX() - viewX), (int) (banditArcher.getY() - viewY) - 38, 60, 8,
                banditArcher.getAttribute().getMP().getCurrent(),
                banditArcher.getAttribute().getMP().getMaximum(), Color.blue);
        /*g.drawString(String.valueOf((int) banditArcher.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getHP().getCurrent()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getHP().getMaximum()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getMP().getCurrent()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMP().getMaximum()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));*/
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                - (float) (viewDegreeAngle - banditArcher.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}