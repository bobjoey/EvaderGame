package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyBattleship extends Entity {
  private Entity[] turrets;
  private double xMovDir, yMovDir, xTarget, yTarget, moveCD;
  private boolean spawnTurrets;

  public EnemyBattleship(GameState g, double x, double y) {
    super(g, 1, 30, 800, x, y);
    xMovDir = 0;
    yMovDir = 0;
    xTarget = getGame().getWidth()/2;
    yTarget = 30;
    moveCD = 0;
    spawnTurrets = true;
  }

  // spawns a EnemyTurretScatter on the left and a EnemyTurretSniper on the right
  public void setupTurrets() {
    turrets = new Entity[2];
    turrets[0] = new EnemyTurretScatter(getGame(), this, -50);
    getGame().addEntity(turrets[0]);
    turrets[1] = new EnemyTurretSniper(getGame(), this, 50);
    getGame().addEntity(turrets[1]);
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
    gets a new target point every 1.5 seconds, x can be any point on the game screen and y is between 0 and 59 (random)
    directs movement to accelerate toward the target point but can overshoot
  */
  public void processMovement() {
    if (moveCD <= 0) {
      moveCD = 90;
      xTarget = Math.random()*getGame().getWidth();
      yTarget = Math.random()*60;
    }
    moveCD--;
  
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

  // spawns 6 particles, and between 2 and 4 random pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 6; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));

    int dropCount = (int) (2 + Math.random()*3);
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
    return new Sprite(getGame().getSheet().get("enemyBattleship"), getX(), getY(), 1);
  }
  
}