package teastapp.raid.beans;

import java.util.List;

import static teastapp.raid.Game.RANDOM;
import static teastapp.raid.util.Time.getNow;

public class Boss {

    private float hp;
    private float dps;
    private float aoeDamage;
    private float aoeNukeDamage;
    private Player currentTarget = null;
    private long lastAOETime;
    private long lastAOENukeTime;

    public Boss(float hp, float dps, float aoeDamage, float aoeNukeDamage) {
        this.hp = hp;
        this.dps = dps;
        this.aoeDamage = aoeDamage;
        this.aoeNukeDamage = aoeNukeDamage;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getDps() {
        return dps;
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

    private void doSimpleAttack(Player target, float damage) {
        if (target != null && target.getHp() > 0 && damage > 0) {
            target.setHp(target.getHp() - damage);
        }
    }

    private void doAOEAttack(List<Player> targets, float damage) {
        for (Player target: targets) {
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
        doAOEAttack(players, getAoeDamage());
        lastAOETime = getNow();
    }

    public void doAOENukeAttack(List<Player> players) {
        doAOEAttack(players, getAoeNukeDamage());
        lastAOENukeTime = getNow();
    }

    public void resetTimers(long time) {
        lastAOETime = time;
        lastAOENukeTime = time;
    }
}
