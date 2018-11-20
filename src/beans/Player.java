package beans;

public class Player extends GameObject {

    public Player(float hp, float dps) {
        super(hp, dps);
    }

    private void doSimpleAttack(GameObject target, float damage) {
        target.hp -= damage;
    }

    public void doSimpleAttack(Boss boss) {
        doSimpleAttack(boss, dps);
    }
}
