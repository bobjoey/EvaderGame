package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyTurretSniper extends Entity {
  private Entity host;
  private double xOffset;
  private double atkCD;

  public EnemyTurretSniper(GameState g, Entity e, double x) {
    super(g, 1, 20, 60, e.getX(), e.getY());
    host = e;
    xOffset = x;
    atkCD = 95;
  }

  // updates its x and y to match the host's (with a horizontal offset to prevent overlap)
  @Override
  public void update() {
    super.update();

    setX(host.getX() + xOffset);
    setY(host.getY());

    atkCD--;
  }

  // when the attack cooldown is 0, randomly chooses to either fire an attack at the player with a cooldown of 2.67 seconds or wait an additional 0.167 seconds
  @Override
  public void prepareActions() {
    if (atkCD <= 0) {
      Player p = getGame().getPlayer();
      double[] r = {Tools.angleTo(getX(), -getY(), p.getX(), -p.getY())};
      
      double d = Math.random();
      if (d > 0.5) {
        atkCD = 80;
        getAList().add(new Action(0, 10, r));
      } else {
        atkCD = 5;
      }
    }
  }

  // fires a row of decelerating EnemyTrapProj in the predetermined direction such that they create a line of projectiles
  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        int t = a.getTimer();
        if (t < 8) {
          getGame().addProjectile(new EnemyTrapProj(getGame(), a.getData()[0], getX(), getY(), t*2.75, 45+3*t, 0.98));
        }
        break;
        
      default:
        break;
    }
  }

  // damage taken is halved and redirected to the host
  @Override
  public void onCollision(Projectile p) {
    p.setDmg(p.getDmg()/2);
    host.onCollision(p);
  }

  // spawns 2 particles when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 2; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));
  }

  // sprite rotates to face the player
  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyChaser"), Tools.angleTo(getX(), -getY(), getGame().getPlayer().getX(), -getGame().getPlayer().getY())), getX(), getY(), 2);
  }
  
}