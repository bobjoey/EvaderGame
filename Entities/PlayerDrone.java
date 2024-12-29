package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class PlayerDrone extends Entity {
  private Player owner;
  private double orbitAngle, facingAngle, atkCD;
  private Entity target;

  public PlayerDrone(GameState g, Player o) {
    super(g, 0, 9, 80, o.getX(), o.getY());
    owner = o;
    orbitAngle = -Math.PI/2;
    facingAngle = orbitAngle;
    atkCD = 15;
    updateTarget();
  }

  @Override
  public void update() {
    if (!getGame().getEntities().contains(target)) {
      updateTarget();
    }

    updateAngle();
    super.update();
    processMovement();
    
    orbitAngle = orbitAngle + Math.PI/90;
    if (orbitAngle > Math.PI*2) orbitAngle = 0;
    
    setHp(getHp() - 3.0/30);
    atkCD--;
  }

  // finds the nearest non-player-team entity to set as target
  public void updateAngle() {
    double targetAngle = Math.PI/2;
    if (target != null) {
      targetAngle = Tools.angleTo(getX(), -getY(), target.getX(), -target.getY());
    }
    
    double d = facingAngle - targetAngle;
    if (d > Math.PI) facingAngle -= Math.PI*2;
    else if (d < -Math.PI) facingAngle += Math.PI*2;
    
    if (Math.abs(d) < Math.PI/15) facingAngle = targetAngle;
    else if (facingAngle > targetAngle) facingAngle -= Math.PI/15;
    else if (facingAngle < targetAngle) facingAngle += Math.PI/15;
  }

  public void processMovement() {
    double orbitX = owner.getX() + Math.cos(orbitAngle) * 60;
    double orbitY = owner.getY() - Math.sin(orbitAngle) * 60;
    if (Tools.dist(getX(), getY(), orbitX, orbitY) <= 8) {
      setX(orbitX);
      setY(orbitY);
    } else {
      moveInDir(Tools.angleTo(getX(), getGame().getWidth()-getY(), orbitX, getGame().getWidth()-orbitY), 8);
    }
  }

  public void updateTarget() {
    // set the nearest enemy to be the target
    Entity nearest = null;
    for (Entity e: getGame().getEntities()) {
      if (e.getTeam() == 0) continue;
      if (nearest == null || Tools.dist(getX(), getY(), e.getX(), e.getY()) < Tools.dist(getX(), getY(), nearest.getX(), nearest.getY())) nearest = e;
    }
    target = nearest;
  }

  @Override
  public void prepareActions() {
    if (atkCD <= 0) {
      getAList().add(new Action(0, 1));
      atkCD = 15;
    }
  }

  @Override
  public void executeAction(Action a) {
    getGame().addProjectile(new PlayerDronePellet(getGame(), facingAngle, getX(), getY()));
  }

  @Override
  public void spawnDrops() {
    for (int i = 0; i < 2; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("playerDrone"), facingAngle), getX(), getY(), 1);
  }
  
}