package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyChaser extends Entity {
  private double timer;
  private int splitCount;

  public EnemyChaser(GameState g, double x, double y) {
    super(g, 1, 8, 120, x, y);
    timer = 90;
    splitCount = 4;
  }
  public EnemyChaser(GameState g, double x, double y, boolean e) {
    this(g, x, y);
    if (e) processEliteModifiers();
  }
  // Elite enemy changes: increased hp, splits into more EnemySplitterProj which each split into more EnemyBeamProj
  public void processEliteModifiers() {
    setHp(180);
    splitCount = 6;
  }

  @Override
  public void update() {
    super.update();
    timer--;
    getGame().addProjectile(new EnemyDamageAura(getGame(), getX(), getY(), 7, 2));
  }

  // when timer reaches 0 (after 1.5 seconds), destroys itself and splits into splitCount amount of EnemySplitterProj - otherwise, moves towards the player in short bursts
  @Override
  public void prepareActions() {
    if (timer <= 0) {
      getAList().add(new Action(1, 20));
    } else if (getAList().size() == 0) {
      getAList().add(new Action(0, 30, new double[]{Tools.angleTo(getX(), -getY(), getGame().getPlayer().getX(), -getGame().getPlayer().getY()), 8}));
    }
  }

  /*
    case 0 moves in the predetermined direction and decelerates
    case 1 creates splitCount amount of EnemySplitterProj evenly distributed around it, which each are set to split into splitCount amount of EnemyBeamProj, then destroys itself
  */
  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        moveInDir(a.getData()[0], a.getData()[1]);
        a.getData()[1] /= 1.05;
        break;
        
      case 1:
        if (a.getTimer() == 1) {
          for (double r = 0; r < Math.PI*2; r += Math.PI*2/splitCount) {
            getGame().addProjectile(new EnemySplitterProj(getGame(), r, getX(), getY(), 20, splitCount));
          }
          destroy();
        }
        break;
        
      default:
        break;
    }
  }

  // spawns 4 particles, and at least 1 random pickup with a chance to spawn any of the other 2 pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 4; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));
    
    if (getHp() <= 0) {
      double d = Math.random();
      
      if (d < 0.3) {
        getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
        
        if (Math.random() > 0.95) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));
        if (Math.random() > 0.95) getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));
        
      } else if (d < 0.6) {
        getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));

        if (Math.random() > 0.95) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));
        if (Math.random() > 0.95) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
        
      } else {
        getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));

        if (Math.random() > 0.95) getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));
        if (Math.random() > 0.95) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
      }
    }
  }

  // sprite rotates to face the player
  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyChaser"), Tools.angleTo(getX(), -getY(), getGame().getPlayer().getX(), -getGame().getPlayer().getY())), getX(), getY(), 0);
  }
  
}