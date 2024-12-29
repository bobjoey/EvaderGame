package Projectiles;
import General.*;
import Game.*;

public class EnemyDamageAura extends Projectile {
  private int timer;
  
  public EnemyDamageAura(GameState g, double x, double y, int s, int d) {
    super(g, 0, 0, 1, s, d, x, y);
    timer = 1;
  }

  @Override
  public void update() {
    if (timer <= 0) destroy();
    timer--;
  }

  // transparent sprite
  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("clear"), getX(), getY(), -1);
  }
  
}