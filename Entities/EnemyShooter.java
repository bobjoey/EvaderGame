package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyShooter extends Entity {
  private double moveClock, atkClock;
  private int splitCount;

  public EnemyShooter(GameState g, double x, double y) {
    super(g, 1, 8, 60, x, y);
    moveClock = 80;
    atkClock = 20;
    splitCount = 8;
  }
  public EnemyShooter(GameState g, double x, double y, boolean e) {
    this(g, x, y);
    if (e) processEliteModifiers();
  }

  // Elite enemy changes: increased health, EnemySplitterProj splits into more EnemyBeamProj
  public void processEliteModifiers() {
    setHp(80);
    splitCount = 12;
  }

  @Override
  public void update() {
    super.update();

    moveClock++;
    atkClock++;
  }

  /*
    every 4 seconds, moves towards a point in front of the player and decelerates 
    every 4 seconds (offset from previous timer by 1.33 seconds), fires an EnemySplitterProj forward with a timer to split when it reaches the player's position at the moment of firing
  */
  @Override
  public void prepareActions() {
    if (moveClock > 120) {
      double tx = getGame().getPlayer().getX();
      double ty = getGame().getPlayer().getY()-270;
      
      double r = Tools.angleTo(getX(), -getY(), tx, -ty);
      double s = Math.min(15, Tools.dist(getX(), getY(), tx, ty) * 0.05);
      
      getAList().add(new Action(0, 30, new double[]{r, s}));

      moveClock = 0;
    }

    if (atkClock > 120) {
      getAList().add(new Action(1, 2));

      atkClock = 0;
    }
  }

  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        processMovement(a);
        break;
        
      case 1:
        if (a.getTimer() == 1) {
          getGame().addProjectile(new EnemySplitterProj(getGame(), Math.PI*3/2, getX(), getY(), 90, splitCount));
        }
        break;
        
      default:
        break;
    }
  }

  // moves in the predetermined direction and decelerates, cannot leave the game screen
  public void processMovement(Action a) {
    double r = a.getData()[0];
    moveInDir(r, a.getData()[1]);
    a.getData()[1] *= 0.95;

    int w = getGame().getWidth();
    int h = getGame().getHeight();
    if (getX() < 0) setX(0);
    if (getX() > w) setX(w);
    if (getY() < 0) setY(0);
    if (getY() > h) setY(h);
  }

  // spawns 4 particles, and has a chance to spawn each of the 3 pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 4; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));

    if (Math.random() > 0.9) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));
    if (Math.random() > 0.9) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
    if (Math.random() > 0.9) getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("enemyShooter"), getX(), getY(), 0);
  }
  
}