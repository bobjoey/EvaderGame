package Entities;
import General.*;
import Game.*;
import Projectiles.*;

import java.util.HashSet;

public class EnemyGatling extends Entity {
  private double atkDelay, facingAngle;
  private HashSet<Action> toAddActions;

  public EnemyGatling(GameState g, double x, double y) {
    super(g, 1, 14, 240, x, y);
    atkDelay = 20;
    toAddActions = new HashSet<Action>();
    facingAngle = -Math.PI/2;
  }

  @Override
  public void update() {
    super.update();
    
    for (Action a: toAddActions) {
      getAList().add(a);
    }
  }

  /*
    If close enough to and facing the player for long enough, triggers the attack
    moves to reach a set distance from the player
  */
  @Override
  public void prepareActions() {
    Player p = getGame().getPlayer();
    double r = Tools.angleTo(getX(), -getY(), p.getX(), -p.getY());
    double d = Tools.dist(getX(), getY(), p.getX(), p.getY());
    
    if (atkDelay <= 80) updateAngle(r);
      
    if (atkDelay > 20) atkDelay--;
    else {
      double s = 0;
      if (d > 180) s = 1-1/(d-179);
      else if (d < 175) s = -1+1/(176-d);
      s *= 1.5;
      getAList().add(new Action(0, 2, new double[]{r, s}));
    }

    if (d < 185) atkDelay--;
    else if (atkDelay < 20) atkDelay++;
    
    if (atkDelay <= 0 && Math.abs(facingAngle-r) < Math.PI/15) {
      atkDelay = 180;
      getAList().add(new Action(1, 60, new double[]{facingAngle, 5}));
    }
  }

  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        processMovement(a);
        break;

      case 1:
        processAttack(a);
        break;
        
      default:
        break;
    }
  }

  // turns to face the player
  public void updateAngle(double r) {
    double d = facingAngle - r;
    if (d > Math.PI) facingAngle -= Math.PI*2;
    else if (d < -Math.PI) facingAngle += Math.PI*2;
    
    if (Math.abs(d) < Math.PI/15) facingAngle = r;
    else if (facingAngle > r) facingAngle -= Math.PI/15;
    else if (facingAngle < r) facingAngle += Math.PI/15;
  }

  // moves in the predetermined direction, cannot leave the game screen
  public void processMovement(Action a) {
    moveInDir(a.getData()[0], a.getData()[1]);
    
    int w = getGame().getWidth();
    int h = getGame().getHeight();
    if (getX() < 0) setX(0);
    if (getX() > w) setX(w);
    if (getY() < 0) setY(0);
    if (getY() > h) setY(h);
  }

  // fires a series of randomly spread EnemyBeamProj in the predetermined direction, fire rate increases as the attack proceeds, and accelerates backwards
  public void processAttack(Action a) {
    double t = a.getTimer();
    if (t <= 20) a.getData()[1] = 3;
    else if (t <= 40) a.getData()[1] = 4;

    if (t % a.getData()[1] == 0) {
      double spreadModifier = Math.random()*Math.PI/4 - Math.PI/8;
      getGame().addProjectile(new EnemyBeamProj(getGame(), a.getData()[0]+spreadModifier, getX(), getY()));
    }
    
    toAddActions.add(new Action(0, 20, new double[]{a.getData()[0], -0.2}));
  }

  // spawns 5 particles, and has a chance to spawn each of the 3 pickups when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 5; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));

    if (Math.random() > 0.85) getGame().addProjectile(new PickupHeal(getGame(), getX(), getY()));
    if (Math.random() > 0.85) getGame().addProjectile(new PickupCharge(getGame(), getX(), getY()));
    if (Math.random() > 0.85) getGame().addProjectile(new PickupPower(getGame(), getX(), getY()));
  }

  // sprite rotates to match facingAngle
  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyGatling"), facingAngle), getX(), getY(), 0);
  }
  
}