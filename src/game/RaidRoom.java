package game;

import beans.Boss;
import beans.Player;
import util.Time;

import java.util.List;

import static util.Time.getNow;

public class RaidRoom {
    private final List<Player> players;
    private final Boss boss;
    private Thread raidThread;

    public RaidRoom(List<Player> players, Boss boss, final long durationMillis) {
        this.players = players;
        this.boss = boss;

        raidThread = new Thread(() -> {
            long endTime = getNow() + durationMillis;
            boss.selectNewTarget(players);
            boss.resetTimers(getNow());
            System.err.println("timeLeft:" + Time.timeLeftBefore(endTime) + " pCount:" + players.size() + " bossHp:" + boss.getHp());
            while (Time.timeLeftBefore(endTime) > 0 && players.size() > 0 && boss.getHp() > 0) {
                long loopStart = getNow();
                bossAttack();
                dropKilledPlayers();
                playersAttack();
                System.err.println("timeLeft:" + Time.timeLeftBefore(endTime) + " pCount:" + players.size() + " bossHp:" + boss.getHp());
                try {
                    Thread.sleep(Time.SECOND_MILLIS - (getNow() - loopStart));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            raidEnd();
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

    private void bossAttack() {
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

    private void playersAttack() {
        int size = players.size();
        int attacksCnt = 0;
        if (size > 0) {
            for (int i = 0; i < size && boss.getHp() > 0; i++) {
                players.get(i).doSimpleAttack(boss);
                System.err.println("player " + i + " hits boss by " + players.get(i).getDps() + "; boss hp left:" + boss.getHp());
            }
        }
        System.err.println("attacks:" + attacksCnt);
    }

    public void raidStart() {
        System.err.println("raid started at " + getNow());
        raidThread.start();
    }

    private void raidEnd() {
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
