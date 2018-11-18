package beans;

public class GameObject {

    float dps;
    float hp;

    public GameObject( float hp, float dps) {
        this.dps = dps;
        this.hp = hp;
    }

    public float getDps() {
        return dps;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }
}
