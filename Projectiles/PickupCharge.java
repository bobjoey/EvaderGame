package Projectiles;
import General.Sprite;
import Game.*;
import Entities.Player;

public class PickupCharge extends Pickup {

  public PickupCharge(GameState g, double x, double y) {
    super(g, x, y, -3);
  }
  public PickupCharge(GameState g, double x, double y, double sV, double ixd) {
    super(g, x, y, sV, ixd);
  }

  @Override
  public void pickupEffect(Player p) {
    p.setCharge(p.getCharge() + 10);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("pickupCharge"), getX(), getY(), 4);
  }
  
}