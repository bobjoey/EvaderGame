package Entities;
import General.*;
import Game.*;

public class EnemyCarrier extends Entity {
  private Entity spawn;
  private double speed;

  // starts at (x, y) and moves straight downward until it reaches yTarget, then waits 0.5 seconds, destroys itself and adds spawn to the game
  public EnemyCarrier(GameState g, Entity e, double x, double y, double yTarget) {
    super(g, 0, 0, 1, x, y);
    spawn = e;
    getAList().add(new Action(1, 30, new double[]{yTarget}));
    speed = 6;
  }

  // starts at (x, y) and moves in direction r until it has moved dToMove units, then waits 0.5 seconds, destroys itself and adds spawn to the game
  public EnemyCarrier(GameState g, Entity e, double x, double y, double dToMove, double r) {
    super(g, 0, 0, 1, x, y);
    spawn = e;
    getAList().add(new Action(2, 30, new double[]{dToMove, r}));
    speed = 6;
  }

  @Override
  public void prepareActions() {
    if (getAList().size() == 0) getAList().add(new Action(0, 10));
  }

  /*
    case 1 moves down until it reaches the target, then triggers case 0 after 0.5 seconds
    case 2 moves in a set direction until it travels far enough, then triggers case 0 after 0.5 seconds
    case 0 adds spawn to the game and destroys the EnemyCarrier
  */
  @Override
  public void executeAction(Action a) {
    switch (a.getId()) {
      case 0:
        spawn.setX(getX());
        spawn.setY(getY());
        getGame().addEntity(spawn);
        destroy();
        break;
        
      case 1:
        if (getY() < a.getData()[0]) {
          a.setTimer(15);
          move(0, Math.min(speed, a.getData()[0]-getY()));
        }
        break;
        
      case 2:
        if (a.getData()[0] > 0) {
          a.setTimer(15);
          moveInDir(a.getData()[1], speed);
          a.getData()[0] -= speed;
        }
        break;
        
      default:
        break;
    }
  }

  @Override
  public void collisionCheck() {}

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("enemyCarrier"), getX(), getY(), 3);
  }
  
}