package GUI;
import General.PanelControl;
import General.Tools;
import Game.GameState;
import Entities.Player;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class SelectPanel extends JPanel implements KeyListener {
  private PanelControl controller;

  private int option;

  public SelectPanel(PanelControl p) {
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
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, 600, 600);
    
    g.setColor(new Color(255, 255, 255));
    g.setFont(controller.getTitleFont(30));
    Tools.drawCenteredText(g, "Choose an Ability", 300, 60);

    g.setFont(controller.getBodyFont(18));
    for (int i = 0; i <= 1; i++) {
      displaySelection(g, i);
    }
    
    controller.nextFrame();
    repaint();
  }

  public void displaySelection(Graphics g, int i) {
    if (option == i) {
      g.setColor(new Color(255, 255, 0));
    } else {
      g.setColor(new Color(255, 255, 255));
    }

    int x = 150 + 300 * i;
    String s = "";
    BufferedImage img = null;
    switch (i) {
      case 0:
        s = "Shield";
        img = controller.getSheet().get("playerShieldCircle");
        break;
      case 1:
        s = "Drone";
        img = Tools.rotate(controller.getSheet().get("playerDrone"), Math.PI/2);
        break;
      default:
        break;
    }
    
    g.drawRoundRect(x-100, 120, 200, 380, 20, 20);
    g.drawRoundRect(x-99, 121, 198, 378, 19, 19);
    
    int size = Math.max(img.getWidth(), img.getHeight());
    img = Tools.resize(img, 150.0/size);
    g.drawImage(img, x-img.getWidth()/2, 300-img.getHeight()/2, null);
    
    Tools.drawCenteredText(g, s, x, 460);

    if (option != i) {
      g.setColor(new Color(0, 0, 0, 100));
      g.fillRoundRect(x-100, 120, 200, 380, 20, 20);
    }
  }

  public void selectOption() {
    switch (option) {
      case 0:
        ((GamePanel)controller.getPanel("GAME")).getGame().getPlayer().setOption(1);
        controller.setScreen("GAME");
        break;
      case 1:
        ((GamePanel)controller.getPanel("GAME")).getGame().getPlayer().setOption(2);
        controller.setScreen("GAME");
        break;
      default:
        break;
    }
  }

  public void changeOption(int c) {
    int maxOption = 2;
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
        controller.setScreen("MENU");
      default:
        break;
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
}