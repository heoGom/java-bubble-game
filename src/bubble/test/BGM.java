package bubble.test;

import javax.sound.sampled.*;
import java.io.File;

public class BGM {
    public BGM(){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            //소리설정
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f);

            clip.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
