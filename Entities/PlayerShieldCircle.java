package Entities;
import General.*;
import Game.*;

public class PlayerShieldCircle extends Entity {
  private double trueSize;
  private double scaleRate;
  private int lifespan;

  public PlayerShieldCircle(GameState g, double x, double y) {
    super(g, 0, 18, 1, x, y);
    trueSize = getSize();
    scaleRate = 30;
    lifespan = 0;
  }

  @Override
  public void update() {
    trueSize += scaleRate;
    setSize((int) trueSize);
    scaleRate *= 0.7;
    lifespan++;
    if (lifespan >= 60) scaleRate = -16;
    if (trueSize <= 6) destroy();
    collisionCheck();
  }

  @Override
  public void onCollision(Projectile p) {}

  @Override
  public void prepareActions() {}

  @Override
  public void executeAction(Action a) {}

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.resize(getGame().getSheet().get("playerShieldCircle"), trueSize/62), getX(), getY(), 4);
  }
  
}