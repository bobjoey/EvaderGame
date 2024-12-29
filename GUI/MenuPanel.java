package GUI;

import General.PanelControl;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class MenuPanel extends JPanel implements KeyListener {
  private PanelControl controller;

  private int option;

  public MenuPanel(PanelControl p) {
    super();
    controller = p;
    setup();
  }

  public void setup() {
    this.setFocusable(true);
    this.addKeyListener(this);

    option = 0;
  }

  @Override
  public void paintComponent(Graphics g) {
    g.drawImage(controller.getSheet().get("menuBG"), 0, 0, null);

    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getTitleFont(60));
    g.drawString("EVADER", 30, 60);

    g.setFont(controller.getBodyFont(16));

    displaySelText(g, "Start", 0);
    displaySelText(g, "Info", 1);
    displaySelText(g, "Exit", 2);

    controller.nextFrame();
    repaint();
  }

  public void displaySelText(Graphics g, String s, int i) {
    if (option == i) {
      g.setColor(new Color(255, 255, 0));
    } else {
      g.setColor(new Color(255, 255, 255));
    }
    g.drawString(s, 35, 100 + 25 * i);
  }

  public void selectOption() {
    switch (option) {
      case 0:
        controller.setScreen("SELECT");
        break;
      case 1:
        controller.setScreen("INFO");
        break;
      case 2:
        System.exit(1);
        break;
      default:
        break;
    }
  }

  public void changeOption(int c) {
    int maxOption = 3;
    option = (maxOption + option + c) % maxOption;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_Z:
        selectOption();
        break;
      case KeyEvent.VK_UP:
        changeOption(-1);
        break;
      case KeyEvent.VK_DOWN:
        changeOption(1);
        break;
      case KeyEvent.VK_LEFT:
        changeOption(-1);
        break;
      case KeyEvent.VK_RIGHT:
        changeOption(1);
        break;
      case KeyEvent.VK_X:
        option = 2;
        break;
      default:
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }
}