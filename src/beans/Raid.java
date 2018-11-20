package beans;

import util.Time;

import java.util.List;

public class Raid implements Runnable, IEvent {
    private final List<Player> players;
    private final Boss boss;
    private final long durationMillis;

    public Raid(List<Player> players, Boss boss, long durationMillis) {
        this.players = players;
        this.boss = boss;
        this.durationMillis = durationMillis;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + durationMillis;
        while (Time.timeLeftBefore(endTime) > 0) {
            long currentIntervalStart = System.currentTimeMillis();

        }
    }

    @Override
    public boolean onBossAttack() {
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
