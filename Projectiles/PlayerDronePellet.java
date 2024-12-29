package Projectiles;
import General.*;
import Game.*;

public class PlayerDronePellet extends Projectile {

  public PlayerDronePellet(GameState g, double r, double x, double y) {
    super(g, 12.0, r, 0, 10, 10, x, y);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("pellet"), getRotation()), getX(), getY(), 5);
  }
  
}