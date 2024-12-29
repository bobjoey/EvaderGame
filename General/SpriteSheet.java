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

  public SpriteSheet() {
    sheet = new HashMap<String, BufferedImage>();
    sheet.put("red", setupRed());
    sheet.put("clear", setupSprite("Sprites/Clear.png", 1, 1));
    sheet.put("player", setupSprite("Sprites/Player.png", 48, 48));
    sheet.put("playerHitbox", setupSprite("Sprites/PlayerHitbox.png", 16, 16));
    sheet.put("playerBeamProj", setupSprite("Sprites/PlayerBeamProj.png", 20, 20));
    sheet.put("enemyBeamProj", setupSprite("Sprites/EnemyBeamProj.png", 16, 16));
    sheet.put("enemySplitterProj", setupSprite("Sprites/EnemySplitterProj.png", 18, 18));
    sheet.put("enemyCarrier", setupSprite("Sprites/EnemyCarrier.png", 32, 32));
    sheet.put("enemyDrone", setupSprite("Sprites/EnemyDrone.png", 32, 32));
    sheet.put("enemyChaser", setupSprite("Sprites/EnemyChaser.png", 36, 36));
    sheet.put("enemyShooter", setupSprite("Sprites/EnemyShooter.png", 36, 36));
    sheet.put("playerShieldCircle", setupSprite("Sprites/PlayerShieldCircle.png", 128, 128));
    sheet.put("pickupHeal", setupSprite("Sprites/PickupHeal.png", 16, 16));
    sheet.put("pickupCharge", setupSprite("Sprites/PickupCharge.png", 16, 16));
    sheet.put("pickupPower", setupSprite("Sprites/PickupPower.png", 16, 16));
    sheet.put("playerDrone", setupSprite("Sprites/PlayerDrone.png", 18, 18));
    sheet.put("pellet", setupSprite("Sprites/Pellet.png", 12, 4));
    sheet.put("enemyGatling", setupSprite("Sprites/EnemyGatling.png", 32, 32));
    sheet.put("enemyBossBattleship", setupSprite("Sprites/EnemyBossBattleship.png", 224, 24));
    sheet.put("enemyBattleship", setupSprite("Sprites/EnemyBattleship.png", 120, 24));
    sheet.put("menuBG", setupSprite("Sprites/MenuBG.png", 600, 600));
    sheet.put("playerGrayscale", setupSprite("Sprites/PlayerGrayscale.png", 300, 300));
  }

  public BufferedImage get(String s) {
    if (sheet.keySet().contains(s)) return sheet.get(s);
    return sheet.get("red");
  }
                                          
  // read in the image file and turn into BufferedImage
  public BufferedImage setupSprite(String pathname, int w, int h) {
    BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    try {
      Image img = ImageIO.read(new File(pathname)).getScaledInstance(w, h, Image.SCALE_DEFAULT);
      Graphics g = bimg.getGraphics();
      g.drawImage(img, 0, 0, null);
      g.dispose();
    } catch (IOException e) {}
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