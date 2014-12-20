package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class SkeletonMageView extends UnitView {

    public SkeletonMageView(GameObject skeletonMage) {
        super(skeletonMage);
        animation = ResourceManager.getInstance().getAnimation("skeleton_mage");
    }

}