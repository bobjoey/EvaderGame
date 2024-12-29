package Waves;
import Game.*;
import Entities.*;

import java.util.HashSet;

public class RandomWave extends Wave {

  public RandomWave(GameState g) {
    super(g);
  }

  @Override
  public HashSet<Entity> getWave() {
    HashSet<Entity> send = new HashSet<Entity>();
    
    int w = getGame().getWidth();
    for (int i = 0; i < 5; i++) {
      double type = Math.random();
      Entity spawn;
      if (type < 0.6) {
        spawn = new EnemyDrone(getGame(), 0, 0);
      } else if (type < 0.8) {
        spawn = new EnemyChaser(getGame(), 0, 0);
      } else {
        spawn = new EnemyShooter(getGame(), 0, 0);
      }
      Entity carrier = new EnemyCarrier(getGame(), spawn, Math.random()*w, -60-10*i, 50+Math.random()*30);
      send.add(carrier);
    }
    
    return send;
  }

  @Override
  public double getWeight(int level) {
    double d = 1;
    if (level < 3 || level > 25) d = 0;
    return d;
  }
  
}