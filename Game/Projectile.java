package Game;
import General.*;

public abstract class Projectile extends GameObj {
  private double speed, rotation, dmg; // rotation in radians, 0 = right
  private int team, size; // size = size of circular hitbox
  
  public Projectile(GameState g, double sp, double r, int t, int s, double d, double x, double y) {
    super(g, x, y);
    speed = sp;
    rotation = r;
    team = t;
    size = s;
    dmg = d;
  }

  public void update() {
    moveInDir(rotation, speed);

    int w = getGame().getWidth();
    int h = getGame().getHeight();
    if (getX() < -60 || getX() > w+60 || getY() < -60 || getY() > h+60) destroy();
  }

  public void collisionCheck() {
    for (Entity e: getGame().getEntities()) {
      if (e.getTeam() == team) continue;
      if (Tools.dist(getX(), getY(), e.getX(), e.getY()) > size + e.getSize()) continue;
      onCollision(e);
      e.onCollision(this);
    }
  }
  public void onCollision(Entity e) {
    destroy();
  }
  
  public double getSpeed() {
	  return speed;
  } 
  public double getRotation() {
	  return rotation;
  }
  public int getTeam() {
	  return team;
  }
  public int getSize() {
	  return size;
  }
  public double getDmg() {
	  return dmg;
  }
  public void setSpeed(double speed) {
	  this.speed = speed;
  }
  public void setRotation(double rotation) {
	  this.rotation = rotation;
  }
  public void setTeam(int team) {
	  this.team = team;
  }
  public void setSize(int size) {
	  this.size = size;
  }
  public void setDmg(double dmg) {
	  this.dmg = dmg;
  }
}