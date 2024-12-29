package Projectiles;
import General.*;
import Game.*;

public class EnemyBeamProj extends Projectile {

  public EnemyBeamProj(GameState g, double r, double x, double y) {
    super(g, 6.0, r, 1, 7, 5, x, y);
  }
  public EnemyBeamProj(GameState g, double r, double x, double y, double sp) {
    super(g, sp, r, 1, 7, 5, x, y);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyBeamProj"), getRotation()), getX(), getY(), 5);
  }
  
}