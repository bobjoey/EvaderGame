package Projectiles;
import General.*;
import Game.*;

public class EnemyTrapProj extends Projectile {
  private double duration, slowRate;

  public EnemyTrapProj(GameState g, double r, double x, double y) {
    super(g, 6.0, r, 1, 7, 5, x, y);
    duration = 30;
    slowRate = 0.95;
  }
  
  public EnemyTrapProj(GameState g, double r, double x, double y, double sp, double dur, double sR) {
    super(g, sp, r, 1, 7, 5, x, y);
    duration = dur;
    slowRate = sR;
  }

  @Override
  public void update() {
    super.update();

    setSpeed(getSpeed()*slowRate);
    if (duration <= 0) destroy();
    duration--;
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyBeamProj"), getRotation()), getX(), getY(), 5);
  }
  
}