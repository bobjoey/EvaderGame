package General;

import java.awt.image.BufferedImage;

public class Sprite {
  private BufferedImage img;
  private double x, y;
  private int priority;

  /*
    Priority scale:
    6: highest priority (player hitbox)
    5: projectiles
    4: vfx (particles)
    3: carriers
    2: high priority entities (boss turrets)
    1: medium priority entities (boss carrier frame)
    0: low priority entities (most enemies, player)
  */
  
  public Sprite(BufferedImage i, double x, double y, int p) {
    img = i;
    this.x = x;
    this.y = y;
    priority = p;
  }

  public BufferedImage getImg() {
    return img;
  }
  public int getPriority() {
    return priority;
  }
  public double getX() {
    return x;
  }
  public double getY() {
    return y;
  }
  
}