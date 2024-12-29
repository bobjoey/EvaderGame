package Projectiles;
import General.*;
import Game.*;

public class PlayerBeamProj extends Projectile {

  public PlayerBeamProj(GameState g, double r, double x, double y) {
    super(g, 15.0, r, 0, 12, 10, x, y);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("playerBeamProj"), getRotation()), getX(), getY(), 5);
  }
  
}