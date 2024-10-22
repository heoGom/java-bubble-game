package bubble.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BackgroundPlayerService implements Runnable {

    private BufferedImage image;
    private Player player;

    private List<Bubble> bubbleList;

    public BackgroundPlayerService(Player player) {
        this.player = player;
        this.bubbleList =player.getBubbleList();
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {

            for (int i = 0; i < bubbleList.size(); i++) {
                if(bubbleList.get(i).getState()==1) {
                    if ((Math.abs(player.getX() - bubbleList.get(i).getX()) < 10) &&
                            (Math.abs(player.getY() - bubbleList.get(i).getY()) > 0 && Math.abs(player.getY() - bubbleList.get(i).getY()) < 50)){
                        bubbleList.get(i).clearBubbled();
                        bubbleList.remove(bubbleList.get(i));
                    }
                }
            }

            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
            int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) + image.getRGB(player.getX() + 50 - 10, player.getY() + 50 + 5);

            //바닥 충돌 확인
            if (bottomColor != -2) {
                player.setDown(false);
            } else {
                if (!player.isUp() && !player.isDown()){
                    System.out.println("다운실행됨");
                    player.down();
                }
            }

            //외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }

        }
    }

}
