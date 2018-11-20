import beans.Boss;
import beans.Player;
import beans.Raid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private static Random RANDOM = new Random();
    private static int PLAYERS_COUNT = 10000;
    private static long RAID_DURATION_MILLIS = 10000;

    public static void main(String[] args) {
        createRaidRoom(
                waitPlayers(PLAYERS_COUNT)
                , createBoss()
                , RAID_DURATION_MILLIS
        );
    }

    private static void createRaidRoom(List<Player> players, Boss boss, long raidDuration) {
        players.sort((o1, o2) -> o2.getDps() - o1.getDps() > 0 ? 1 : o1.getDps() - o2.getDps() < 0 ? -1 : 0);
        Raid raid = new Raid(players, boss, raidDuration) {

        };
    }

    private static List<Player> waitPlayers(int playersCount) {
        List<Player> players = new ArrayList<>(playersCount);
        for (int i = 0; i < playersCount; i++) {
            players.add(new Player(5f + RANDOM.nextFloat() * 15f, 1 + RANDOM.nextFloat() * 20f));
        }
        return players;
    }

    private static Boss createBoss() {
        return new Boss(1000000f, 10f, 50f, 500f);
    }
}
