package core.model.gameplay.gameobjects.ai;

import java.util.Arrays;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.BulletShot;
import core.model.gameplay.skills.SkillInstanceKind;

public class SkeletonMageAI extends BotAI {

    private enum SkeletonMageAIState implements BotAI.BotAIState {
        STAND, WALK, PURSUE, FORCE_PURSUE, ATTACK
    }

    @Override
    protected void init() {
        final int standTime = 3000;
        final int pursueDistance = 400;
        final int attackDistance = 200;
        final int forcePursueTime = 7000;
        currentState = SkeletonMageAIState.STAND;
        stateMap.put(SkeletonMageAIState.STAND, new AIState() {
            private Timer timer;
            private double previousHP;
            public void enter()           { timer = new Timer(standTime); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { owner.stand();                                                                 }
            public void update(int delta) { if (timer.update(delta)) currentState = SkeletonMageAIState.WALK;
                if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = SkeletonMageAIState.PURSUE; return; }
                if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = SkeletonMageAIState.FORCE_PURSUE; return; }
                previousHP = owner.getAttribute().getCurrentHP(); }
        });
        stateMap.put(SkeletonMageAIState.WALK, new AIState() {
            private Point target;
            private double previousHP;
            public void enter()           { target = getRandomTarget(); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { followTarget(target);                                                           }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) currentState = SkeletonMageAIState.PURSUE;
                if (getDistanceToTarget(target) < 2) { currentState = SkeletonMageAIState.STAND; return; }
                if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = SkeletonMageAIState.FORCE_PURSUE; return; }
                previousHP = owner.getAttribute().getCurrentHP();   }
        });
        stateMap.put(SkeletonMageAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter()           { isFollowing = true;                                                                  }
            public void run(int delta)    { isFollowing = followHero();         }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) currentState = SkeletonMageAIState.STAND;
                if (getDistanceToHero() < attackDistance && seeTarget(Hero.getInstance())) currentState = SkeletonMageAIState.ATTACK; }
        });
        stateMap.put(SkeletonMageAIState.FORCE_PURSUE, new AIState() {
            private Timer timer;
            private boolean isFollowing;
            public void enter()           { timer = new Timer(forcePursueTime); isFollowing = true;                                                                  }
            public void run(int delta)    { isFollowing = followHero();         }
            public void update(int delta) {
                timer.update(delta);
                if ((getDistanceToHero() >= pursueDistance && timer.isTime()) || !isFollowing) currentState = SkeletonMageAIState.STAND;
                if (getDistanceToHero() < attackDistance && seeTarget(Hero.getInstance())) currentState = SkeletonMageAIState.ATTACK; }
        });
        stateMap.put(SkeletonMageAIState.ATTACK, new AIState() {
            private boolean isAttacking;
            public void enter()           { isAttacking = true;                                                             }
            public void run(int delta)    { isAttacking = attackHeroWithFireball();                                      }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance || !isAttacking) currentState = SkeletonMageAIState.PURSUE; }
        });
    }

    private boolean attackHeroWithFireball() {
        owner.stand();
        owner.setDirection(getPredictedDirection(owner.getSkillByKind(SkillInstanceKind.SKELETON_FIREBALL)));
        if (seeTarget(Hero.getInstance())) {
            owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.STAFF, ItemInstanceKind.STRONG_STAFF));
            owner.startCastSkill(SkillInstanceKind.SKELETON_FIREBALL);
            return true;
        }
        return false;
    }

}