package beans;

public class Player extends GameObject {

    public Player(float hp, float dps) {
        super(hp, dps);
    }

    public void doSimpleAttack(GameObject target, float damage) {

    }

    @Override
    public String toString() {
        return "P{" +
                "dps=" + Float.toString(dps) +
                '}';
    }
}
