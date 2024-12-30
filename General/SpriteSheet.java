package General;

import java.util.HashMap;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;

public class SpriteSheet {
  private HashMap<String, BufferedImage> sheet;
  private static final String PATH = "Sprites";

  public SpriteSheet() {
    sheet = new HashMap<String, BufferedImage>();
    sheet.put("red", setupRed());
    sheet.put("clear", setupSprite("Clear.png", 1, 1));
    sheet.put("player", setupSprite("Player.png", 48, 48));
    sheet.put("playerHitbox", setupSprite("PlayerHitbox.png", 16, 16));
    sheet.put("playerBeamProj", setupSprite("PlayerBeamProj.png", 20, 20));
    sheet.put("enemyBeamProj", setupSprite("EnemyBeamProj.png", 16, 16));
    sheet.put("enemySplitterProj", setupSprite("EnemySplitterProj.png", 18, 18));
    sheet.put("enemyCarrier", setupSprite("EnemyCarrier.png", 32, 32));
    sheet.put("enemyDrone", setupSprite("EnemyDrone.png", 32, 32));
    sheet.put("enemyChaser", setupSprite("EnemyChaser.png", 36, 36));
    sheet.put("enemyShooter", setupSprite("EnemyShooter.png", 36, 36));
    sheet.put("playerShieldCircle", setupSprite("PlayerShieldCircle.png", 128, 128));
    sheet.put("pickupHeal", setupSprite("PickupHeal.png", 16, 16));
    sheet.put("pickupCharge", setupSprite("PickupCharge.png", 16, 16));
    sheet.put("pickupPower", setupSprite("PickupPower.png", 16, 16));
    sheet.put("playerDrone", setupSprite("PlayerDrone.png", 18, 18));
    sheet.put("pellet", setupSprite("Pellet.png", 12, 4));
    sheet.put("enemyGatling", setupSprite("EnemyGatling.png", 32, 32));
    sheet.put("enemyBossBattleship", setupSprite("EnemyBossBattleship.png", 224, 24));
    sheet.put("enemyBattleship", setupSprite("EnemyBattleship.png", 120, 24));
    sheet.put("menuBG", setupSprite("MenuBG.png", 600, 600));
    sheet.put("playerGrayscale", setupSprite("PlayerGrayscale.png", 300, 300));
  }

  public BufferedImage get(String s) {
    if (sheet.keySet().contains(s)) return sheet.get(s);
    return sheet.get("red");
  }
                                          
  // read in the image file and turn into BufferedImage
  public BufferedImage setupSprite(String filename, int w, int h) {
    BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    try {
      Image img = ImageIO.read(new File(PATH, filename)).getScaledInstance(w, h, Image.SCALE_DEFAULT);
      Graphics g = bimg.getGraphics();
      g.drawImage(img, 0, 0, null);
      g.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bimg;
  }

  // placeholder sprite
  public BufferedImage setupRed() {
    BufferedImage red = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
    Graphics g = red.getGraphics();
    g.setColor(new Color(255, 0, 0));
    g.fillOval(5, 5, 25, 25);
    g.dispose();
    return red;
  }

}