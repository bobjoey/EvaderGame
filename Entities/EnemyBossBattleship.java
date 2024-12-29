package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyBossBattleship extends Entity {
  private Entity[] turrets;
  private int movementMode;
  private double xMovDir, yMovDir, xTarget, yTarget;
  private boolean spawnTurrets;
  // private double shootCD, spawnCD;

  public EnemyBossBattleship(GameState g, double x, double y) {
    super(g, 1, 30, 3200, x, y);
    movementMode = 0;
    xMovDir = 0;
    yMovDir = 0;
    xTarget = getGame().getWidth()/2;
    yTarget = 30;
    spawnTurrets = true;
    // shootCD = 80;
    // spawnCD = 80;
  }

  // creates n*2 turrets randomly chosen between EnemyTurretScatter and EnemyTurretSniper, spaced 50 apart with n on the left and n on the right of the boss
  public void setupTurrets() {
    int n = 2;
    turrets = new Entity[n*2];
    int index = 0;
    for (int i = -n; i <= n; i++) {
      if (i == 0) continue;
      
      double xOffset = i*50;
      double d = Math.random();
      if (d > 0.5) {
        turrets[index] = new EnemyTurretScatter(getGame(), this, xOffset);
      } else {
        turrets[index] = new EnemyTurretSniper(getGame(), this, xOffset);
      }
      getGame().addEntity(turrets[index]);
      index++;
    }
  }

  @Override
  public void update() {
    if (spawnTurrets) {
      setupTurrets();
      spawnTurrets = false;
    }
    
    super.update();

    processMovement();
  }
  
  @Override
  public void prepareActions() {}

  @Override
  public void executeAction(Action a) {}

  /* Movement pattern:
    target point starts at the base value (centered horizontally and 50 down vertically) and changes when movement is too slow, then slowly shifts back
    directs movement to accelerate toward the target point but can overshoot
  */
  public void processMovement() {
    if (movementMode == 0) {
      setY(getY() + 4);
      if (getY() > 10) movementMode = 1;
      return;
    }

    double xBase = getGame().getWidth()/2;
    double yBase = 50;
    
    xTarget = xBase + (xTarget-xBase)*0.9;
    yTarget = yBase + (yTarget-yBase)*0.9;

    if (Math.abs(xMovDir) <= 0.1 && xTarget < xBase + 25) xTarget = xBase + 50;
    if (Math.abs(yMovDir) <= 0.5 && yTarget < yBase + 25) yTarget = yBase + 50;
  
    if (getX() < xTarget) xMovDir += 0.15;
    if (getX() > xTarget) xMovDir -= 0.15;
    if (getY() < yTarget) yMovDir += 0.05;
    if (getY() > yTarget) yMovDir -= 0.05;

    if (xMovDir > 4) xMovDir = 4;
    if (xMovDir < -4) xMovDir = -4;
    if (yMovDir > 4) yMovDir = 4;
    if (yMovDir < -4) yMovDir = -4;

    setX(getX() + xMovDir);
    setY(getY() + yMovDir);
  }

  @Override
  public void destroy() {
    for (Entity e: turrets) {
      e.destroy();
    }
    super.destroy();
  }

  // spawns 8 particles, and between 4 and 8 random pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 8; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));

    int dropCount = (int) (4 + Math.random()*5);
    for (int i = 0; i < dropCount; i++) {
      double sV = -Math.random()*5-1;
      double ixd = (Math.random()-0.5)*12;
      
      double d = Math.random();
      if (d > 0.66) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY(), sV, ixd));
      else if (d > 0.33) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY(), sV, ixd));
      else getGame().addProjectile(new PickupPower(getGame(), getX(), getY(), sV, ixd));
    }
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("enemyBossBattleship"), getX(), getY(), 1);
  }
  
}