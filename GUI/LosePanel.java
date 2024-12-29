package GUI;
import General.*;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class LosePanel extends JPanel implements KeyListener {
  private PanelControl controller;
  private int wave;

  public LosePanel(PanelControl p) {
    super();
    controller = p;
    wave = 0;
    setup();
  }

  public void setup() {
    this.setFocusable(true);
    this.addKeyListener(this);
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, 600, 600);
    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getTitleFont(40));
    Tools.drawCenteredText(g, "GAME OVER", 300, 60);

    g.setFont(controller.getBodyFont(14));
    Tools.drawCenteredText(g, "Reached wave: " + wave, 300, 100);

    g.drawImage(controller.getSheet().get("playerGrayscale"), 150, 150, null);

    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getBodyFont(15));
    Tools.drawCenteredText(g, "- Press Z to Continue -", 300, 500);
    
    controller.nextFrame();
    repaint();
  }

  public void setWave(int w) {
    wave = w;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_Z) {
      controller.setScreen("MENU");
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
}