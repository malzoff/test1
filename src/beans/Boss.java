package beans;

public class Boss extends GameObject {

    private float aoeDamage;
    private float aoeNukeDamage;
    private Player currentTarget = null;

    public Boss(float hp, float dps, float aoeDamage, float aoeNukeDamage) {
        super(dps, hp);
        this.aoeDamage = aoeDamage;
        this.aoeNukeDamage = aoeNukeDamage;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }

    public float getAoeNukeDamage() {
        return aoeNukeDamage;
    }

    public Player getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Player currentTarget) {
        this.currentTarget = currentTarget;
    }
}
