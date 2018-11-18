package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Raid {

    static Random RANDOM = new Random();
    long duration;

    public Raid(int playersCount, long durationMillis) {
        List<Player> players = new ArrayList<>(10000);

        for (int i = 0; i < playersCount; i++) {
            addPlayerTo(players);
        }
        Boss boss = new Boss(1000000f, 10f, 50f, 500f);
    }

    private static void addPlayerTo(List<Player> playerList) {
        Player player = new Player(5f + RANDOM.nextFloat() * 15f, 1 + RANDOM.nextInt(10));
        int size = playerList.size();
        if (size == 0) {
            playerList.add(player);
        } else {
            System.err.println("dps=" + (int) player.dps);
            binaryPut(playerList, player);
        }
    }

    private static void binaryPut(List<Player> playerList, Player player) {
        int firstIndex = 0;
        int lastIndex = playerList.size() - 1;
        int index = lastIndex / 2;
        int newDps = (int) player.dps;
        while (lastIndex - firstIndex >= 0) {
            int indexDps = (int) playerList.get(index).dps;
            if (newDps > indexDps) {
                System.err.print(newDps + ">" + indexDps);
                if (lastIndex - firstIndex == 0) {
                    System.err.println("!");
                    playerList.add(index, player);
                    break;
                }
                lastIndex = index - 1;
                index = index / 2;
            } else if (newDps < indexDps) {
                System.err.print(newDps + "<" + indexDps);
                if (lastIndex - firstIndex == 0) {
                    System.err.println("!!");
                    playerList.add(index + 1, player);
                    break;
                }
                firstIndex = index + 1;
                index = firstIndex + (lastIndex - index) / 2;
            } else if (newDps == indexDps) {
                System.err.println(newDps + "=" + indexDps);
                playerList.add(index + 1, player);
                break;
            } else {
                System.err.println("!!!!");
            }
            System.err.println(" fI=" + firstIndex + "; I=" + index + "; lI=" + lastIndex);
        }
        System.err.println("list:" + playerList.toString() + "; size=" + playerList.size());
    }

    public static void main(String[] args) {
        new Raid(20, 10000);
    }
}
