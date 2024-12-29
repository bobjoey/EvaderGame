package GUI;
import General.*;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class InfoPanel extends JPanel implements KeyListener {
  private PanelControl controller;
  private int page;

  public InfoPanel(PanelControl p) {
    super();
    controller = p;
    setup();
  }

  public void setup() {
    this.setFocusable(true);
    this.addKeyListener(this);
    page = 0;
  }

  /*
  display a specific page of the info panel (based on the value of page)
  pages: controls, focus, stats, pickups
  */
  @Override
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, 600, 600);
    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getBodyFont(15));
    
    switch (page) {
      case 0:
        drawControlsInfo(g);
        break;
      case 1:
        drawControlsInfo(g);
        break;
      case 2:
        drawFocusInfo(g);
        break;
      case 3:
        drawStatsInfo(g);
        break;
      case 4:
        drawPickupsInfo(g);
      default:
        break;
    }

    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getBodyFont(15));
    Tools.drawCenteredText(g, "- Press Z to Continue -", 300, 500);
    
    controller.nextFrame();
    repaint();
  }

  public void drawControlsInfo(Graphics g) {
    g.drawString("Controls:", 20, 40);
    g.drawString("Arrow Keys:  Select (In Menu) / Move (In Game)", 20, 80);
    g.drawString("Z:  Confirm (In Menu) / Main Attack (In Game)", 20, 100);
    g.drawString("X:  Back (In Menu) / Special Ability (In Game)", 20, 120);
    g.drawString("Shift:  Focus (In Game)", 20, 140);
    g.drawString("Esc:  Pause (In Game)", 20, 160);
  }

  public void drawFocusInfo(Graphics g) {
    g.drawString("Focus", 20, 40);
    g.drawString("Holding shift allows you to 'focus', which decreases your", 20, 80);
    g.drawString("speed, displays your hitbox, and decreases your main", 20, 100);
    g.drawString("attack spread.", 20, 120);
    g.drawString("Focus can be useful for precise dodging or for aiming at", 20, 160);
    g.drawString("key targets.", 20, 180);
  }

  public void drawStatsInfo(Graphics g) {
    g.setColor(new Color(30, 255, 40));
    g.drawString("Health (HP)", 20, 40);
    g.setColor(new Color(255, 255, 255));
    g.drawString("When you get hit by projectiles, your health decreases.", 20, 70);
    g.drawString("If it reaches 0, the game ends.", 20, 90);
    g.drawString("Health starts at its maximum value, 100, and can only be", 20, 120);
    g.drawString("replenished with green pickups.", 20, 140);

    g.setColor(new Color(100, 240, 255));
    g.drawString("Power (P)", 20, 180);
    g.setColor(new Color(255, 255, 255));
    g.drawString("Power can increase the strength of your main attack. At", 20, 210);
    g.drawString("20, 50, and 80 power, the main attack gains extra", 20, 230);
    g.drawString("projectiles.", 20, 250);
    g.drawString("Power starts at 0, and can be increased up to a maximum of", 20, 280);
    g.drawString("100 using blue pickups. However, it decreases over time.", 20, 300);

    g.setColor(new Color(255, 255, 10));
    g.drawString("Charge (C)", 20, 340);
    g.setColor(new Color(255, 255, 255));
    g.drawString("Charge is required in order to use your special ability.", 20, 370);
    g.drawString("Different abilities consume different amounts of charge.", 20, 390);
    g.drawString("Charge starts at 0 and can increase over time up to a", 20, 420);
    g.drawString("maximum of 100, but yellow pickups can speed up the", 20, 440);
    g.drawString("process.", 20, 460);
  }

  public void drawPickupsInfo(Graphics g) {
    g.drawString("Pickups", 20, 40);
    g.drawString("Enemies will occasionally drop pickups when destroyed.", 20, 80);
    g.drawString("Approach these pickups to collect them, granting certain", 20, 100);
    g.drawString("bonuses:", 20, 120);
    g.drawString(" - Green pickups replenish health", 20, 160);
    g.drawString(" - Blue pickups increase power", 20, 190);
    g.drawString(" - Yellow pickups increase charge", 20, 220);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_Z) {
      if (page <= 0 || page >= 4) {
        page = 1;
        controller.setScreen("MENU");
      } else {
        page++;
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_X) {
      if (page == 1) {
        controller.setScreen("MENU");
      } else if (page > 1 && page <= 4) {
        page--;
      }
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
}