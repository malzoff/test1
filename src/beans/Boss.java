package beans;

import java.util.List;

import static game.Game.RANDOM;
import static util.Time.getNow;

public class Boss extends GameObject {

    private float aoeDamage;
    private float aoeNukeDamage;
    private Player currentTarget = null;
    private long lastAOETime;
    private long lastAOENukeTime;

    public Boss(float hp, float dps, float aoeDamage, float aoeNukeDamage) {
        super(hp, dps);
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
            doSimpleAttack(currentTarget, getDps());
    }

    private void doSimpleAttack(GameObject target, float damage) {
        if (target != null && target.getHp() > 0 && damage > 0) {
            target.setHp(target.getHp() - damage);
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

    public void doAOEAttack(List<Player> players) {
        doAOEAttack(players, aoeDamage);
        lastAOETime = getNow();
    }

    public void doAOENukeAttack(List<Player> players) {
        doAOEAttack(players, aoeNukeDamage);
        lastAOENukeTime = getNow();
    }

    public void resetTimers(long time) {
        lastAOETime = time;
        lastAOENukeTime = time;
    }
}
