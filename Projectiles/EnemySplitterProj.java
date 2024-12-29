package Projectiles;
import General.*;
import Game.*;

public class EnemySplitterProj extends Projectile {
  private double duration;
  private int splitCount;

  public EnemySplitterProj(GameState g, double r, double x, double y) {
    super(g, 3.0, r, 1, 9, 15, x, y);
    duration = 90;
    splitCount = 8;
  }
  
  public EnemySplitterProj(GameState g, double r, double x, double y, double dur, int splits) {
    super(g, 3.0, r, 1, 9, 15, x, y);
    duration = dur;
    splitCount = splits;
  }

  public EnemySplitterProj(GameState g, double r, double x, double y, double dur, int splits, double sp) {
    super(g, sp, r, 1, 9, 15, x, y);
    duration = dur;
    splitCount = splits;
  }

  @Override
  public void update() {
    super.update();
    
    duration--;
    if (duration <= 0) destroy();
  }

  @Override
  public void destroy() {
    // if (duration > 0) {
    //   super.destroy();
    //   return;
    // }
    
    double base = getRotation();
    for (double r = base; r < Math.PI*2+base; r += Math.PI*2/splitCount) {
      getGame().addProjectile(new EnemyBeamProj(getGame(), r, getX(), getY()));
    }
    
    super.destroy();
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("enemySplitterProj"), getX(), getY(), 5);
  }
  
}