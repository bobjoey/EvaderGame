package Entities;
import General.*;
import Game.*;
import Projectiles.*;

public class EnemyTurretScatter extends Entity {
  private Entity host;
  private double xOffset;
  private double atkCD;

  public EnemyTurretScatter(GameState g, Entity e, double x) {
    super(g, 1, 20, 60, e.getX(), e.getY());
    host = e;
    xOffset = x;
    atkCD = 90;
  }

  // updates its x and y to match the host's (with a horizontal offset to prevent overlap)
  @Override
  public void update() {
    super.update();

    setX(host.getX() + xOffset);
    setY(host.getY());

    atkCD--;
  }

  // when the attack cooldown is 0, randomly chooses one of two attacks aimed at the player - one has a cooldown of 2 seconds, one has a cooldown of 2.5 seconds
  @Override
  public void prepareActions() {
    if (atkCD <= 0) {
      Player p = getGame().getPlayer();
      double[] r = {Tools.angleTo(getX(), -getY(), p.getX(), -p.getY())};
      
      double d = Math.random();
      if (d > 0.5) {
        atkCD = 60;
        getAList().add(new Action(0, 1, r));
      } else {
        atkCD = 75;
        getAList().add(new Action(1, 1, r));
      }
    }
  }

  /*
    case 0: fires an even spread of 5 EnemyBeamProj in the predetermined direction
    case 1: fires an even spread of 3 EnemySplitterProj in the predetermined direction
  */
  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        int spreadCount = 5;
        double spreadAngle = Math.PI/3;
        for (double r = a.getData()[0]-spreadAngle/2; r <= a.getData()[0]+spreadAngle*1.05/2; r += spreadAngle/(spreadCount-1)) {
          getGame().addProjectile(new EnemyBeamProj(getGame(), r, getX(), getY()));
        }
        break;
        
      case 1:
        int spreadCountB = 3;
        double spreadAngleB = Math.PI/6;
        for (double r = a.getData()[0]-spreadAngleB/2; r <= a.getData()[0]+spreadAngleB*1.05/2; r += spreadAngleB/(spreadCountB-1)) {
          getGame().addProjectile(new EnemySplitterProj(getGame(), r, getX(), getY(), 45, 6, 4));
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

  // spawns 3 particles when destroyed
  @Override
  public void spawnDrops() {
    for (int i = 0; i < 2; i++) getGame().addProjectile(new Particle(getGame(), getX(), getY()));
  }

  // sprite rotates to face the player
  @Override
  public Sprite getSprite() {
    return new Sprite(Tools.rotate(getGame().getSheet().get("enemyGatling"), Tools.angleTo(getX(), -getY(), getGame().getPlayer().getX(), -getGame().getPlayer().getY())), getX(), getY(), 2);
  }
  
}