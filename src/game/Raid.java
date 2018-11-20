package game;

import beans.Boss;
import beans.IAction;
import beans.Player;
import util.Time;

import java.util.Date;
import java.util.List;

import static util.Time.getNow;

public class Raid implements IAction {
    private final List<Player> players;
    private final Boss boss;
    private Thread raidThread;

    public Raid(List<Player> players, Boss boss, final long durationMillis) {
        this.players = players;
        this.boss = boss;

        raidThread = new Thread(() -> {
            try {
                long endTime = getNow() + durationMillis;
                boss.selectNewTarget(players);
                boss.resetTimers(getNow());
                while (Time.timeLeftBefore(endTime) > 0 && players.size() > 0 && boss.getHp() > 0) {
                    long loopStart = getNow();
                    bossAttack();
                    dropKilledPlayers();
                    playersAttack();
                    Thread.sleep(Time.SECOND_MILLIS - (getNow() - loopStart));
                }
                raidEnd();
            } catch (InterruptedException e) {
                //
            }
        });
    }

    private void dropKilledPlayers() {
        int size = players.size();
        if (size > 0) {
            players.removeIf(player -> player.getHp() <= 0);
            if (players.size() < size) {
                System.err.println(players.size() - size + "player(s) killed");
            }
        }
    }

    @Override
    public void bossAttack() {
        if ((getNow() - boss.getLastAOENukeTime()) / (2 * Time.MINUTE_MILLIS) > 0) {
            System.err.println("Boss uses AOE NUKE");
            boss.doAOENukeAttack(players.subList(0, 99));
        } else if (Time.timePassedFrom(boss.getLastAOETime()) / Time.MINUTE_MILLIS > 0) {
            System.err.println("Boss uses AOE");
            boss.doAOEAttack(players);
        } else if (Time.timePassedFrom(boss.getLastSimpleAttackTime()) / Time.SECOND_MILLIS > 0) {
            if (boss.getCurrentTarget() == null || boss.getCurrentTarget().getHp() < 0) {
                boss.selectNewTarget(players);
            }
            System.err.println("Boss hits target " + players.indexOf(boss.getCurrentTarget()));
            boss.doSimpleAttack();
        }
    }

    @Override
    public void playersAttack() {
        int size = players.size();
        if (size > 0) {
            for (int i = 0; i < size && boss.getHp() > 0; i++) {
                players.get(i).doSimpleAttack(boss);
            }
        }
    }

    @Override
    public void raidStart() {
        System.err.println("raid started at " + new Date(getNow()));
        raidThread.start();
    }

    @Override
    public void raidEnd() throws InterruptedException {
        System.err.print("Raid ended by ");
        if (boss.getHp() > 0 || players.size() == 0) {
            raidFailed();
        } else {
            raidSuccess();
        }
    }

    private void raidSuccess() {
        System.err.println("success!");
    }

    private void raidFailed() {
        System.err.println("fail!");
    }
}
