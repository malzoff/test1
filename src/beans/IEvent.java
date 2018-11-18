package beans;

import java.util.List;

public interface IEvent {

    boolean attack(GameObject actor, GameObject target);

    boolean aoeAttack(GameObject actor, List<GameObject> target);

    boolean raidStart();

    boolean raidEnd();

    boolean raidWin();

    boolean raidDefeate();

}
