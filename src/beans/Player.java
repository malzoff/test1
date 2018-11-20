package beans;

public class Player {

    private float hp;
    private float dps;

    public Player(float hp, float dps) {

        this.hp = hp;
        this.dps = dps;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getDps() {
        return dps;
    }

    private void doSimpleAttack(Boss target, float damage) {
        target.setHp(target.getHp() - damage);
    }

    public void doSimpleAttack(Boss boss) {
        doSimpleAttack(boss, getDps());
    }
}
