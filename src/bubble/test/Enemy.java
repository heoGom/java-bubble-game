package bubble.test;


import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

    private BubbleFrame mContext;

    private int x, y;

    private EnemyWay enemyWay;

    private ImageIcon enemyR, enemyL;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private final int SPEED = 3;
    private final int JUMPSPEED = 1;

    private int state;

    public Enemy(BubbleFrame mContext) {
        this.mContext = mContext;
        this.state = 0;
        initObject();
        initSetting();
        initBackgroundEnemyService();
        right();

    }

    private void initBackgroundEnemyService() {
        new Thread(
                new BackgroundEnemyService(this)
        ).start();
    }

    private void initObject() {
        enemyR = new ImageIcon("image/enemyR.png");
        enemyL = new ImageIcon("image/enemyL.png");

    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;


        setIcon(enemyR);
        enemyWay = EnemyWay.RIGHT;
        setSize(50, 50);
        setLocation(x, y);

    }


    @Override
    public void left() {
        enemyWay = EnemyWay.LEFT;
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(enemyL);
                x = x - SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    @Override
    public void right() {
        enemyWay = EnemyWay.RIGHT;
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(enemyR);
                x = x + SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void up() {
        up = true;
        new Thread(() -> {
            while (up) {
                for (int i = 0; i < 130 / JUMPSPEED; i++) {
                    y = y - JUMPSPEED;
                    setLocation(x, y);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            up = false;
            down();

        }).start();

    }

    @Override
    public void down() {
        down = true;
        new Thread(() -> {
            while (down) {
                y = y + JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            down = false;
        }).start();

    }
}
