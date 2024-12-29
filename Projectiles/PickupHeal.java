package Projectiles;
import General.Sprite;
import Game.*;
import Entities.Player;

public class PickupHeal extends Pickup {

  public PickupHeal(GameState g, double x, double y) {
    super(g, x, y, -5);
  }
  public PickupHeal(GameState g, double x, double y, double sV, double ixd) {
    super(g, x, y, sV, ixd);
  }

  @Override
  public void pickupEffect(Player p) {
    p.setHp(p.getHp() + 10);
  }

  @Override
  public Sprite getSprite() {
    return new Sprite(getGame().getSheet().get("pickupHeal"), getX(), getY(), 4);
  }
  
}