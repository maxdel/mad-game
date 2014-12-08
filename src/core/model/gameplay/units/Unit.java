package core.model.gameplay.units;

import core.model.gameplay.*;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class Unit extends GameObjectSolid {

    private double relativeDirection;
    private GameObjectState currentState;
    protected ItemRecord usingItem;
    protected Inventory inventory;
    private Attribute attribute;

    protected List<Skill> skillList;
    protected Skill castingSkill;

    protected int useItemCounter;
    protected final int USE_ITEM_TIME = 400;

    public Unit(double x, double y, double maximumSpeed) {
        super(x, y, 0);

        this.relativeDirection = 0;
        this.currentState = GameObjectState.STAND;
        inventory = new Inventory(this);
        attribute = new Attribute(100, 50, maximumSpeed);
        skillList = new ArrayList<Skill>();
    }

    @Override
    public void update(int delta) {
        updateUsingItem(delta);
        updateCastingSkill(delta);
        updateSkills(delta);
        motionInCollisionProcessing(delta);
        updateDeath();
    }

    public void startUseItem() {//todo: ref if clause
        if (inventory.getSelectedRecord() != null && getCurrentState() != GameObjectState.PICK_ITEM &&
                getCurrentState() != GameObjectState.DROP_ITEM &&
                getCurrentState() != GameObjectState.USE_ITEM) {
            setCurrentState(GameObjectState.USE_ITEM);
            getAttribute().setCurrentSpeed(0);
            usingItem = inventory.getSelectedRecord();
            useItemCounter = USE_ITEM_TIME;
        }
    }

    private void useItem(){
        inventory.useItem(usingItem);
        setCurrentState(GameObjectState.STAND);
        usingItem = null;
    }

    public void startCastSkill(int skillIndex) {
        if (skillList.get(skillIndex) != null && skillList.get(skillIndex).startCast()) {
            castingSkill = skillList.get(skillIndex);
            setCurrentState(GameObjectState.CAST);
            getAttribute().setCurrentSpeed(0);
        }
    }

    protected void applyCurrentSkill() {
        castingSkill.apply();
        castingSkill.setAlreadyApplied(true);
    }

    private void updateDeath() {
        if (attribute.hpAreEnded()) {
            die();
        }
    }

    private void die() {
        World.getInstance().getToDeleteList().add(this);
        onDelete();
    }

    private void motionInCollisionProcessing(int delta) {
        double length, direction, lengthDirX, lengthDirY;
        length = attribute.getCurrentSpeed() * delta;
        direction = getDirection() + getRelativeDirection();

        lengthDirX = lengthDirX(direction, length);
        lengthDirY = lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            setX(getX() + lengthDirX);
            setY(getY() + lengthDirY);
        } else {
            double stepAngle = 10 * Math.PI / 180;
            double altDirection = direction;
            for (int i = 0; Math.abs(altDirection - direction) < Math.PI / 2; ++i) {
                altDirection += i * stepAngle * (i % 2 == 0 ? 1 : -1);
                lengthDirX = lengthDirX(altDirection, length * Math.cos(altDirection - direction));
                lengthDirY = lengthDirY(altDirection, length * Math.cos(altDirection - direction));
                if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
                    setX(getX() + lengthDirX);
                    setY(getY() + lengthDirY);
                    break;
                }
            }
        }
    }

    private void updateUsingItem(int delta) {
        if (useItemCounter > 0) {
            useItemCounter -= delta;
            if (useItemCounter <= 0) {
                useItemCounter = 0;
                useItem();
            }
        }
    }

    private void updateCastingSkill(int delta) {
        if (castingSkill != null && castingSkill.isCastingСontinues()) {
            castingSkill.tickCastingTime(delta);

            if (castingSkill.isTimeToApply()) {
                // post apply state code hear
                applyCurrentSkill();
            }

            if (castingSkill.isCastingFinished()) {
                stopCasting();
            }
        }
    }

    private void stopCasting() {
        setCurrentState(GameObjectState.STAND);
        castingSkill.stopCasting();
        castingSkill.setAlreadyApplied(false);
        castingSkill = null;
    }

    private void updateSkills(int delta) {
        for (Skill skill : skillList) {
            skill.update(delta);
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }


    public Attribute getAttribute() {
        return attribute;
    }

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public void setRelativeDirection(double relativeDirection) {
        this.relativeDirection = relativeDirection;
    }

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameObjectState currentState) {
        this.currentState = currentState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public Skill getCastingSkill() {
        return castingSkill;
    }

    protected void onDelete() {

    }

    public ItemRecord getUsingItem() {
        return usingItem;
    }

}