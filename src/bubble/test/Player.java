package bubble.test;


import lombok.Data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Player extends JLabel implements Moveable {

    private BubbleFrame mContext;

    private List<Bubble> bubbleList;

    private int x, y;

    private PlayerWay playerWay;

    private ImageIcon playerR, playerL;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private boolean leftWallCrash;
    private boolean rightWallCrash;

    private final int SPEED = 4;
    private final int JUMPSPEED = 2;

    public Player(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundPlayerService();

    }

    private void initBackgroundPlayerService() {
        new Thread(
                new BackgroundPlayerService(this)
        ).start();
    }

    private void initObject() {
        playerR = new ImageIcon("image/playerR.png");
        playerL = new ImageIcon("image/playerL.png");
        bubbleList = new ArrayList<>();

    }

    private void initSetting() {
        x = 80;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        leftWallCrash = false;
        rightWallCrash = false;

        setIcon(playerR);
        playerWay = PlayerWay.RIGHT;
        setSize(50, 50);
        setLocation(x, y);

    }

    @Override
    public void attack() {
        new Thread(() -> {
            Bubble bubble = new Bubble(mContext);
            mContext.add(bubble);
            bubbleList.add(bubble);
            if (playerWay == PlayerWay.LEFT) {
                bubble.left();
            } else if (playerWay == PlayerWay.RIGHT) {
                bubble.right();
            }
        }).start();

    }

    @Override
    public void left() {
        playerWay = PlayerWay.LEFT;
        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(playerL);
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
        playerWay = PlayerWay.RIGHT;
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(playerR);
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
        System.out.println("up");
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
        System.out.println("down");
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
