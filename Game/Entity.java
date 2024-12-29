package Game;
import General.*;

import java.util.HashSet;

public abstract class Entity extends GameObj {
  // list of movements/actions to execute
  private HashSet<Action> aList;

  // team: 0 = player, 1 = enemy, anything else = neutral
  // size: radius of circular hitbox
  private int team, size;
  private double hp;
  
  public Entity(GameState g, int t, int s, double h, double x, double y) {
    super(g, x, y);
    aList = new HashSet<Action>();
    team = t;
    size = s;
    hp = h;
  }

  @Override
  public void update() {
    prepareActions();
    processActions();
    collisionCheck();

    if (hp <= 0) destroy();
  }
  
  public abstract void prepareActions();
  
  public void processActions() {
    getAList().removeIf(a -> a.tick() == false);
    for (Action a: getAList()) {
      executeAction(a);
    }
  }
  public abstract void executeAction(Action a);

  public void collisionCheck() {
    for (Projectile p: getGame().getProjectiles()) {
      if (p.getTeam() == team) continue;
      if (Tools.dist(getX(), getY(), p.getX(), p.getY()) > size + p.getSize()) continue;
      onCollision(p);
      p.onCollision(this);
    }
  }
  public void onCollision(Projectile p) {
    hp -= p.getDmg();
  }
  
  @Override
  public void destroy() {
    spawnDrops();
    getGame().remove(this);
  }
  public void spawnDrops() {}

  public HashSet<Action> getAList() {
	  return aList;
  }
  public int getTeam() {
	  return team;
  }
  public int getSize() {
	  return size;
  }
  public double getHp() {
	  return hp;
  }
  public void setTeam(int team) {
	  this.team = team;
  }
  public void setSize(int size) {
	  this.size = size;
  }
  public void setHp(double hp) {
	  this.hp = hp;
  }
}

