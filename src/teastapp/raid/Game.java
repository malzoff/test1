package teastapp.raid;

import teastapp.raid.beans.Boss;
import teastapp.raid.beans.Player;
import teastapp.raid.game.RaidRoom;
import teastapp.raid.util.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static Random RANDOM = new Random();
    private static int PLAYERS_COUNT = 10000;
    private static long RAID_DURATION_MILLIS = Time.MINUTE_MILLIS * 10;

    public static void main(String[] args) {
        createRaidRoom(
                waitPlayers(PLAYERS_COUNT)
                , createBoss()
                , RAID_DURATION_MILLIS
        );
    }

    private static void createRaidRoom(List<Player> players, Boss boss, long raidDuration) {
        players.sort((o1, o2) -> o2.getDps() - o1.getDps() > 0 ? 1 : o1.getDps() - o2.getDps() < 0 ? -1 : 0);
        new RaidRoom(players, boss, raidDuration).raidStart();
    }

    private static List<Player> waitPlayers(int playersCount) {
        List<Player> players = new ArrayList<>(playersCount);
        for (int i = 0; i < playersCount; i++) {
            players.add(new Player(10f + RANDOM.nextFloat() * 40f, 0.01f + RANDOM.nextFloat() * 0.99f));
        }
        return players;
    }

    private static Boss createBoss() {
        return new Boss(1000000f, 0.1f, 25f, 50f);
    }
}
