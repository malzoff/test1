package game;

import beans.Boss;
import beans.IEvent;
import beans.Player;
import util.Time;

import java.util.List;

import static util.Time.getNow;

public class Raid implements Runnable, IEvent {
    private List<Player> players;
    private Boss boss;
    private long durationMillis;

    public Raid(List<Player> players, Boss boss, long durationMillis) {
        this.players = players;
        this.boss = boss;
        this.durationMillis = durationMillis;
    }

    @Override
    public void run() {
        long endTime = getNow() + durationMillis;
        boss.selectNewTarget(players);
        boss.resetTimers(getNow());
        while (Time.timeLeftBefore(endTime) > 0) {

        }
    }

    @Override
    public boolean onBossAttack() {
        if ((getNow() - boss.getLastAOENukeTime()) / (2 * Time.MINUTE_MILLIS) > 0) {
            boss.doAOENukeAttack(players.subList(0, 99));
        } else if (Time.timePassedFrom(boss.getLastAOETime()) / Time.MINUTE_MILLIS > 0) {
            boss.doAOEAttack(players);
        } else if (Time.timePassedFrom(boss.getLastSimpleAttackTime()) / Time.SECOND_MILLIS > 0) {
            if (boss.getCurrentTarget() == null || boss.getCurrentTarget().getHp() < 0) {
                boss.selectNewTarget(players);
            }
            boss.doSimpleAttack();
        }
        return false;
    }

    @Override
    public boolean onPlayerAttack() {
        return false;
    }

    @Override
    public boolean onRaidStart() {
        return false;
    }

    @Override
    public boolean onRaidEnd() {
        return false;
    }

    @Override
    public boolean onRaidSuccess() {
        return false;
    }

    @Override
    public boolean onRaidFailed() {
        return false;
    }
}
