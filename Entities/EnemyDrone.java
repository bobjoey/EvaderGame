package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyDrone extends Entity {
  private double moveClock, atkClock;
  private int shotCount;

  public EnemyDrone(GameState g, double x, double y) {
    super(g, 1, 8, 40, x, y);
    moveClock = 40;
    atkClock = 40;
    shotCount = 3;
  }
  public EnemyDrone(GameState g, double x, double y, boolean e) {
    this(g, x, y);
    if (e) processEliteModifiers();
  }
  // Elite enemy changes: increased health, fires EnemyBeamProj in bursts of 4 instead of 3
  public void processEliteModifiers() {
    setHp(60);
    shotCount = 4;
  }

  @Override
  public void update() {
    super.update();

    moveClock++;
    atkClock++;
  }

  /*
    every 4 seconds, moves in a random direction and decelerates
    every 2.83 seconds, fires a burst of enemyBeamProj at the player
  */
  @Override
  public void prepareActions() {
    if (moveClock > 120) {
      double r = Math.random() * 2 * Math.PI;
      getAList().add(new Action(0, 15, new double[]{r}));

      moveClock = 0;
    }

    if (atkClock > 85) {
      double x = getGame().getPlayer().getX();
      double y = getGame().getPlayer().getY();
      getAList().add(new Action(1, shotCount*2-1, new double[]{x, y}));

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
        processAtk(a);
        break;
        
      default:
        break;
    }
  }

  // moves in the predetermined direction and decelerates, cannot leave the game screen
  public void processMovement(Action a) {
    double r = a.getData()[0];
    double spd = a.getTimer()/2;
    moveInDir(r, spd);

    int w = getGame().getWidth();
    int h = getGame().getHeight();
    if (getX() < 0) setX(0);
    if (getX() > w) setX(w);
    if (getY() < 0) setY(0);
    if (getY() > h) setY(h);
  }

  // fires 1 EnemyBeamProj in the predetermined direction every other frame for the set amount of time
  public void processAtk(Action a) {
    if (a.getTimer() % 2 == 0) {
      double x = a.getData()[0];
      double y = a.getData()[1];
      double r = Tools.angleTo(getX(), -getY(), x, -y);
      getGame().addProjectile(new EnemyBeamProj(getGame(), r, getX(), getY()));
    }
  }

  // spawns 3 particles, and has a chance to spawn each of the 3 pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 3; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));
    
    if (Math.random() > 0.95) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));
    if (Math.random() > 0.95) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
    if (Math.random() > 0.95) getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("enemyDrone"), getX(), getY(), 0);
  }
  
}