package beans;

import java.util.List;

import static game.Game.RANDOM;

public class Boss extends GameObject {

    private float aoeDamage;
    private float aoeNukeDamage;
    private Player currentTarget = null;
    private long lastAOETime;
    private long lastAOENukeTime;
    private long lastSimpleAttackTime;

    public Boss(float hp, float dps, float aoeDamage, float aoeNukeDamage) {
        super(dps, hp);
        this.aoeDamage = aoeDamage;
        this.aoeNukeDamage = aoeNukeDamage;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }

    public float getAoeNukeDamage() {
        return aoeNukeDamage;
    }

    public Player getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Player currentTarget) {
        this.currentTarget = currentTarget;
    }

    public void doSimpleAttack() {
            doSimpleAttack(currentTarget, dps);
            lastSimpleAttackTime = System.currentTimeMillis();
    }

    private void doSimpleAttack(GameObject target, float damage) {
        if (target != null && target.hp > 0 && damage > 0) {
            target.hp -= damage;
        }
    }

    private void doAOEAttack(List<? extends GameObject> targets, float damage) {
        for (GameObject target: targets) {
            doSimpleAttack(target, damage);
        }
    }

    public Player selectNewTarget(List<Player> targets) {
        int size = targets.size();
        if (size == 0){
            setCurrentTarget(null);
        } else if (size == 1) {
            setCurrentTarget(targets.get(0));
        } else {
            setCurrentTarget(targets.get(RANDOM.nextInt(targets.size())));
        }
        return currentTarget;
    }

    public long getLastAOENukeTime() {
        return lastAOENukeTime;
    }

    public long getLastAOETime() {
        return lastAOETime;
    }

    public long getLastSimpleAttackTime() {
        return lastSimpleAttackTime;
    }

    public void doAOEAttack(List<Player> players) {
        doAOEAttack(players, aoeDamage);
        lastAOETime = System.currentTimeMillis();
    }

    public void doAOENukeAttack(List<Player> players) {
        doAOEAttack(players, aoeNukeDamage);
        lastAOENukeTime = System.currentTimeMillis();
    }

    public void resetTimers(long time) {
        lastSimpleAttackTime = time;
        lastAOETime = time;
        lastAOENukeTime = time;
    }
}
