package Projectiles;
import General.*;
import Game.*;
import Entities.*;

public class Pickup extends Projectile {
  private double maxPickupRange, initialXDir;

  public Pickup(GameState g, double x, double y, double sV) {
    super(g, sV, Math.PI*3/2, 1, 8, 0, x, y);
    maxPickupRange = 80;
    initialXDir = 0;
  }
  public Pickup(GameState g, double x, double y, double sV, double ixd) {
    super(g, sV, Math.PI*3/2, 1, 8, 0, x, y);
    maxPickupRange = 80;
    initialXDir = ixd;
  }

  @Override
  public void update() {
    super.update();

    setX(getX()+initialXDir);
    initialXDir *= 0.95;

    setSpeed(getSpeed()+0.2);
    if (getSpeed() > 5) setSpeed(5);

    Player p = getGame().getPlayer();
    double dist = Tools.dist(getX(), -getY(), p.getX(), -p.getY());
    if (dist < maxPickupRange) {
      double r = Tools.angleTo(getX(), -getY(), p.getX(), -p.getY());
      moveInDir(r, Math.min((maxPickupRange-dist)*0.3, 10));
    }
  }

  @Override
  public void onCollision(Entity e) {
    if (e instanceof Player) {
      pickupEffect((Player)e);
      destroy();
    }
  }

  public void pickupEffect(Player p) {}
  
}