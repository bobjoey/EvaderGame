package Game;
import General.*;

public abstract class GameObj {
  private GameState game;
  private double x, y;
  
  public GameObj(GameState g, double x, double y) {
    game = g;
    this.x = x;
    this.y = y;
  }

  public abstract void update();

  // moves spd units in the specified direction (radians)
  public void moveInDir(double r, double spd) {
    x += Math.cos(r) * spd;
    y -= Math.sin(r) * spd;
  }
  
  public void move(double xd, double yd) {
    x += xd;
    y += yd;
  }
  
  public void destroy() {
    game.remove(this);
  }
  
  public Sprite getSprite() {
    return new Sprite(game.getSheet().get("red"), x, y, 0);
  }

  public GameState getGame() {
    return game;
  }
  public double getX() {
	  return x;
  }
  public double getY() {
	  return y;
  }
  public void setX(double x) {
	  this.x = x;
  }
  public void setY(double y) {
	  this.y = y;
  }
}

