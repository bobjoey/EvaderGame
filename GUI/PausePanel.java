package GUI;
import General.*;
import Game.*;
import Entities.Player;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class PausePanel extends JPanel implements KeyListener {
  private PanelControl controller;

  public PausePanel(PanelControl p) {
    super();
    controller = p;
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
    Tools.drawCenteredText(g, "PAUSED", 300, 60);

    GameState game = ((GamePanel)controller.getPanel("GAME")).getGame();

    g.setFont(controller.getBodyFont(14));
    
    g.setColor(new Color(255, 255, 200));
    g.drawString("Wave: " + game.getWaveControl().getWaveNum(), 100, 110);
    
    int hp = (int) game.getPlayer().getHp();
    g.setColor(new Color(180, 30, 10));
    g.fillRect(200, 152, 200, 10);
    g.setColor(new Color(30, 255, 40));
    g.fillRect(200, 152, (int)(hp*2), 10);
    g.drawString("HP: " + hp, 100, 160);

    int c = (int) game.getPlayer().getCharge();
    g.setColor(new Color(60, 60, 100));
    g.fillRect(200, 192, 200, 10);
    g.setColor(new Color(255, 255, 10));
    g.fillRect(200, 192, (int)(c*2), 10);
    g.drawString("C: " + c, 100, 200);

    int p = (int) game.getPlayer().getPowerLevel();
    g.setColor(new Color(80, 80, 60));
    g.fillRect(200, 232, 200, 10);
    g.setColor(new Color(100, 240, 255));
    g.fillRect(200, 232, (int)(p*2), 10);
    g.drawString("P: " + p, 100, 240);

    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getBodyFont(15));
    Tools.drawCenteredText(g, "- Press Z to Continue -", 300, 470);
    Tools.drawCenteredText(g, "- Press X to Quit -", 300, 510);
    
    controller.nextFrame();
    repaint();
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_Z) {
      controller.setScreen("GAME");
    } else if (e.getKeyCode() == KeyEvent.VK_X) {
      ((GamePanel)controller.getPanel("GAME")).getGame().gameOver();
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
}