package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class ShooterWave extends Wave {

  public ShooterWave(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    int w = getGame().getWidth();
    Entity spawnLeft = new EnemyShooter(getGame(), 0, 0);
    Entity carrierLeft = new EnemyCarrier(getGame(), spawnLeft, w/12.0, -60, 40);
    send.add(carrierLeft);
    Entity spawnRight = new EnemyShooter(getGame(), 0, 0);
    Entity carrierRight = new EnemyCarrier(getGame(), spawnRight, w-w/12.0, -60, 40);
    send.add(carrierRight);
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 1;
    if (level < 1 || level > 15) d = 0;
    return d;
  }
  
}