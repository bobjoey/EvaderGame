package Projectiles;
import General.Sprite;
import Game.*;
import Entities.Player;

public class PickupPower extends Pickup {

  public PickupPower(GameState g, double x, double y) {
    super(g, x, y, -4);
  }
  public PickupPower(GameState g, double x, double y, double sV, double ixd) {
    super(g, x, y, sV, ixd);
  }

  @Override
  public void pickupEffect(Player p) {
    p.setPowerLevel(p.getPowerLevel() + 10);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("pickupPower"), getX(), getY(), 4);
  }
  
}