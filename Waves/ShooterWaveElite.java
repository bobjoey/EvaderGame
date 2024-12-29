package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class ShooterWaveElite extends Wave {

  public ShooterWaveElite(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    int w = getGame().getWidth();
    Entity spawnLeft = new EnemyShooter(getGame(), 0, 0, true);
    Entity carrierLeft = new EnemyCarrier(getGame(), spawnLeft, w/12.0, -60, 40);
    send.add(carrierLeft);
    Entity spawnRight = new EnemyShooter(getGame(), 0, 0, true);
    Entity carrierRight = new EnemyCarrier(getGame(), spawnRight, w-w/12.0, -60, 40);
    send.add(carrierRight);
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 1;
    if (level < 16 || level > 50) d = 0;
    return d;
  }
  
}