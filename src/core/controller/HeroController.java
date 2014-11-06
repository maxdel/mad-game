package core.controller;

import core.model.HeroManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class HeroController {

    private HeroManager heroManager;

    public HeroController(HeroManager heroManager) {
        this.heroManager = heroManager;
    }

    public void update(GameContainer gc) {
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            heroManager.move(0);
        }
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            heroManager.move(1);
        }
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            heroManager.move(2);
        }
        if (gc.getInput().isKeyDown(Input.KEY_S)) {
            heroManager.move(3);
        }

    }

}
