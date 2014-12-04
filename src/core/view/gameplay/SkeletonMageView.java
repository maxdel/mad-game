package core.view.gameplay;

import core.model.gameplay.units.GameObjectSolid;
import core.model.gameplay.units.Hero;
import core.model.gameplay.units.SkeletonMage;
import org.newdawn.slick.*;

import core.model.gameplay.resource_manager.ResourceManager;

public class SkeletonMageView extends GameObjectView {

    public SkeletonMageView(GameObjectSolid enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("skeletonMage");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        SkeletonMage skeletonMage = (SkeletonMage) gameObjectSolid;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                (float) (viewDegreeAngle - skeletonMage.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(skeletonMage.getX() - viewX), (int)(skeletonMage.getY() - viewY) - 50, 60, 8, skeletonMage.getAttribute().getCurrentHP(),
                skeletonMage.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, (int) (skeletonMage.getX() - viewX), (int) (skeletonMage.getY() - viewY) - 38, 60, 8, skeletonMage.getAttribute().getCurrentMP(),
                skeletonMage.getAttribute().getMaximumMP(), Color.blue);
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                - (float) (viewDegreeAngle - skeletonMage.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}