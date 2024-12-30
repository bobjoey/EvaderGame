package General;
import GUI.*;

import javax.swing.JPanel;
import java.awt.Font;
import java.io.File;

public class PanelControl {
  private EvFrame evFrame;
  private SpriteSheet sheet;
  private Font title, body;
  
  private long ntime;

  public PanelControl() {
    sheet = new SpriteSheet();
    ntime = System.currentTimeMillis();

    try {
      title = Font.createFont(Font.TRUETYPE_FONT, new File("Sprites", "upheavtt.ttf"));
      body = Font.createFont(Font.TRUETYPE_FONT, new File("Sprites", "Retro Gaming.ttf"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    evFrame = new EvFrame(this);
  }

  public void setScreen(String s) {
    evFrame.setScreen(s);
  }
  public JPanel getPanel(String s) {
    return evFrame.getPanels().get(s);
  }

  // wait for the next frame (~30 fps)
  public void nextFrame() {
    ntime += 33;
    long stime = ntime - System.currentTimeMillis();
    if (stime > 0) {
      try {
        Thread.sleep(stime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      ntime -= stime/2;
    }
  }

  public Font getTitleFont(float size) {
    return title.deriveFont(size);
  }
  public Font getBodyFont(float size) {
    return body.deriveFont(size);
  }

  public SpriteSheet getSheet() {
    return sheet;
  }
  
}