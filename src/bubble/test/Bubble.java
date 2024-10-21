package bubble.test;

import com.sun.jdi.event.ThreadStartEvent;
import lombok.Data;

import javax.swing.*;

@Data
public class Bubble extends JLabel implements Moveable {

    private BubbleFrame mContext;
    private Player player;
    private Enemy enemy;

    private BackgroundBubbleService backgroundBubbleService;

    private int x, y;
    private boolean left;
    private boolean right;
    private boolean up;

    private int state; //0(물방울) , 1(적을 가둔 물방울)

    private ImageIcon bubble;
    private ImageIcon bubbled;
    private ImageIcon bomb;

    public Bubble(BubbleFrame context) {
        this.mContext = context;
        this.player = mContext.getPlayer();
        this.enemy = mContext.getEnemy();
        initObject();
        initSetting();

    }

    private void initObject() {
        bubble = new ImageIcon("image/bubble.png");
        bubbled = new ImageIcon("image/bubbled.png");
        bomb = new ImageIcon("image/bomb.png");
        backgroundBubbleService = new BackgroundBubbleService(this);
    }

    private void initSetting() {
        left = false;
        right = false;
        up = false;

        x = player.getX();
        y = player.getY();
        setIcon(bubble);
        setSize(50, 50);
        state = 0;
    }

    @Override
    public void left() {
        left = true;
        for (int i = 0; i < 400; i++) {
            x--;
            setLocation(x, y);

            if (backgroundBubbleService.leftWall()) {
                left = false;
                break;
            }

            if ((Math.abs(x - enemy.getX()) < 10) &&
                    (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {
                System.out.println("적군 충돌");
                if (enemy.getState() == 0) {
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void right() {
        right = true;
        for (int i = 0; i < 400; i++) {
            x++;
            setLocation(x, y);

            if (backgroundBubbleService.rightWall()) {
                right = false;

            }

            if ((Math.abs(x - enemy.getX()) < 10) &&
                    (Math.abs(y - enemy.getY()) > 0 && Math.abs(y - enemy.getY()) < 50)) {
                System.out.println("적군 충돌");
                if (enemy.getState() == 0) {
                    attack();
                    break;
                }
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        up();
    }

    @Override
    public void up() {
        up = true;
        while (up) {
            y--;
            setLocation(x, y);
            if (backgroundBubbleService.topWall()) {
                up = false;
                break;
            }
            if (state == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }
        if (state == 0) {
            clearBubble();
        }
    }

    @Override
    public void attack() {
        state = 1;
        enemy.setState(1);
        setIcon(bubbled);
        mContext.remove(enemy);
        mContext.repaint();
    }

    private void clearBubble() {
        try {
            Thread.sleep(3000);
            setIcon(bomb);
            Thread.sleep(500);
            mContext.remove(this);
            mContext.getPlayer().getBubbleList().remove(this);
            mContext.repaint();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBubbled() {
        new Thread(() -> {
            try {
                setIcon(bomb);
                Thread.sleep(1000);
                mContext.remove(this);
                mContext.repaint();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }


}
