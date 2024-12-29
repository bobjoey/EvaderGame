package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class DroneWave extends Wave {

  public DroneWave(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    int w = getGame().getWidth();
    for (int i = 0; i < 3; i++) {
      Entity spawnLeft = new EnemyDrone(getGame(), 0, 0);
      Entity carrierLeft = new EnemyCarrier(getGame(), spawnLeft, w*i/6.0+w/12.0, -60-60*i, 40);
      send.add(carrierLeft);
      Entity spawnRight = new EnemyDrone(getGame(), 0, 0);
      Entity carrierRight = new EnemyCarrier(getGame(), spawnRight, w-w*i/6.0-w/12.0, -60-60*i, 40);
      send.add(carrierRight);
    }
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 1;
    if (level > 14) d = 0;
    return d;
  }
  
}