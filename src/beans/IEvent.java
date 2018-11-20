package beans;

public interface IEvent {

    boolean onBossAttack();

    boolean onPlayerAttack();

    boolean onRaidStart();

    boolean onRaidEnd();

    boolean onRaidSuccess();

    boolean onRaidFailed();

}
