package bubble.test;

public interface Moveable {
    public abstract void left();
    public abstract void right();
    public abstract void up() throws InterruptedException;
    default public void down() {};
    default public void attack() {};
}
