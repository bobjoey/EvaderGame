package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class Player extends Entity {
  private boolean[] ctrls;
  public static final int UP=0, DOWN=1, LEFT=2, RIGHT=3, SHIFT=4, Z=5, X=6;
  private double baseSpeed, dir, speed, atkCD, charge, powerLevel;
  private int maxHp, specOption;

  public Player(GameState g) {
    super(g, 0, 6, 100, 0, 0);
    ctrls = new boolean[7];
    
    maxHp = 100;
    baseSpeed = 10.0;
    specOption = 1;
    
    dir = Math.PI / 2;
    speed = 0;
    atkCD = 0;
    charge = 0;
    powerLevel = 0;
  }

  @Override
  public void update() {
    super.update();
    
    updateStats();
  }

  @Override
  public void prepareActions() {
    prepareMovement();
    prepareAtk();
  }
  // movement keys --> direction & speed
  public void prepareMovement() {
    speed = 0;
    int ud = (ctrls[UP] ? 1 : 0) + (ctrls[DOWN] ? -1 : 0);
    int lr = (ctrls[LEFT] ? -1 : 0) + (ctrls[RIGHT] ? 1 : 0);
    if (ud != 0 || lr != 0) {
      speed = baseSpeed * (ctrls[SHIFT] ? 0.5 : 1);
      dir = Tools.angleTo(0, 0, lr, ud);
    }
  }
  public void prepareAtk() {
    if (ctrls[Z] && atkCD <= 0) {
      getAList().add(new Action(0, 1));
      atkCD = 8;
    }
    
    if (ctrls[X]) {
      if (specOption == 1 && charge >= 50) {
        // deploy shield
        ctrls[X] = false;
        getAList().add(new Action(1, 1));
        charge -= 50;
        
      } else if (specOption == 2 && charge >= 20) {
        // deploy drone
        ctrls[X] = false;
        getAList().add(new Action(2, 1));
        charge -= 20;
      }
    } 
  }

  @Override
  public void processActions() {
    processMovement();
    super.processActions();
  }

  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        // z ability
        spreadShot(a);
        break;
        
      case 1:
        // x ability option 1
        deployShield(a);
        break;

      case 2:
        // x ability option 2
        deployDrone(a);
        break;
        
      default:
        break;
    }
  }
  
  public void processMovement() {
    if (speed > 0) moveInDir(dir, speed);

    int w = getGame().getWidth();
    int h = getGame().getHeight();
    if (getX() < 0) setX(0);
    if (getX() > w) setX(w);
    if (getY() < 0) setY(0);
    if (getY() > h) setY(h);
  }

  // main attack, fires an even spread of PlayerBeamProj forward - projectile count increases as powerLevel increases, holding shift halves the spread angle
  public void spreadShot(Action a) {
    getGame().addProjectile(new PlayerBeamProj(getGame(), Math.PI/2, getX(), getY()));
    
    int spreadCount = 1;
    if (powerLevel >= 20) spreadCount++;
    if (powerLevel >= 50) spreadCount++;
    if (powerLevel >= 80) spreadCount++;

    double spreadAngle = 0.1;
    if (ctrls[SHIFT]) spreadAngle *= 0.5;
    
    for (int i = 1; i <= spreadCount; i++) {
      getGame().addProjectile(new PlayerBeamProj(getGame(), Math.PI/2+i*spreadAngle, getX(), getY()));
      getGame().addProjectile(new PlayerBeamProj(getGame(), Math.PI/2-i*spreadAngle, getX(), getY()));
    }
  }

  public void deployShield(Action a) {
    getGame().addEntity(new PlayerShieldCircle(getGame(), getX(), getY()));
  }

  public void deployDrone(Action a) {
    getGame().addEntity(new PlayerDrone(getGame(), this));
  }

  // updates the player's cooldowns, charge, power, and health
  public void updateStats() {
    atkCD--;
    
    charge += 1.0/9;
    if (charge < 0) charge = 0;
    if (charge > 100) charge = 100;

    powerLevel -= 0.0005 * powerLevel;
    if (powerLevel < 0) powerLevel = 0;
    if (powerLevel > 100) powerLevel = 100;
    
    if (getHp() > maxHp) setHp(maxHp);
  }

  @Override
  public void destroy() {
    // trigger game over
    getGame().gameOver();
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("player"), getX(), getY(), 0);
  }

  public int getOption() {
    return specOption;
  }
  public void setOption(int o) {
    specOption = o;
  }
  public double getCharge() {
    return charge;
  }
  public void setCharge(double charge) {
    this.charge = charge;
  }
  public double getPowerLevel() {
    return powerLevel;
  }
  public void setPowerLevel(double powerLevel) {
    this.powerLevel = powerLevel;
  }

  // key pressed (P) and released (R) methods
  public void keyP(int keyCode) {
    if (keyCode < 0 || keyCode > ctrls.length) return;
    ctrls[keyCode] = true;
  }
  public void keyR(int keyCode) {
    if (keyCode < 0 || keyCode > ctrls.length) return;
    ctrls[keyCode] = false;
  }
  public boolean getKey(int keyCode) {
    return ctrls[keyCode];
  }
  
}