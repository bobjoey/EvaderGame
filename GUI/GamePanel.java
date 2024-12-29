package GUI;
import General.*;
import Game.*;
import Entities.Player;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

public class GamePanel extends JPanel implements KeyListener {
  private PanelControl controller;
  
  private GameState game;

  public GamePanel(PanelControl p) {
    super();
    controller = p;
    setup();
  }

  public void setup() {
    this.setFocusable(true);
    this.addKeyListener(this);

    game = new GameState(controller, controller.getSheet());
  }

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, 600, 600);

    game.updateGame();
    displayGame(g);
    
    controller.nextFrame();
    repaint();
  }

  // displays the game
  public void displayGame(Graphics g) {
    displayObjs(g);
    displayUI(g);
  }
  
  // display entities and projectiles
  public void displayObjs(Graphics g) {
    // queue of sprites to be drawn, key = priority level
    TreeMap<Integer, ArrayList<Sprite>> sprites = new TreeMap<>();
    
    // add player to sprite queue
    Player pl = game.getPlayer();
    addSprite(sprites, pl.getSprite());
    // draw player hitbox if they are "focused"
    if (pl.getKey(Player.SHIFT)) {
     addSprite(sprites, new Sprite(Tools.resize(getGame().getSheet().get("playerHitbox"), pl.getSize()/8.0), pl.getX(), pl.getY(), 6));
    }
    
    // add entities to sprite queue
    for (Entity e: game.getEntities()) {
      addSprite(sprites, e.getSprite());
    }
    
    // add projectiles to sprite queue
    for (Projectile p: game.getProjectiles()) {
      addSprite(sprites, p.getSprite());
    }
    
    // draw the sprites added in the queue
    for (int k: sprites.keySet()) {
      for (Sprite s: sprites.get(k)) {
        drawSprite(g, s);
      }
    }
  }
  
  public void addSprite(TreeMap<Integer, ArrayList<Sprite>> sprites, Sprite s) {
    int k = s.getPriority();
    if (!sprites.containsKey(k)) {
      sprites.put(k, new ArrayList<Sprite>());
    }
    sprites.get(k).add(s);
  }
  
  public void drawSprite(Graphics g, Sprite s) {
    int w = s.getImg().getWidth();
    int h = s.getImg().getHeight();

    g.drawImage(s.getImg(), (int)(s.getX()-w/2), (int)(s.getY()-h/2), null);
  }
  
  // display UI elements such as health, score, and wave #
  public void displayUI(Graphics g) {
    g.setColor(new Color(20, 20, 40));
    g.fillRect(500, 0, 200, 600);
    g.setFont(controller.getBodyFont(14));
    
    g.setColor(new Color(255, 255, 200));
    g.drawString("Wave: " + game.getWaveControl().getWaveNum(), game.getWidth()+15, 50);
    
    // stat indicators
    int hp = (int) game.getPlayer().getHp();
    g.setColor(new Color(180, 30, 10));
    g.fillRect(510, 105, 80, 10);
    g.setColor(new Color(30, 255, 40));
    g.fillRect(510, 105, (int)(hp*0.8), 10);
    g.drawString("HP: " + hp, game.getWidth()+15, 100);

    int c = (int) game.getPlayer().getCharge();
    g.setColor(new Color(60, 60, 100));
    g.fillRect(510, 155, 80, 10);
    g.setColor(new Color(255, 255, 10));
    g.fillRect(510, 155, (int)(c*0.8), 10);
    g.drawString("C: " + c, game.getWidth()+15, 150);

    int p = (int) game.getPlayer().getPowerLevel();
    g.setColor(new Color(80, 80, 60));
    g.fillRect(510, 205, 80, 10);
    g.setColor(new Color(100, 240, 255));
    g.fillRect(510, 205, (int)(p*0.8), 10);
    g.drawString("P: " + p, game.getWidth()+15, 200);
  }

  public GameState getGame() {
    return game;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        game.getPlayer().keyP(Player.UP);
        break;
      case KeyEvent.VK_DOWN:
        game.getPlayer().keyP(Player.DOWN);
        break;
      case KeyEvent.VK_LEFT:
        game.getPlayer().keyP(Player.LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        game.getPlayer().keyP(Player.RIGHT);
        break;
      case KeyEvent.VK_Z:
        game.getPlayer().keyP(Player.Z);
        break;
      case KeyEvent.VK_X:
        game.getPlayer().keyP(Player.X);
        break;
      case KeyEvent.VK_SHIFT:
        game.getPlayer().keyP(Player.SHIFT);
        break;
      case KeyEvent.VK_ESCAPE:
        game.pause();
      default:
        break;
    }
  }
  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP:
        game.getPlayer().keyR(Player.UP);
        break;
      case KeyEvent.VK_DOWN:
        game.getPlayer().keyR(Player.DOWN);
        break;
      case KeyEvent.VK_LEFT:
        game.getPlayer().keyR(Player.LEFT);
        break;
      case KeyEvent.VK_RIGHT:
        game.getPlayer().keyR(Player.RIGHT);
        break;
      case KeyEvent.VK_Z:
        game.getPlayer().keyR(Player.Z);
        break;
      case KeyEvent.VK_X:
        game.getPlayer().keyR(Player.X);
        break;
      case KeyEvent.VK_SHIFT:
        game.getPlayer().keyR(Player.SHIFT);
        break;
      default:
        break;
    }
  }
  public void keyTyped(KeyEvent e) {}
}